package shin.board.articleread.service.event.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shin.board.articleread.repository.ArticleIdListRepository;
import shin.board.articleread.repository.ArticleQueryModelRepository;
import shin.board.articleread.repository.BoardArticleCountRepository;
import shin.board.common.event.Event;
import shin.board.common.event.EventType;
import shin.board.common.event.payload.ArticleDeletedEventPayload;
import shin.board.common.event.payload.ArticleUpdatedEventPayload;

@RequiredArgsConstructor
@Component
public class ArticleDeletedEventHandler implements EventHandler<ArticleDeletedEventPayload>{

    private final ArticleIdListRepository articleIdListRepository;
    private final ArticleQueryModelRepository articleQueryModelRepository;
    private final BoardArticleCountRepository boardArticleCountRepository;

    @Override
    public void handle(Event<ArticleDeletedEventPayload> event) {
        ArticleDeletedEventPayload payload = event.getPayload();
        articleIdListRepository.delete(payload.getBoardId(), payload.getArticleId());
        articleQueryModelRepository.delete(payload.getArticleId());
        boardArticleCountRepository.createOrUpdate(payload.getBoardId(), payload.getBoardArticleCount());

        // 순서가 중요한 이유, 만약 아래와 같은 순서라면
        // articleQueryModelRepository.delete(payload.getArticleId());
        // 이 시점에서 사용자는 게시글 목록을 조회할 수 있다.
        // 아직 게시글 목록이 아직 삭제가 안되서, 클릭해서 들어가면 이미 조회가 안될 가능성이 있다.
        // 찰나의 순간이지만 최대한 줄이자
        // articleIdListRepository.delete(payload.getBoardId(), payload.getArticleId());
        // boardArticleCountRepository.createOrUpdate(payload.getBoardId(), payload.getBoardArticleCount());
    }

    @Override
    public boolean supports(Event<ArticleDeletedEventPayload> event) {
        return EventType.ARTICLE_DELETED == event.getType();
    }
}
