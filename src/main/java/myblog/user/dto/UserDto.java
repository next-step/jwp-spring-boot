package myblog.user.dto;

public class UserDto {
    private long id;

    private String userId;

    private String email;

    private UserDto() {
    }

    public UserDto(long id, String userId, String email) {
        this.id = id;
        this.userId = userId;
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
}
