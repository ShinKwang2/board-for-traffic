package shin.board.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shin.board.comment.entity.ArticleCommentCount;

public interface ArticleCommentCountRepository extends JpaRepository<ArticleCommentCount, Long> {

    @Query(
            value = "UPDATE article_comment_count SET comment_count = comment_count + 1 " +
                    "WHERE article_id = :articleId",
            nativeQuery = true
    )
    @Modifying
    int increase(@Param("articleId") Long articleId);

    @Query(
            value = "UPDATE article_comment_count SET comment_count = comment_count - 1 " +
                    "WHERE article_id = :articleId",
            nativeQuery = true
    )
    @Modifying
    int decrease(@Param("articleId") Long articleId);
}
