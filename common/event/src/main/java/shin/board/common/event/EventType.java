package shin.board.common.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shin.board.common.event.payload.*;

@RequiredArgsConstructor
@Slf4j
@Getter
public enum EventType {

    ARTICLE_CREATED(ArticleCreatedEventPayload.class, Topic.SHIN_BOARD_ARTICLE),
    ARTICLE_UPDATED(ArticleUpdatedEventPayload.class, Topic.SHIN_BOARD_ARTICLE),
    ARTICLE_DELETED(ArticleDeletedEventPayload.class, Topic.SHIN_BOARD_ARTICLE),
    COMMENT_CREATED(CommentCreatedEventPayload.class, Topic.SHIN_BOARD_COMMENT),
    COMMENT_DELETED(CommentDeletedEventPayload.class, Topic.SHIN_BOARD_COMMENT),
    ARTICLE_LIKED(ArticleLikedEventPayload.class, Topic.SHIN_BOARD_LIKE),
    ARTICLE_UNLIKED(ArticleUnlikedEventPayload.class, Topic.SHIN_BOARD_LIKE),
    ARTICLE_VIEWED(ArticleViewedEventPayload.class, Topic.SHIN_BOARD_VIEW);

    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type) {
        try {
            return valueOf(type);
        } catch (Exception e) {
            log.error("[EventType.from] type={}", type, e);
            return null;
        }
    }

    public static class Topic {
        public static final String SHIN_BOARD_ARTICLE = "shin-board-article";
        public static final String SHIN_BOARD_COMMENT = "shin-board-comment";
        public static final String SHIN_BOARD_LIKE = "shin-board-like";
        public static final String SHIN_BOARD_VIEW = "shin-board-view";
    }
}
