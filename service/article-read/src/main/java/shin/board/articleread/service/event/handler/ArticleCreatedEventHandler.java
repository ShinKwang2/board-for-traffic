package shin.board.articleread.service.event.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shin.board.articleread.repository.ArticleIdListRepository;
import shin.board.articleread.repository.ArticleQueryModel;
import shin.board.articleread.repository.ArticleQueryModelRepository;
import shin.board.articleread.repository.BoardArticleCountRepository;
import shin.board.common.event.Event;
import shin.board.common.event.EventType;
import shin.board.common.event.payload.ArticleCreatedEventPayload;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class ArticleCreatedEventHandler implements EventHandler<ArticleCreatedEventPayload>{

    private final ArticleIdListRepository articleIdListRepository;
    private final ArticleQueryModelRepository articleQueryModelRepository;
    private final BoardArticleCountRepository boardArticleCountRepository;

    @Override
    public void handle(Event<ArticleCreatedEventPayload> event) {
        ArticleCreatedEventPayload payload = event.getPayload();
        articleQueryModelRepository.create(
                ArticleQueryModel.create(payload),
                Duration.ofDays(1)
        );
        articleIdListRepository.add(payload.getBoardId(), payload.getArticleId(), 1000L);
        boardArticleCountRepository.createOrUpdate(payload.getBoardId(), payload.getBoardArticleCount());

        // 아래와 같은 순서로 되어 있다면
        // articleIdListRepository.add(payload.getBoardId(), payload.getArticleId(), 1000L);
        // 사용자가 이 시점에 조회할 때는 목록에는 노출되고, 아티클 쿼리모델에서는 아직 생성되지 않은 상황일 수 있다.
        // articleQueryModelRepository.create(
        //         ArticleQueryModel.create(payload),
        //         Duration.ofDays(1)
        // );
    }

    @Override
    public boolean supports(Event<ArticleCreatedEventPayload> event) {
        return EventType.ARTICLE_CREATED == event.getType();
    }
}
