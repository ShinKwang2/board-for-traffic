package shin.board.articleread.service.event.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shin.board.articleread.repository.ArticleQueryModelRepository;
import shin.board.common.event.Event;
import shin.board.common.event.EventType;
import shin.board.common.event.payload.ArticleDeletedEventPayload;
import shin.board.common.event.payload.CommentCreatedEventPayload;

@RequiredArgsConstructor
@Component
public class CommentCreatedEventHandler implements EventHandler<CommentCreatedEventPayload>{

    private final ArticleQueryModelRepository articleQueryModelRepository;

    @Override
    public void handle(Event<CommentCreatedEventPayload> event) {
        CommentCreatedEventPayload payload = event.getPayload();
        articleQueryModelRepository.read(payload.getArticleId())
                .ifPresent(articleQueryModel -> {
                    articleQueryModel.updateBy(payload);
                    articleQueryModelRepository.update(articleQueryModel);
                });
    }

    @Override
    public boolean supports(Event<CommentCreatedEventPayload> event) {
        return EventType.COMMENT_CREATED == event.getType();
    }
}
