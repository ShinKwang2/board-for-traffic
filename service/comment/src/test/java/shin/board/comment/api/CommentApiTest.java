package shin.board.comment.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import shin.board.comment.service.response.CommentResponse;

public class CommentApiTest {

    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void create() {
        CommentResponse response1 = createComment(new CommentCreateRequest(1L, "my comment1", null, 1L));
        CommentResponse response2 = createComment(new CommentCreateRequest(1L, "my comment2", response1.getCommentId(), 1L));
        CommentResponse response3 = createComment(new CommentCreateRequest(1L, "my comment3", response1.getCommentId(), 1L));

        System.out.println("commentId=%s".formatted(response1.getCommentId()));
        System.out.println("\tcommentId=%s".formatted(response2.getCommentId()));
        System.out.println("\tcommentId=%s".formatted(response3.getCommentId()));

//        commentId=145748521034797056
//            commentId=145748522557329408
//            commentId=145748522737684480
    }

    @Test
    void read() {
        CommentResponse response = restClient.get()
                .uri("v1/comments/{commentId}", 145748521034797056L)
                .retrieve()
                .body(CommentResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void delete() {
//        commentId=145748521034797056 - x
//            commentId=145748522557329408
//            commentId=145748522737684480

        restClient.delete()
                .uri("/v1/comments/{commentId}", 145748522737684480L)
                .retrieve();
    }

    CommentResponse createComment(CommentCreateRequest request) {
        return restClient.post()
                .uri("/v1/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequest {
        private Long articleId;
        private String content;
        private Long parentCommitId;
        private Long writerId;
    }

}
