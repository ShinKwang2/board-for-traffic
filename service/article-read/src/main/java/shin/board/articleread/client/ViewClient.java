package shin.board.articleread.client;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import shin.board.articleread.cache.aop.OptimizedCacheable;

@Slf4j
@RequiredArgsConstructor
@Component
public class ViewClient {

    private RestClient restClient;

    @Value("${endpoints.shin-board-view-service.url}")
    private String viewServiceUrl;

    @PostConstruct
    public void initRestClient() {
        restClient = RestClient.create(viewServiceUrl);
    }

    // 레디스에서 데이터를 조회해본다.
    // 레디스에 데이터가 없다면, count 메소드 내부 로직이 호출되면서, viewService 원본 데이터를 요청한다. 그리고 레디스에 데이터를 넣고 응답한다.
    // 레디스에 데이터가 있으면, 그 데이터를 그대로 반환한다.
    //@Cacheable(key = "#articleId", value = "articleViewCount")
    @OptimizedCacheable(type = "articleViewCount", ttlSeconds = 1)
    public long count(Long articleId) {
        log.info("[ViewClient.count] articleId={}", articleId);
        try {
            return restClient.get()
                    .uri("/v1/article-views/articles/{articleId}/count", articleId)
                    .retrieve()
                    .body(Long.class);
        } catch (Exception e) {
            log.error("[ViewClient.count] article={}", articleId, e);
            return 0L;
        }
    }
}
