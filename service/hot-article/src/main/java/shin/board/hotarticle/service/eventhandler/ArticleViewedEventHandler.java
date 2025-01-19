package shin.board.hotarticle.service.eventhandler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shin.board.common.event.Event;
import shin.board.common.event.EventType;
import shin.board.common.event.payload.ArticleViewedEventPayload;
import shin.board.hotarticle.repository.ArticleViewCountRepository;
import shin.board.hotarticle.utils.TimeCalculatorUtils;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ArticleViewedEventHandler implements EventHandler<ArticleViewedEventPayload> {

    private final ArticleViewCountRepository articleViewCountRepository;

    @Override
    public void handle(Event<ArticleViewedEventPayload> event) {
        ArticleViewedEventPayload payload = event.getPayload();
        articleViewCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleViewCount(),
                TimeCalculatorUtils.calculateDurationToMidnight(LocalDateTime.now())
        );
    }

    @Override
    public boolean supports(Event<ArticleViewedEventPayload> event) {
        return EventType.ARTICLE_VIEWED == event.getType();
    }

    @Override
    public Long findArticleId(Event<ArticleViewedEventPayload> event) {
        return event.getPayload().getArticleId();
    }
}
