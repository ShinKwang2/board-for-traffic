package shin.board.articleread.service.event.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shin.board.articleread.repository.ArticleQueryModel;
import shin.board.articleread.repository.ArticleQueryModelRepository;
import shin.board.common.event.Event;
import shin.board.common.event.EventType;
import shin.board.common.event.payload.ArticleCreatedEventPayload;
import shin.board.common.event.payload.ArticleUpdatedEventPayload;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class ArticleUpdatedEventHandler implements EventHandler<ArticleUpdatedEventPayload>{

    private final ArticleQueryModelRepository articleQueryModelRepository;

    @Override
    public void handle(Event<ArticleUpdatedEventPayload> event) {
        ArticleUpdatedEventPayload payload = event.getPayload();
        articleQueryModelRepository.read(payload.getArticleId())
                .ifPresent(articleQueryModel -> {
                    articleQueryModel.updateBy(payload);
                    articleQueryModelRepository.update(articleQueryModel);
                });
    }

    @Override
    public boolean supports(Event<ArticleUpdatedEventPayload> event) {
        return EventType.ARTICLE_UPDATED == event.getType();
    }
}
