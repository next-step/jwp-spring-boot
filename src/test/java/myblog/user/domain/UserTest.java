package myblog.user.domain;

import myblog.user.dto.SessionedUser;
import myblog.user.dto.UserUpdatedDto;
import org.junit.jupiter.api.Test;
import support.security.HasNotPermission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserTest {
    @Test
    void update() {
        User origin = new User(1, "javajigi", "javajigi@nextstep.camp", "pass");
        UserUpdatedDto updateUser = new UserUpdatedDto("sanjigi", "sanjigi@nextstep.camp");
        SessionedUser sessionedUser = new SessionedUser(origin.getId(), origin.getUserId());

        origin.update(sessionedUser, updateUser);

        assertThat(origin.getUserId()).isEqualTo(updateUser.getUserId());
        assertThat(origin.getEmail()).isEqualTo(updateUser.getEmail());
    }

    @Test
    void update_when_other_user() {
        User origin = new User(1, "javajigi", "javajigi@nextstep.camp", "pass");
        UserUpdatedDto updateUser = new UserUpdatedDto("sanjigi", "sanjigi@nextstep.camp");
        SessionedUser loginUser = new SessionedUser(2, "sanjigi");

        assertThatThrownBy(() -> {
            origin.update(loginUser, updateUser);
        }).isInstanceOf(HasNotPermission.class);
    }
}
