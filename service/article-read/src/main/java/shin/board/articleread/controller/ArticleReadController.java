package shin.board.articleread.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import shin.board.articleread.service.ArticleReadService;
import shin.board.articleread.service.response.ArticleReadResponse;

@RequiredArgsConstructor
@RestController
public class ArticleReadController {

    private final ArticleReadService articleReadService;

    @GetMapping("/v1/articles/{articleId}")
    public ArticleReadResponse read(@PathVariable("articleId") Long articleId) {
        return articleReadService.read(articleId);
    }
}
