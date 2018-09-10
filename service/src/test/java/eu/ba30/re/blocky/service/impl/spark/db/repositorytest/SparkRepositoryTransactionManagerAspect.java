package eu.ba30.re.blocky.service.impl.spark.db.repositorytest;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ba30.re.blocky.common.aspects.AspectPointcuts;
import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.service.impl.spark.db.SparkDbTransactionManager;

/**
 * Commit transactions after each repository call,
 * instead of handling transactions only after service calls like in {@link eu.ba30.re.blocky.service.impl.spark.db.aspect.SparkDbServiceTransactionManagerAspect}
 */
@Aspect
public class SparkRepositoryTransactionManagerAspect extends AspectPointcuts implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(SparkRepositoryTransactionManagerAspect.class);

    private final SparkDbTransactionManager transactionManager;

    public SparkRepositoryTransactionManagerAspect(@Nonnull final SparkDbTransactionManager transactionManager) {
        Validate.notNull(transactionManager);
        this.transactionManager = transactionManager;
    }

    @AfterThrowing(value = "repositoryCall()", throwing = "e")
    public void rollbackTransaction(JoinPoint joinPoint, Throwable e) {
        log.error("rollbackTransaction() for {}", joinPoint.getSignature(), e);

        transactionManager.rollback();
    }

    @After(value = "repositoryCall()")
    public void commitTransaction(JoinPoint joinPoint) {
        log.info("commitTransaction() for {}", joinPoint.getSignature());

        transactionManager.commit();
    }
}
