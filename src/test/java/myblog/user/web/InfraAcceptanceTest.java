package myblog.user.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.web.reactive.server.WebTestClient;
import support.ResourceNotFoundException;
import support.test.AcceptanceTest;
import support.test.NsWebTestClient;

public class InfraAcceptanceTest extends AcceptanceTest {
    @Autowired
    private MessageSourceAccessor msa;

    @Test
    void resource_not_found() {
        ResourceNotFoundException ex = new ResourceNotFoundException("USER", Long.MAX_VALUE);
        String errorMessage = msa.getMessage(ex.getErrorCode(), ex.getArgs());

        WebTestClient client = NsWebTestClient.client(port);
        client
                .get()
                .uri("/users/" + Long.MAX_VALUE)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.success").isEqualTo(false)
                .jsonPath("$.errorMessage").isEqualTo(errorMessage);
    }
}
