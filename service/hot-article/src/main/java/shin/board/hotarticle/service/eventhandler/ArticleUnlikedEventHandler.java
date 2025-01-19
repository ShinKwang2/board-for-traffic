package shin.board.hotarticle.service.eventhandler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shin.board.common.event.Event;
import shin.board.common.event.EventType;
import shin.board.common.event.payload.ArticleUnlikedEventPayload;
import shin.board.hotarticle.repository.ArticleLikeCountRepository;
import shin.board.hotarticle.utils.TimeCalculatorUtils;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ArticleUnlikedEventHandler implements EventHandler<ArticleUnlikedEventPayload> {

    private final ArticleLikeCountRepository articleLikeCountRepository;

    @Override
    public void handle(Event<ArticleUnlikedEventPayload> event) {
        ArticleUnlikedEventPayload payload = event.getPayload();
        articleLikeCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleLikeCount(),
                TimeCalculatorUtils.calculateDurationToMidnight(LocalDateTime.now())
        );
    }

    @Override
    public boolean supports(Event<ArticleUnlikedEventPayload> event) {
        return EventType.ARTICLE_UNLIKED == event.getType();
    }

    @Override
    public Long findArticleId(Event<ArticleUnlikedEventPayload> event) {
        return event.getPayload().getArticleId();
    }
}
