package myblog.user.web;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import support.test.AcceptanceTest;
import support.test.NsWebTestClient;

public class InfraAcceptanceTest extends AcceptanceTest {
    @Test
    void resource_not_found() {
        WebTestClient client = NsWebTestClient.client(port);
        client
                .get()
                .uri("/users/" + Long.MAX_VALUE)
                .exchange()
                .expectStatus().isNotFound();
    }
}
