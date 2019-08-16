package myblog.user.domain;

import myblog.user.dto.UserUpdatedDto;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false, length = 20)
    private String userId;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(length = 50)
    private String email;

    private User() {
    }

    public User(String userId, String password, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.email = email;
    }

    public long getId() {
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

    public void update(UserUpdatedDto userUpdatedDto) {
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
