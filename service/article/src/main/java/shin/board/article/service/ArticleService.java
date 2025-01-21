package shin.board.article.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shin.board.article.entity.Article;
import shin.board.article.entity.BoardArticleCount;
import shin.board.article.repository.ArticleRepository;
import shin.board.article.repository.BoardArticleCountRepository;
import shin.board.article.service.request.ArticleCreateRequest;
import shin.board.article.service.request.ArticleUpdateRequest;
import shin.board.article.service.response.ArticlePageResponse;
import shin.board.article.service.response.ArticleResponse;
import shin.board.common.event.EventType;
import shin.board.common.event.payload.ArticleCreatedEventPayload;
import shin.board.common.event.payload.ArticleDeletedEventPayload;
import shin.board.common.event.payload.ArticleUpdatedEventPayload;
import shin.board.common.outboxmessagerelay.OutboxEventPublisher;
import shin.board.common.snowflake.Snowflake;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;
    private final BoardArticleCountRepository boardArticleCountRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Article savedArticle = articleRepository.save(
                Article.create(snowflake.nextId(), request.getTitle(), request.getContent(), request.getBoardId(), request.getWriterId())
        );
        int result = boardArticleCountRepository.increase(request.getBoardId());
        if (result == 0) {
            boardArticleCountRepository.save(
                    BoardArticleCount.init(request.getBoardId(), 1L)
            );
        }

        outboxEventPublisher.publish(
                EventType.ARTICLE_CREATED,
                ArticleCreatedEventPayload.builder()
                        .articleId(savedArticle.getArticleId())
                        .title(savedArticle.getTitle())
                        .content(savedArticle.getContent())
                        .boardId(savedArticle.getBoardId())
                        .writerId(savedArticle.getWriterId())
                        .createdAt(savedArticle.getCreatedAt())
                        .modifiedAt(savedArticle.getModifiedAt())
                        .boardArticleCount(count(savedArticle.getBoardId()))
                        .build(),
                savedArticle.getBoardId()
        );

        return ArticleResponse.from(savedArticle);
    }

    @Transactional
    public ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow();
        article.update(request.getTitle(), request.getContent());

        outboxEventPublisher.publish(
                EventType.ARTICLE_UPDATED,
                ArticleUpdatedEventPayload.builder()
                        .articleId(article.getArticleId())
                        .title(article.getTitle())
                        .content(article.getContent())
                        .boardId(article.getBoardId())
                        .writerId(article.getWriterId())
                        .createdAt(article.getCreatedAt())
                        .modifiedAt(article.getModifiedAt())
                        .build(),
                article.getBoardId()
        );

        return ArticleResponse.from(article);
    }

    public ArticleResponse read(Long articleId) {
        return ArticleResponse.from(articleRepository.findById(articleId).orElseThrow());
    }

    @Transactional
    public void delete(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        articleRepository.delete(article);
        boardArticleCountRepository.decrease(article.getBoardId());

        outboxEventPublisher.publish(
                EventType.ARTICLE_DELETED,
                ArticleDeletedEventPayload.builder()
                        .articleId(article.getArticleId())
                        .title(article.getTitle())
                        .content(article.getContent())
                        .boardId(article.getBoardId())
                        .writerId(article.getWriterId())
                        .createdAt(article.getCreatedAt())
                        .modifiedAt(article.getModifiedAt())
                        .boardArticleCount(count(article.getBoardId()))
                        .build(),
                article.getBoardId()
        );
    }

    public ArticlePageResponse readAll(Long boardId, Long page, Long pageSize) {
        return ArticlePageResponse.of(
                articleRepository.findAll(boardId, (page - 1) * pageSize, pageSize).stream()
                        .map(ArticleResponse::from)
                        .toList(),
                articleRepository.count(
                        boardId,
                        PageLimitCalculator.calculatePageLimit(page, pageSize, 10L)
                )
        );
    }

    public List<ArticleResponse> readAllInfiniteScroll(Long boardId, Long pageSize, Long lastArticleId) {
        List<Article> articles = lastArticleId == null ?
                articleRepository.findAllInfiniteScroll(boardId, pageSize) :
                articleRepository.findAllInfiniteScroll(boardId, pageSize, lastArticleId);
        return articles.stream()
                .map(ArticleResponse::from)
                .toList(); // Since Java 16
    }

    public Long count(Long boardId) {
        return boardArticleCountRepository.findById(boardId)
                .map(BoardArticleCount::getArticleCount)
                .orElse(0L);
    }
}
