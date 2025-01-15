package shin.board.view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import shin.board.view.service.ArticleViewService;

@RequiredArgsConstructor
@RestController
public class ArticleViewController {

    private final ArticleViewService articleViewService;

    @PostMapping("/v1/article-views/articles/{articleId}/users/{userId}")
    public Long increase(
            @PathVariable("articleId") Long articleId,
            @PathVariable("userId") Long userId
    ) {
        return articleViewService.increase(articleId, userId);
    }

    @GetMapping("/v1/article-views/articles/{articleId}/count")
    public Long count(@PathVariable("articleId") Long articleId) {
        return articleViewService.count(articleId);
    }
}
