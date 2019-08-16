package myblog.user.web;

import myblog.user.domain.User;
import myblog.user.dto.UserCreatedDto;
import myblog.user.dto.UserUpdatedDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import support.test.AcceptanceTest;
import support.test.NsWebTestClient;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserAcceptanceTest.class);

    private NsWebTestClient webClient;

    @BeforeEach
    void setUp() {
        webClient = NsWebTestClient.of(port);
    }

    @Test
    @DisplayName("사용자 회원가입/조회/수정/삭제")
    void crud() {
        // 회원가입
        UserCreatedDto expected = new UserCreatedDto("javajigi", "javajigi@nextstep.camp", "password");
        URI location = webClient.createResource("/users", expected, UserCreatedDto.class);
        logger.debug("location : {}", location);

        // 조회
        User actual = webClient.getResource(location, User.class);
        assertThat(actual.getUserId()).isEqualTo(expected.getUserId());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());

        // 수정
        UserUpdatedDto updateUser = new UserUpdatedDto("sanjigi", "sanjigi@nextstep.camp");
        webClient.basicAuth(expected.getUserId(), expected.getPassword())
                .updateResource(location, updateUser, UserUpdatedDto.class);

        actual = webClient.getResource(location, User.class);
        assertThat(actual.getUserId()).isEqualTo(updateUser.getUserId());
        assertThat(actual.getEmail()).isEqualTo(updateUser.getEmail());
    }
}
