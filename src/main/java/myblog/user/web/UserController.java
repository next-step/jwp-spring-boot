package myblog.user.web;

import myblog.user.dto.UserCreatedDto;
import myblog.user.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserCreatedDto userCreatedDto;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserCreatedDto userCreatedDto) {
        logger.debug("Created User : {}", userCreatedDto);
        this.userCreatedDto = userCreatedDto;
        return ResponseEntity
                .created(URI.create("/users/1"))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> show(@PathVariable long id) {
        return ResponseEntity.ok(new UserDto(1, userCreatedDto.getUserId(), userCreatedDto.getEmail()));
    }
}
