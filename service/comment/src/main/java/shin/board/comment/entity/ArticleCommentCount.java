package shin.board.comment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "article_comment_count")
public class ArticleCommentCount {

    @Id
    private Long articleId; // shard key
    private Long commentCount;

    public static ArticleCommentCount init(Long articleId, Long commentCount) {
        ArticleCommentCount articleCommentCount = new ArticleCommentCount();
        articleCommentCount.articleId = articleId;
        articleCommentCount.commentCount = commentCount;
        return articleCommentCount;
    }
}
