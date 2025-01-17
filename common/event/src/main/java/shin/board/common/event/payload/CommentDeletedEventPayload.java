package shin.board.common.event.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shin.board.common.event.EventPayload;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CommentDeletedEventPayload implements EventPayload {

    private Long commentId;
    private String content;
    private String path; // v2
    private Long articleId;
    private Long writerId;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private Long articleCommentCount;
}
