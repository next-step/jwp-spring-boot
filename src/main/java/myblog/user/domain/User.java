package myblog.user.domain;

import myblog.user.dto.SessionedUser;
import myblog.user.dto.UserUpdatedDto;
import support.security.HasNotPermission;

public class User {
    private int id;

    private String userId;

    private String email;

    private String password;

    private User() {
    }

    public User(int id, String userId, String email, String password) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void update(SessionedUser loginUser, UserUpdatedDto userUpdatedDto) {
        if (!loginUser.isOwner(id)) {
            throw new HasNotPermission();
        }

        this.userId = userUpdatedDto.getUserId();
        this.email = userUpdatedDto.getEmail();
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public boolean matchUserId(String userId) {
        return this.userId.equals(userId);
    }
}
