package support.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Aspect
public class NsAspect {
    private Map<Class, Logger> loggers = new ConcurrentHashMap<>();

    @Pointcut("within(myblog..service..*) || within(myblog..web..*)")
    public void serviceAndWebPointcut() {}

    private <T> Logger getLogger(Class<T> clazz) {
        if (loggers.get(clazz) == null) {
            loggers.put(clazz, LoggerFactory.getLogger(clazz));
        }
        return loggers.get(clazz);
    }

    @Around("serviceAndWebPointcut()")
    public Object doLogging(ProceedingJoinPoint pjp)
            throws Throwable {
        final Logger log = getLogger(pjp.getTarget().getClass());
        final String methodName = pjp.getSignature().getName();
        log.debug("{}(): {}", methodName, pjp.getArgs());

        Object result = pjp.proceed();
        log.debug("{}(): result={}", methodName, result);

        return result;
    }

    @Around("serviceAndWebPointcut()")
    public Object checkPerformance(ProceedingJoinPoint pjp)
            throws Throwable {
        final Logger log = getLogger(pjp.getTarget().getClass());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = pjp.proceed();

        final String methodName = pjp.getSignature().getName();
        stopWatch.stop();

        if (stopWatch.getTotalTimeMillis() > 200) {
            log.info("{}() - execution time: {}", methodName, stopWatch.prettyPrint());
        }

        return result;
    }
}
