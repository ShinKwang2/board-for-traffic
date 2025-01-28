package shin.board.articleread.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import shin.board.articleread.service.ArticleReadService;
import shin.board.common.event.Event;
import shin.board.common.event.EventPayload;
import shin.board.common.event.EventType;

@Slf4j
@RequiredArgsConstructor
@Component
public class ArticleReadEventConsumer {

    private final ArticleReadService articleReadService;

    @KafkaListener(topics = {
            EventType.Topic.SHIN_BOARD_ARTICLE,
            EventType.Topic.SHIN_BOARD_COMMENT,
            EventType.Topic.SHIN_BOARD_LIKE,
    })
    public void listen(String message, Acknowledgment ack) {
        log.info("[ArticleReadEventConsumer.listen] message={}", message);
        Event<EventPayload> event = Event.fromJson(message);
        if (event != null) {
            articleReadService.handleEvent(event);
        }
        ack.acknowledge();
    }
}
