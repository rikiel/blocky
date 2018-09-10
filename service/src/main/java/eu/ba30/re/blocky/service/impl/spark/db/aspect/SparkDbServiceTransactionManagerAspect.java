package eu.ba30.re.blocky.service.impl.spark.db.aspect;

import java.io.Serializable;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ba30.re.blocky.common.aspects.AspectPointcuts;
import eu.ba30.re.blocky.service.impl.spark.db.SparkDbTransactionManager;

/**
 * Commit transactions after each service call.
 */
@Aspect
@Component
public class SparkDbServiceTransactionManagerAspect extends AspectPointcuts implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(SparkDbServiceTransactionManagerAspect.class);

    @Autowired
    private SparkDbTransactionManager transactionManager;

    @AfterThrowing(value = "serviceCall()", throwing = "e")
    public void rollbackTransaction(JoinPoint joinPoint, Throwable e) {
        log.error("rollbackTransaction() for {}", joinPoint.getSignature(), e);

        transactionManager.rollback();
    }

    @After(value = "serviceCall()")
    public void commitTransaction(JoinPoint joinPoint) {
        log.info("commitTransaction() for {}", joinPoint.getSignature());

        transactionManager.commit();
    }
}
