package myblog.user.service;

import myblog.ResourceNotFoundException;
import myblog.user.LoginFailedException;
import myblog.user.domain.User;
import myblog.user.domain.UserRepository;
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
        return userRepository.save(user);
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public void update(long id, UserUpdatedDto userUpdatedDto) {
        User user = findById(id);
        user.update(userUpdatedDto);
    }

    public User login(String userId, String password) {
        return userRepository.findByUserId(userId)
                .filter(u -> u.matchPassword(password))
                .orElseThrow(LoginFailedException::new);
    }
}
