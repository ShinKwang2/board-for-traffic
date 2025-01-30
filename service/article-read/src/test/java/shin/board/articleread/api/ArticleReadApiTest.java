package shin.board.articleread.api;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import shin.board.articleread.service.response.ArticleReadResponse;

public class ArticleReadApiTest {

    RestClient restClient = RestClient.create("http://localhost:9005");

    @Test
    void readTest() {
        ArticleReadResponse response = restClient.get()
                .uri("/v1/articles/{articleId}", 145017267107520512L)
                .retrieve()
                .body(ArticleReadResponse.class);

        System.out.println("response = " + response);
    }
}
