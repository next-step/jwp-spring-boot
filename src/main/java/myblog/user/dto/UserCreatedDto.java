package myblog.user.dto;

public class UserCreatedDto {
    private String userId;
    private String email;
    private String password;

    private UserCreatedDto() {
    }

    public UserCreatedDto(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
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

    @Override
    public String toString() {
        return "UserCreatedDto{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
