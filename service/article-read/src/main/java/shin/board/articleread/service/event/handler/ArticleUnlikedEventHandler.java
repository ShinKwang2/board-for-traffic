package shin.board.articleread.service.event.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shin.board.articleread.repository.ArticleQueryModelRepository;
import shin.board.common.event.Event;
import shin.board.common.event.EventType;
import shin.board.common.event.payload.ArticleLikedEventPayload;
import shin.board.common.event.payload.ArticleUnlikedEventPayload;

@RequiredArgsConstructor
@Component
public class ArticleUnlikedEventHandler implements EventHandler<ArticleUnlikedEventPayload>{

    private final ArticleQueryModelRepository articleQueryModelRepository;

    @Override
    public void handle(Event<ArticleUnlikedEventPayload> event) {
        ArticleUnlikedEventPayload payload = event.getPayload();
        articleQueryModelRepository.read(payload.getArticleId())
                .ifPresent(articleQueryModel -> {
                    articleQueryModel.updateBy(payload);
                    articleQueryModelRepository.update(articleQueryModel);
                });
    }

    @Override
    public boolean supports(Event<ArticleUnlikedEventPayload> event) {
        return EventType.ARTICLE_UNLIKED == event.getType();
    }
}
