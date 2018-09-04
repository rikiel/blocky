package eu.ba30.re.blocky.common.aspects;

import java.io.Serializable;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import eu.ba30.re.blocky.common.exception.DatabaseException;

/**
 * Aspect that changes exception type that was thrown from service.
 * For all exceptions, {@link DatabaseException} is used.
 */
@Aspect
@Order(2)
@Component
public class ServiceExceptionsAspect extends AspectPointcuts implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(ServiceExceptionsAspect.class);

    @Around("serviceCall()")
    public Object watchCalls(ProceedingJoinPoint joinPoint) throws DatabaseException {
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
}
