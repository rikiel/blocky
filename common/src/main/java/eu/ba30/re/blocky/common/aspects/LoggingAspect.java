package eu.ba30.re.blocky.common.aspects;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging all calls of repository, service, view and presenter
 */
@Aspect
@Order(1)
@Component
public class LoggingAspect extends AspectPointcuts implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    private static int CALL_ID = 100;

    @Around("repositoryCall() || serviceCall() || viewCall() || presenterCall()")
    public Object logCalls(ProceedingJoinPoint joinPoint) throws Throwable {
        final int callId = CALL_ID++;
        try {
            log.info("[{}] Call begin: {} with arguments [{}]",
                    callId,
                    joinPoint.getSignature(),
                    joinArgs(joinPoint.getArgs()));
            final Object result = joinPoint.proceed();
            log.info("[{}] Call end: {} with arguments [{}] resulted {}",
                    callId,
                    joinPoint.getSignature(),
                    joinArgs(joinPoint.getArgs()),
                    result);
            return result;
        } catch (Throwable thr) {
            log.error("[{}] Call end with exception: {} with arguments [{}]",
                    callId,
                    joinPoint.getSignature(),
                    joinArgs(joinPoint.getArgs()),
                    thr);
            throw thr;
        }
    }

    @Nonnull
    private static String joinArgs(@Nonnull final Object[] args) {
        return Arrays.stream(args).map(Objects::toString).collect(Collectors.joining(", "));
    }
}
