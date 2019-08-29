package myblog.user.service;

import support.ResourceNotFoundException;
import myblog.user.LoginFailedException;
import myblog.user.domain.User;
import myblog.user.domain.UserRepository;
import myblog.user.dto.SessionedUser;
import myblog.user.dto.UserCreatedDto;
import myblog.user.dto.UserUpdatedDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(UserCreatedDto createdDto) {
        User user = new User(
                createdDto.getUserId(),
                createdDto.getPassword(),
                createdDto.getEmail());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {}

        return userRepository.save(user);
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("USER", id));
    }

    public void update(SessionedUser loginUser, long id, UserUpdatedDto userUpdatedDto) {
        User user = findById(id);
        user.update(loginUser, userUpdatedDto);
    }

    public User login(String userId, String password) {
        return userRepository.findByUserId(userId)
                .filter(u -> u.matchPassword(password))
                .orElseThrow(LoginFailedException::new);
    }
}
