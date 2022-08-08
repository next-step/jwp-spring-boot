package myblog.user.domain;

import myblog.user.dto.UserCreatedDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static List<User> users = new ArrayList<>();

    public static User create(UserCreatedDto createdDto) {
        Optional<User> maybeUser = findByUserId(createdDto.getUserId());
        if (maybeUser.isPresent()) {
            return maybeUser.get();
        }

        User user = new User(users.size() + 1,
                createdDto.getUserId(),
                createdDto.getEmail(),
                createdDto.getPassword());
        users.add(user);
        return user;
    }

    public static User findById(int id) {
        return users.get(id - 1);
    }

    public static Optional<User> findByUserId(String userId) {
        return users.stream()
                .filter(u -> u.matchUserId(userId))
                .findFirst();
    }
}
