package myblog.user.web;

import myblog.user.domain.User;
import myblog.user.dto.UserCreatedDto;
import myblog.user.dto.UserUpdatedDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import support.test.AcceptanceTest;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

public class UserAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserAcceptanceTest.class);

    @Value("${local.server.port}")
    private int port;

    @Test
    @DisplayName("사용자 회원가입/조회/수정/삭제")
    void crud() {
        // 회원가입
        UserCreatedDto expected = new UserCreatedDto("javajigi", "javajigi@nextstep.camp", "password");
        EntityExchangeResult<byte[]> response = clientBuilder().build()
                .post()
                .uri("/users")
                .body(Mono.just(expected), UserCreatedDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult();
        URI location = response.getResponseHeaders().getLocation();
        logger.debug("location : {}", location);

        // 조회
        User actual = clientBuilder().build()
                .get()
                .uri(location.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .returnResult().getResponseBody();
        assertThat(actual.getUserId()).isEqualTo(expected.getUserId());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());

        // 수정
        UserUpdatedDto updateUser = new UserUpdatedDto("sanjigi", "sanjigi@nextstep.camp");
        clientBuilder()
                .filter(basicAuthentication(expected.getUserId(), expected.getPassword()))
                .build()
                .put()
                .uri(location.toString())
                .body(Mono.just(updateUser), UserUpdatedDto.class)
                .exchange()
                .expectStatus().isOk();


        actual = clientBuilder().build()
                .get()
                .uri(location.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .returnResult().getResponseBody();
        assertThat(actual.getUserId()).isEqualTo(updateUser.getUserId());
        assertThat(actual.getEmail()).isEqualTo(updateUser.getEmail());
    }

    private WebTestClient.Builder clientBuilder() {
        return WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port);
    }
}
