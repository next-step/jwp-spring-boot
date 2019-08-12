package myblog.user.dto;

public class SessionedUser {
    public static final String SESSIONED_USER_KEY = "sessionedUser";

    public static final SessionedUser GUEST = new GuestUser();

    private int id;

    private String userId;

    private SessionedUser() {}

    public SessionedUser(int id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isGuest() {
        return true;
    }

    private static class GuestUser extends SessionedUser {
        @Override
        public boolean isGuest() {
            return false;
        }
    }
}
