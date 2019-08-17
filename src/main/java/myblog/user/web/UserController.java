package myblog.user.web;

import myblog.user.domain.User;
import myblog.user.dto.SessionedUser;
import myblog.user.dto.UserCreatedDto;
import myblog.user.dto.UserUpdatedDto;
import myblog.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import support.web.argumentresolver.LoginUser;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserCreatedDto userCreatedDto) {
        logger.debug("Created User : {}", userCreatedDto);
        User user = userService.create(userCreatedDto);
        return ResponseEntity
                .created(URI.create("/users/" + user.getId()))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @LoginUser SessionedUser loginUser,
            @PathVariable long id,
            @RequestBody UserUpdatedDto userUpdatedDto) {
        logger.debug("Updated User : {}", userUpdatedDto);
        userService.update(loginUser, id, userUpdatedDto);
        return ResponseEntity.ok().build();
    }
}
