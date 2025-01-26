package shin.board.articleread.service.event.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shin.board.articleread.repository.ArticleQueryModelRepository;
import shin.board.common.event.Event;
import shin.board.common.event.EventType;
import shin.board.common.event.payload.ArticleDeletedEventPayload;
import shin.board.common.event.payload.ArticleUpdatedEventPayload;

@RequiredArgsConstructor
@Component
public class ArticleDeletedEventHandler implements EventHandler<ArticleDeletedEventPayload>{

    private final ArticleQueryModelRepository articleQueryModelRepository;

    @Override
    public void handle(Event<ArticleDeletedEventPayload> event) {
        ArticleDeletedEventPayload payload = event.getPayload();
        articleQueryModelRepository.delete(payload.getArticleId());
    }

    @Override
    public boolean supports(Event<ArticleDeletedEventPayload> event) {
        return EventType.ARTICLE_DELETED == event.getType();
    }
}
