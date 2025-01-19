package shin.board.hotarticle.service.eventhandler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shin.board.common.event.Event;
import shin.board.common.event.EventType;
import shin.board.common.event.payload.CommentCreatedEventPayload;
import shin.board.hotarticle.repository.ArticleCommentCountRepository;
import shin.board.hotarticle.utils.TimeCalculatorUtils;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class CommentCreatedEventHandler implements EventHandler<CommentCreatedEventPayload>{

    private final ArticleCommentCountRepository articleCommentCountRepository;

    @Override
    public void handle(Event<CommentCreatedEventPayload> event) {
        CommentCreatedEventPayload payload = event.getPayload();
        articleCommentCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleCommentCount(),
                TimeCalculatorUtils.calculateDurationToMidnight(LocalDateTime.now())
        );
    }

    @Override
    public boolean supports(Event<CommentCreatedEventPayload> event) {
        return EventType.COMMENT_CREATED == event.getType();
    }

    @Override
    public Long findArticleId(Event<CommentCreatedEventPayload> event) {
        return event.getPayload().getArticleId();
    }
}
