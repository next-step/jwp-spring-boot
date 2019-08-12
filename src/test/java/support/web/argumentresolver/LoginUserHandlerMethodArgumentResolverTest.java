package support.web.argumentresolver;

import myblog.user.dto.SessionedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import support.security.LoginRequiredException;

import java.lang.annotation.Annotation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginUserHandlerMethodArgumentResolverTest {
    @Mock
    private MethodParameter parameter;

    @Mock
    private NativeWebRequest webRequest;

    private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;

    @BeforeEach
    void setUp() {
        loginUserHandlerMethodArgumentResolver = new LoginUserHandlerMethodArgumentResolver();
    }

    @Test
    public void supportsParameter_false() {
        when(parameter.hasParameterAnnotation(LoginUser.class)).thenReturn(false);
        assertThat(loginUserHandlerMethodArgumentResolver.supportsParameter(parameter)).isFalse();
    }

    @Test
    public void supportsParameter_true() {
        when(parameter.hasParameterAnnotation(LoginUser.class)).thenReturn(true);
        assertThat(loginUserHandlerMethodArgumentResolver.supportsParameter(parameter)).isTrue();
    }

    @Test
    public void loginUserRequired_but_guest() {
        when(parameter.getParameterAnnotation(LoginUser.class)).thenReturn( new FakeLoginUser(true));
        when(webRequest.getAttribute(SessionedUser.SESSIONED_USER_KEY, NativeWebRequest.SCOPE_SESSION)).thenReturn(null);

        assertThatThrownBy(() -> {
            loginUserHandlerMethodArgumentResolver.resolveArgument(parameter, null, webRequest, null);
        }).isInstanceOf(LoginRequiredException.class);
    }

    @Test
    public void loginUser_not_required() throws Exception {
        when(parameter.getParameterAnnotation(LoginUser.class)).thenReturn( new FakeLoginUser(false));
        when(webRequest.getAttribute(SessionedUser.SESSIONED_USER_KEY, NativeWebRequest.SCOPE_SESSION)).thenReturn(SessionedUser.GUEST);

        SessionedUser sessionedUser = (SessionedUser)loginUserHandlerMethodArgumentResolver.resolveArgument(parameter, null, webRequest, null);
        assertThat(sessionedUser).isEqualTo(SessionedUser.GUEST);
    }

    @Test
    public void loginUser_일반상황() throws Exception {
        SessionedUser expected = new SessionedUser(1, "javajigi");
        when(parameter.getParameterAnnotation(LoginUser.class)).thenReturn( new FakeLoginUser(false));
        when(webRequest.getAttribute(SessionedUser.SESSIONED_USER_KEY, NativeWebRequest.SCOPE_SESSION)).thenReturn(expected);

        SessionedUser actual = (SessionedUser)loginUserHandlerMethodArgumentResolver.resolveArgument(parameter, null, webRequest, null);
        assertThat(actual).isEqualTo(expected);
    }

    public static class FakeLoginUser implements LoginUser {
        private boolean requiredValue = true;

        public FakeLoginUser(boolean requiredValue) {
            this.requiredValue = requiredValue;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return LoginUser.class;
        }

        @Override
        public boolean required() {
            return requiredValue;
        }
    }
}
