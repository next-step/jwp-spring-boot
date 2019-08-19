package support.web.controlleradvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import support.ResourceNotFoundException;
import support.security.HasNotPermission;
import support.security.LoginRequiredException;
import support.web.NsResponse;

@RestControllerAdvice(annotations = {RestController.class})
public class NsRestControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(NsRestControllerAdvice.class);

    private final MessageSourceAccessor messageSourceAccessor;

    public NsRestControllerAdvice(MessageSourceAccessor msa) {
        this.messageSourceAccessor = msa;
    }

    @ExceptionHandler(LoginRequiredException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public NsResponse loginRequired(LoginRequiredException ex) {
        String errorMessage = messageSourceAccessor.getMessage(ex.getErrorCode());
        logger.debug("Error Message : {}", errorMessage);
        return NsResponse.fail(errorMessage);
    }

    @ExceptionHandler(HasNotPermission.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public NsResponse notPermission(HasNotPermission ex) {
        String errorMessage = messageSourceAccessor.getMessage(ex.getErrorCode(), ex.getArgs());
        logger.debug("Error Message : {}", errorMessage);
        return NsResponse.fail(errorMessage);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public NsResponse resourceNotFound(ResourceNotFoundException ex) {
        String errorMessage = messageSourceAccessor.getMessage(ex.getErrorCode(), ex.getArgs());
        logger.debug("Error Message : {}", errorMessage);
        return NsResponse.fail(errorMessage);
    }
}
