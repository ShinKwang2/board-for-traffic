package shin.board.hotarticle.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shin.board.hotarticle.repository.ArticleCommentCountRepository;
import shin.board.hotarticle.repository.ArticleLikeCountRepository;
import shin.board.hotarticle.repository.ArticleViewCountRepository;

import java.util.random.RandomGenerator;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class HotArticleScoreCalculatorTest {

    @InjectMocks
    HotArticleScoreCalculator hotArticleScoreCalculator;

    @Mock
    ArticleLikeCountRepository articleLikeCountRepository;
    @Mock
    ArticleCommentCountRepository articleCommentCountRepository;
    @Mock
    ArticleViewCountRepository articleViewCountRepository;

    @Test
    void calculateTest() {
        // given
        Long articleId = 1L;
        long likeCount = RandomGenerator.getDefault().nextLong(100);
        long commentCount = RandomGenerator.getDefault().nextLong(100);
        long viewCount = RandomGenerator.getDefault().nextLong(100);

        given(articleLikeCountRepository.read(articleId)).willReturn(likeCount);
        given(articleCommentCountRepository.read(articleId)).willReturn(commentCount);
        given(articleViewCountRepository.read(articleId)).willReturn(viewCount);

        // when
        long score = hotArticleScoreCalculator.calculate(articleId);

        assertThat(score)
                .isEqualTo(3 * likeCount + 2 * commentCount + 1 * viewCount);
    }
}