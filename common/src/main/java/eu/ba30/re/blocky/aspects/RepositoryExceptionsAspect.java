package eu.ba30.re.blocky.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.ba30.re.blocky.exception.DatabaseException;

@Aspect
@Component
public class RepositoryExceptionsAspect {
    private static final Logger log = LoggerFactory.getLogger(RepositoryExceptionsAspect.class);

    @Around("repositoryCall()")
    public Object logCalls(ProceedingJoinPoint joinPoint) throws DatabaseException {
        try {
            return joinPoint.proceed();
        }catch (DatabaseException e) {
            log.trace("Database exception is thrown yet");
            throw e;
        } catch (Throwable thr) {
            log.trace("Throwing database exception");
            throw new DatabaseException(thr);
        }
    }

    @Pointcut("execution(* eu.ba30.re.blocky.service..*Repository+.*(..))")
    public void repositoryCall() {
    }
}
