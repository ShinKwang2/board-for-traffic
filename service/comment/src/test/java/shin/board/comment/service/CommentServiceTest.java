package shin.board.comment.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import shin.board.comment.entity.Comment;
import shin.board.comment.repository.CommentRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    CommentRepository commentRepository;

    @Test
    @DisplayName("삭제할 댓글이 자식이 있으면, 삭제 표시만 한다.")
    void deleteShouldMarkDeletedIfHasChildren() {
        // given
        Long articleId = 1L;
        Long commentId = 2L;
        Comment comment = createComment(articleId, commentId);
        BDDMockito.given(commentRepository.findById(commentId))
                .willReturn(Optional.of(comment));
        BDDMockito.given(commentRepository.countBy(articleId, commentId, 2L)).willReturn(2L);

        // when
        commentService.delete(commentId);

        // then
        BDDMockito.verify(comment).delete();
    }

    @Test
    @DisplayName("하위 댓글이 삭제되고, 삭제되지 않은 부모면, 하위 댓글만 삭제한다.")
    void deleteShouldDeleteChildrenOnlyIfNotDeletedParent() {
        // given
        Long articleId = 1L;
        Long commentId = 2L;
        Long parentCommentId = 1L;
        Comment comment = createComment(articleId, commentId, parentCommentId);
        BDDMockito.given(comment.isRoot()).willReturn(false);

        Comment parentComment = Mockito.mock(Comment.class);
        BDDMockito.given(parentComment.getDeleted()).willReturn(false);

        BDDMockito.given(commentRepository.findById(commentId))
                .willReturn(Optional.of(comment));
        BDDMockito.given(commentRepository.countBy(articleId, commentId, 2L)).willReturn(1L);

        BDDMockito.given(commentRepository.findById(parentCommentId))
                .willReturn(Optional.of(parentComment));

        // when
        commentService.delete(commentId);

        // then
        BDDMockito.verify(commentRepository).delete(comment);
        BDDMockito.verify(commentRepository, Mockito.never()).delete(parentComment);
    }

    @Test
    @DisplayName("하위 댓글이 모두 삭제되고, 삭제된 부모면, 재귀적으로 모두 삭제한다.")
    void deleteShouldDeleteAllRecursively() {
        // given
        Long articleId = 1L;
        Long commentId = 2L;
        Long parentCommentId = 1L;

        Comment comment = createComment(articleId, commentId, parentCommentId);
        BDDMockito.given(comment.isRoot()).willReturn(false);

        Comment parentComment = createComment(articleId, parentCommentId);
        BDDMockito.given(parentComment.isRoot()).willReturn(true);
        BDDMockito.given(parentComment.getDeleted()).willReturn(true);

        BDDMockito.given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));
        BDDMockito.given(commentRepository.countBy(articleId, commentId, 2L)).willReturn(1L);

        BDDMockito.given(commentRepository.findById(parentCommentId)).willReturn(Optional.of(parentComment));
        BDDMockito.given(commentRepository.countBy(articleId, parentCommentId, 2L)).willReturn(1L);

        // when
        commentService.delete(commentId);

        // then
        BDDMockito.verify(commentRepository).delete(comment);
        BDDMockito.verify(commentRepository).delete(parentComment);
    }

    private Comment createComment(Long articleId, Long commentId) {
        Comment comment = Mockito.mock(Comment.class);
        BDDMockito.given(comment.getArticleId()).willReturn(articleId);
        BDDMockito.given(comment.getCommentId()).willReturn(commentId);
        return comment;
    }

    private Comment createComment(Long articleId, Long commentId, Long parentCommentId) {
        Comment comment = createComment(articleId, commentId);
        BDDMockito.given(comment.getParentCommentId()).willReturn(parentCommentId);
        return comment;
    }
}