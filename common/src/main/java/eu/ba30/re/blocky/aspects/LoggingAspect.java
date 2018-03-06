package eu.ba30.re.blocky.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("serviceCall() || viewCall() || presenterCall()")
    public Object logCalls(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            final Object result = joinPoint.proceed();
            log.info("Call {} with arguments [{}] resulted {}",
                    joinPoint.getSignature(),
                    Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining("")),
                    result);
            return result;
        } catch (Throwable thr) {
            log.error("Call {} with arguments [{}] throws exception",
                    joinPoint.getSignature(),
                    Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining("")),
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
}
