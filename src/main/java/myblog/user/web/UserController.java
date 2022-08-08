package myblog.user.web;

import myblog.user.domain.User;
import myblog.user.domain.UserRepository;
import myblog.user.dto.UserCreatedDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserCreatedDto userCreatedDto) {
        logger.debug("Created User : {}", userCreatedDto);
        User user = UserRepository.create(userCreatedDto);
        return ResponseEntity
                .created(URI.create("/users/" + user.getId()))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable int id) {
        return ResponseEntity.ok(UserRepository.findById(id));
    }
}
