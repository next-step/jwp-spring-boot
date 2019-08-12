package support.web.argumentresolver;

import myblog.user.dto.SessionedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import support.security.LoginRequiredException;

@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private Logger logger = LoggerFactory.getLogger(LoginUserHandlerMethodArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        LoginUser loginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class);

        SessionedUser sessionedUser = (SessionedUser) webRequest.getAttribute(
                SessionedUser.SESSIONED_USER_KEY,
                NativeWebRequest.SCOPE_SESSION);
        logger.debug("@LoginUser : {}", sessionedUser);
        if (loginUserAnnotation.required() && sessionedUser.isGuest()) {
            throw new LoginRequiredException();
        }

        return sessionedUser;
    }
}
