package myblog.user.web;

import myblog.user.domain.User;
import myblog.user.dto.UserCreatedDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserAcceptanceTest.class);

    @Autowired
    private WebTestClient webClient;

    @DisplayName("사용자 회원가입/조회/수정/삭제")
    @Test
    void crud() {
        // 회원가입
        UserCreatedDto expected = new UserCreatedDto("javajigi", "javajigi@nextstep.camp", "password");
        EntityExchangeResult<byte[]> response = webClient
                .post()
                .uri("/users")
                .body(Mono.just(expected), UserCreatedDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult();

        String location = response.getResponseHeaders().getLocation().toString();
        logger.debug("location : {}", location);

        // 조회
        User actual = webClient
                .get()
                .uri(location.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .returnResult().getResponseBody();
        assertThat(actual.getUserId()).isEqualTo(expected.getUserId());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
    }
}
