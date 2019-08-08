package myblog.user.web;

import myblog.user.dto.UserCreatedDto;
import myblog.user.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import support.test.AcceptanceTest;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserAcceptanceTest.class);

    @Autowired
    private WebTestClient client;

    @Test
    @DisplayName("사용자 회원가입/조회/수정/삭제")
    void crud() {
        UserCreatedDto expected = new UserCreatedDto("javajigi", "javajigi@nextstep.camp", "password");
        EntityExchangeResult<byte[]> response = client
                .post()
                .uri("/users")
                .body(Mono.just(expected), UserCreatedDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult();

        URI location = response.getResponseHeaders().getLocation();
        logger.debug("location : {}", location);
        UserDto actual = client
                .get()
                .uri(location.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .returnResult().getResponseBody();

        assertThat(actual.getUserId()).isEqualTo(expected.getUserId());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
    }
}
