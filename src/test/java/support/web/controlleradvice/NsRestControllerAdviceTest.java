package support.web.controlleradvice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import support.ResourceNotFoundException;
import support.security.HasNotPermission;
import support.web.NsResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class NsRestControllerAdviceTest {
    private NsRestControllerAdvice advice;

    @BeforeEach
    void setUp() {
        MessageSourceAccessor msa = new MessageSourceAccessor(getMessageSource());
        advice = new NsRestControllerAdvice(msa);
    }

    @Test
    void not_found() {
        ResourceNotFoundException ex = new ResourceNotFoundException("USER", 1L);
        NsResponse nsResponse = advice.resourceNotFound(ex);
        assertThat(nsResponse.isSuccess()).isFalse();
    }

    @Test
    void has_not_permssion() {
        HasNotPermission ex = new HasNotPermission("USER", 1L, 2L);
        NsResponse nsResponse = advice.notPermission(ex);
        assertThat(nsResponse.isSuccess()).isFalse();
    }

    private MessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        return messageSource;
    }
}
