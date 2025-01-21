package shin.board.view.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shin.board.common.event.EventType;
import shin.board.common.event.payload.ArticleViewedEventPayload;
import shin.board.common.outboxmessagerelay.OutboxEventPublisher;
import shin.board.view.entity.ArticleViewCount;
import shin.board.view.repository.ArticleViewCountBackUpRepository;

@RequiredArgsConstructor
@Component
public class ArticleViewCountBackUpProcessor {

    private final ArticleViewCountBackUpRepository articleViewCountBackUpRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public void backUp(Long articleId, Long viewCount) {
        int result = articleViewCountBackUpRepository.updateViewCount(articleId, viewCount);
        if (result == 0) {
            articleViewCountBackUpRepository.findById(articleId)
                    .ifPresentOrElse(ignored -> { },
                            () -> articleViewCountBackUpRepository.save(
                                    ArticleViewCount.init(articleId, viewCount)
                            )
                    );
        }
        outboxEventPublisher.publish(
                EventType.ARTICLE_VIEWED,
                ArticleViewedEventPayload.builder()
                        .articleId(articleId)
                        .articleViewCount(viewCount)
                        .build(),
                articleId
        );
    }
}
