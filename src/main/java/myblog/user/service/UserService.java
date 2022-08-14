package myblog.user.service;

import myblog.audit.domain.Audit;
import myblog.audit.domain.EventType;
import myblog.audit.service.AuditService;
import org.springframework.transaction.annotation.Transactional;
import support.ResourceNotFoundException;
import myblog.user.LoginFailedException;
import myblog.user.domain.User;
import myblog.user.domain.UserRepository;
import myblog.user.dto.SessionedUser;
import myblog.user.dto.UserCreatedDto;
import myblog.user.dto.UserUpdatedDto;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;

    private AuditService auditService;

    public UserService(UserRepository userRepository, AuditService auditService) {
        this.userRepository = userRepository;
        this.auditService = auditService;
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

    public void update(SessionedUser loginUser, long id, UserUpdatedDto userUpdatedDto) {
        User user = findById(id);
        user.update(loginUser, userUpdatedDto);
    }

    public User login(String userId, String password) {
        User user = userRepository.findByUserId(userId)
                .filter(u -> u.matchPassword(password))
                .orElse(null);
        auditService.auditEvent(new Audit(EventType.LOGIN));
        return user;
    }
}
