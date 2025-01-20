package shin.board.hotarticle.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import shin.board.common.event.Event;
import shin.board.common.event.EventPayload;
import shin.board.common.event.EventType;
import shin.board.hotarticle.service.HotArticleService;

@RequiredArgsConstructor
@Slf4j
@Component
public class HotArticleEventConsumer {

    private final HotArticleService hotArticleService;

    @KafkaListener(topics = {
            EventType.Topic.SHIN_BOARD_ARTICLE,
            EventType.Topic.SHIN_BOARD_COMMENT,
            EventType.Topic.SHIN_BOARD_LIKE,
            EventType.Topic.SHIN_BOARD_VIEW
    })
    public void listen(String message, Acknowledgment ack) {
        log.info("[HotArticleEventConsumer.listen] received message={}", message);
        Event<EventPayload> event = Event.fromJson(message);
        if (event != null) {
            hotArticleService.handleEvent(event);
        }
        ack.acknowledge();
    }
}
