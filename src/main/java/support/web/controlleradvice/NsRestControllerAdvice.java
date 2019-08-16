package support.web.controlleradvice;

import myblog.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NsRestControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(NsRestControllerAdvice.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void emptyResultData() {
        logger.debug("ResourceNotFoundException is happened!");
    }
}
