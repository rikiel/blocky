package eu.ba30.re.blocky.service.impl.spark.common;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;

@Service
@Scope("singleton")
public class SparkTransactionManager implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(SparkTransactionManager.class);

    private final List<Transaction> transactions = Lists.newArrayList();

    public void newTransaction(@Nonnull final Transaction transaction) {
        Validate.notNull(transaction);
        log.info("Creating new transaction {}", transaction);
        transactions.add(transaction);
    }

    public void commit() {
        synchronized (this) {
            try {
                log.info("Commiting transaction for {}", transactions);
                transactions.forEach(Transaction::onCommit);
            } catch (Throwable thr) {
                log.error("Could not commit all transactions! Rolling back!", thr);
                rollback();
                throw thr;
            } finally {
                transactions.clear();
            }
        }
    }

    public void rollback() {
        synchronized (this) {
            try {
                log.info("Rolling back transaction for {}", transactions);
                transactions.forEach(Transaction::onRollback);
            } catch (Throwable thr) {
                log.error("Could not rollback all transactions!", thr);
                throw new TransactionManagerError("Failed to rollback transactions", thr);
            } finally {
                transactions.clear();
            }
        }
    }

    public interface Transaction extends Serializable {
        void onCommit();

        void onRollback();
    }

    private static class TransactionManagerError extends RuntimeException {
        TransactionManagerError(String s, Throwable throwable) {
            super(s, throwable);
        }
    }
}
