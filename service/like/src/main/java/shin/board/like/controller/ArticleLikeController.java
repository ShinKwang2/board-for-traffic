package shin.board.like.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shin.board.like.service.ArticleLikeService;
import shin.board.like.service.response.ArticleLikeResponse;

@RequiredArgsConstructor
@RestController
public class ArticleLikeController {

    private final ArticleLikeService articleLikeService;

    @GetMapping("/v1/article-likes/articles/{articleId}/users/{userId}")
    public ArticleLikeResponse read(
            @PathVariable("articleId") Long articleId,
            @PathVariable("userId") Long userId
    ) {
        return articleLikeService.read(articleId, userId);
    }

    @PostMapping("/v1/article-likes/articles/{articleId}/users/{userId}")
    public void like(
            @PathVariable("articleId") Long articleId,
            @PathVariable("userId") Long userId
    ) {
        articleLikeService.like(articleId, userId);
    }

    @DeleteMapping("/v1/article-likes/articles/{articleId}/users/{userId}")
    public void unlike(
            @PathVariable("articleId") Long articleId,
            @PathVariable("userId") Long userId
    ) {
        articleLikeService.unlike(articleId, userId);
    }
}
