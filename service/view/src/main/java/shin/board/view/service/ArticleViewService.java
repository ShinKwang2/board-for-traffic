package shin.board.view.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shin.board.view.repository.ArticleViewCountBackUpRepository;
import shin.board.view.repository.ArticleViewCountRepository;

@RequiredArgsConstructor
@Service
public class ArticleViewService {

    private static final int BACK_UP_BATCH_SIZE = 100;

    private final ArticleViewCountRepository articleViewCountRepository;
    private final ArticleViewCountBackUpProcessor articleViewCountBackUpProcessor;


    public Long increase(Long articleId, Long userId) {
        Long count = articleViewCountRepository.increase(articleId);
        if (count % BACK_UP_BATCH_SIZE == 0) {
            articleViewCountBackUpProcessor.backUp(articleId, count);
        }
        return count;
    }

    public Long count(Long articleId) {
        return articleViewCountRepository.read(articleId);
    }
}
