package shin.board.common.event.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shin.board.common.event.EventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ArticleViewedEventPayload implements EventPayload {

    private Long articleId;
    private Long articleViewCount;
}
