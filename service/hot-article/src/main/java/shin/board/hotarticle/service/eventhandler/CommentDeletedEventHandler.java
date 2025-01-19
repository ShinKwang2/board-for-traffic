package shin.board.hotarticle.service.eventhandler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shin.board.common.event.Event;
import shin.board.common.event.EventType;
import shin.board.common.event.payload.CommentDeletedEventPayload;
import shin.board.hotarticle.repository.ArticleCommentCountRepository;
import shin.board.hotarticle.utils.TimeCalculatorUtils;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class CommentDeletedEventHandler implements EventHandler<CommentDeletedEventPayload> {

    private final ArticleCommentCountRepository articleCommentCountRepository;

    @Override
    public void handle(Event<CommentDeletedEventPayload> event) {
        CommentDeletedEventPayload payload = event.getPayload();
        articleCommentCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleCommentCount(),
                TimeCalculatorUtils.calculateDurationToMidnight(LocalDateTime.now())
        );
    }

    @Override
    public boolean supports(Event<CommentDeletedEventPayload> event) {
        return EventType.COMMENT_DELETED == event.getType();
    }

    @Override
    public Long findArticleId(Event<CommentDeletedEventPayload> event) {
        return event.getPayload().getArticleId();
    }
}
