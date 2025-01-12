package shin.board.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shin.board.article.entity.BoardArticleCount;

@Repository
public interface BoardArticleCountRepository extends JpaRepository<BoardArticleCount, Long> {

    @Query(
            value = "UPDATE board_article_count SET article_count = article_count + 1 " +
                    "WHERE board_id = :boardId",
            nativeQuery = true
    )
    @Modifying
    int increase(@Param("boardId") Long boardId);

    @Query(
            value = "UPDATE board_article_count SET article_count = article_count - 1 " +
                    "WHERE board_id = :boardId",
            nativeQuery = true
    )
    @Modifying
    int decrease(@Param("boardId") Long boardId);
}
