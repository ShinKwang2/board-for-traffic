package shin.board.article.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import shin.board.article.service.response.ArticlePageResponse;
import shin.board.article.service.response.ArticleResponse;

public class ArticleApiTest {

    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createArticle() {
        ArticleResponse response = create(new ArticleCreateRequest(
                "hi", "my content", 1L, 1L
        ));
        System.out.println(response);
    }

    ArticleResponse create(ArticleCreateRequest request) {
        return restClient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void readArticle() {
        ArticleResponse response = read(145012452045713408L);
        System.out.println("response = " + response);
    }

    ArticleResponse read(Long articleId) {
        return restClient.get()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void updateArticle() {
        update(145012452045713408L);
        ArticleResponse response = read(145012452045713408L);
        System.out.println("response = " + response);
    }
    void update(Long articleId) {
        restClient.put()
                .uri("/v1/articles/{articleId}", articleId)
                .body(new ArticleUpdateRequest("hi 2", "my content 22"))
                .retrieve();
    }

    @Test
    void deleteArticle() {
        restClient.delete()
                .uri("/v1/articles/{articleId}", 145012452045713408L)
                .retrieve();
    }

    @Test
    void readAllTest() {
        ArticlePageResponse response = restClient.get()
                .uri("/v1/articles?boardId=1&pageSize=30&page=50000")
                .retrieve()
                .body(ArticlePageResponse.class);

        System.out.println("response.getArticleCount() = " + response.getArticleCount());
        for (ArticleResponse article : response.getArticles()) {
            System.out.println("article = " + article.getArticleId());
        }
    }

    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest {

        private String title;
        private String content;
        private Long writerId;
        private Long boardId;
    }

    @Getter
    @AllArgsConstructor
    static class ArticleUpdateRequest {

        private String title;
        private String content;
    }

}
