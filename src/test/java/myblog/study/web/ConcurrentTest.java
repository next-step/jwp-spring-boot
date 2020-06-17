package myblog.study.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import support.test.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.CONCURRENT)
public class ConcurrentTest extends AcceptanceTest {
    @Autowired
    private WebTestClient client;

    @Test
    void myName1() {
        myNameRequest("javajigi");
    }

    @Test
    void myName2() {
        myNameRequest("pobi");
    }

    private void myNameRequest(String name) {
        EntityExchangeResult<String> response = client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                        .path("/my-name")
                        .queryParam("name", name)
                        .build()
                    )
                .exchange()
                    .expectStatus()
                    .isOk()
                .expectHeader()
                .cacheControl(CacheControl.empty())
                    .expectBody(String.class)
                    .returnResult();

        String responseBody = response.getResponseBody();
        assertThat(responseBody).isEqualTo("My name is " + name);
    }
}
