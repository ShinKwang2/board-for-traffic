package shin.board.hotarticle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shin.board.hotarticle.repository.ArticleCommentCountRepository;
import shin.board.hotarticle.repository.ArticleLikeCountRepository;
import shin.board.hotarticle.repository.ArticleViewCountRepository;

@RequiredArgsConstructor
@Component
public class HotArticleScoreCalculator {

    private final ArticleLikeCountRepository articleLikeCountRepository;
    private final ArticleCommentCountRepository articleCommentCountRepository;
    private final ArticleViewCountRepository articleViewCountRepository;

    private static final long ARTICLE_LIKE_COUNT_WEIGHT = 3;
    private static final long ARTICLE_COMMENT_COUNT_WEIGHT = 2;
    private static final long ARTICLE_VIEW_COUNT_WEIGHT = 1;

    public long calculate(Long articleId) {
        Long articleLikeCount = articleLikeCountRepository.read(articleId);
        Long articleCommentCount = articleCommentCountRepository.read(articleId);
        Long articleViewCount = articleViewCountRepository.read(articleId);
        return articleLikeCount * ARTICLE_LIKE_COUNT_WEIGHT
                + articleCommentCount * ARTICLE_COMMENT_COUNT_WEIGHT
                + articleViewCount * ARTICLE_VIEW_COUNT_WEIGHT;
    }
}
