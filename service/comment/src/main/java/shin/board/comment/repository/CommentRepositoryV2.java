package shin.board.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shin.board.comment.entity.CommentV2;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepositoryV2 extends JpaRepository<CommentV2, Long> {

    @Query("SELECT c FROM CommentV2 c WHERE c.commentPath.path = :path")
    Optional<CommentV2> findByPath(@Param("path") String path);

    @Query(
            value = "SELECT path FROM comment_v2 " +
                    "WHERE article_id = :articleId AND path > :pathPrefix AND path like :pathPrefix% " +
                    "ORDER BY path DESC LIMIT 1",
            nativeQuery = true
    )
    Optional<String> findDescendantsTopPath(
            @Param("articleId") Long articleId,
            @Param("pathPrefix") String pathPrefix
    );
}
