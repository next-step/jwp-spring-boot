package support.web;

import myblog.user.domain.User;
import myblog.user.domain.UserRepository;
import myblog.user.dto.SessionedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Optional;

public class BasicAuthInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(BasicAuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authorization = request.getHeader("Authorization");
        logger.debug("Authorization : {}", authorization);
        if (authorization == null || !authorization.startsWith("Basic")) {
            setSessionedUserToSession(request, SessionedUser.GUEST);
            return true;
        }

        String base64Credentials = authorization.substring("Basic".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
        final String[] values = credentials.split(":", 2);
        logger.debug("username : {}", values[0]);
        logger.debug("password : {}", values[1]);

        Optional<User> maybeUser = UserRepository.findByUserId(values[0]);
        SessionedUser sessionedUser = maybeUser.filter(u -> u.matchPassword(values[1]))
                .map(u -> new SessionedUser(u.getId(), u.getUserId()))
                .orElse(SessionedUser.GUEST);
        setSessionedUserToSession(request, sessionedUser);
        return true;
    }

    private void setSessionedUserToSession(HttpServletRequest request, SessionedUser sessionedUser) {
        HttpSession session = request.getSession();
        session.setAttribute(SessionedUser.SESSIONED_USER_KEY, sessionedUser);
    }
}