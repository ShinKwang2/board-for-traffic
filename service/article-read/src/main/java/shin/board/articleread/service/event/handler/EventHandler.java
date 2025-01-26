package shin.board.articleread.service.event.handler;

import shin.board.common.event.Event;
import shin.board.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {

    void handle(Event<T> event);

    boolean supports(Event<T> event);
}
