package shin.board.comment.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import shin.board.comment.service.response.CommentResponse;

public class CommentApiV2Test {

    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void create() {
        CommentResponse response1 = create(new CommentCreateRequestV2(1L, "my comment1", null, 1L));
        CommentResponse response2 = create(new CommentCreateRequestV2(1L, "my comment1", response1.getPath(), 1L));
        CommentResponse response3 = create(new CommentCreateRequestV2(1L, "my comment1", response2.getPath(), 1L));

        System.out.println("response1.getCommentId() = " + response1.getCommentId());
        System.out.println("\tresponse2.getCommentId() = " + response2.getCommentId());
        System.out.println("\t\tresponse3.getCommentId() = " + response3.getCommentId());

        /**
         * response1.getCommentId() = 147206904353308672
         * 	response2.getCommentId() = 147206906836336640
         * 		response3.getCommentId() = 147206907176075264
         */
    }

    CommentResponse create(CommentCreateRequestV2 request) {
        return restClient.post()
                .uri("/v2/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Test
    void read() {
        CommentResponse response = restClient.get()
                .uri("/v2/comments/{commentId}", 147206907176075264L)
                .retrieve()
                .body(CommentResponse.class);
        System.out.println("response = " + response);
    }

    @Test
    void delete() {
        restClient.delete()
                .uri("/v2/comments/{commentId}", 147206907176075264L)
                .retrieve();
    }

    @Getter
    @AllArgsConstructor
    public class CommentCreateRequestV2 {
        private Long articleId;
        private String content;
        private String parentPath;
        private Long writerId;
    }
}
