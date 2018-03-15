package eu.ba30.re.blocky.aspects;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    private static int CALL_ID = 100;

    @Around("serviceCall() || repositoryCall() || viewCall() || presenterCall()")
    public Object logCalls(ProceedingJoinPoint joinPoint) throws Throwable {
        final int callId = CALL_ID++;
        try {
            log.info("[{}] Calling {} with arguments [{}]",
                    callId,
                    joinPoint.getSignature(),
                    joinArgs(joinPoint.getArgs()));
            final Object result = joinPoint.proceed();
            log.info("[{}] Call {} with arguments [{}] resulted {}",
                    callId,
                    joinPoint.getSignature(),
                    joinArgs(joinPoint.getArgs()),
                    result);
            return result;
        } catch (Throwable thr) {
            log.error("[{}] Call {} with arguments [{}] throws exception",
                    callId,
                    joinPoint.getSignature(),
                    joinArgs(joinPoint.getArgs()),
                    thr);
            throw thr;
        }
    }

    @Pointcut("execution(* eu.ba30.re.blocky.service..*Service+.*(..))")
    public void serviceCall() {
    }

    @Pointcut("execution(* eu.ba30.re.blocky.view..*View+.*(..))")
    public void viewCall() {
    }

    @Pointcut("execution(* eu.ba30.re.blocky.view..*Presenter+.*(..))")
    public void presenterCall() {
    }

    @Pointcut("execution(* eu.ba30.re.blocky.service..*Repository+.*(..))")
    public void repositoryCall() {
    }

    @Nonnull
    private static String joinArgs(@Nonnull final Object[] args) {
        return Arrays.stream(args).map(Object::toString).collect(Collectors.joining(""));
    }
}
