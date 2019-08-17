package support.web.controlleradvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import support.ResourceNotFoundException;
import support.security.HasNotPermission;
import support.security.LoginRequiredException;

@RestControllerAdvice(annotations = {RestController.class})
public class NsRestControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(NsRestControllerAdvice.class);

    @ExceptionHandler(LoginRequiredException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public void loginRequired() {
        logger.debug("LoginRequiredException is happened!");
    }

    @ExceptionHandler(HasNotPermission.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public void notPermission() {
        logger.debug("HasNotPermission is happened!");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void emptyResultData() {
        logger.debug("ResourceNotFoundException is happened!");
    }
}
