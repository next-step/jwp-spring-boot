package myblog.user.dto;

public class UserUpdatedDto {
    private String userId;
    private String email;

    private UserUpdatedDto() {
    }

    public UserUpdatedDto(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}
