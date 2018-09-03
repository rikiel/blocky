package eu.ba30.re.blocky.service.impl.spark.repository;

import javax.annotation.Nonnull;

import org.apache.spark.sql.Dataset;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.service.impl.spark.SparkTransactionManager;

// TODO BLOCKY-16 reimplementovat
abstract class SparkTransaction<DbModel> implements SparkTransactionManager.Transaction {
    private final Dataset<DbModel> snapshotData;

    SparkTransaction(@Nonnull final Dataset<DbModel> snapshotData) {
        Validate.notNull(snapshotData);

        this.snapshotData = snapshotData;
    }

    @Nonnull
    protected abstract Dataset<DbModel> getNewDataForCommit();

    protected abstract void setData(@Nonnull final Dataset<DbModel> newData);

    @Override
    public final void onCommit() {
        final Dataset<DbModel> newDataForCommit = getNewDataForCommit();
        Validate.notNull(newDataForCommit);
        setData(newDataForCommit);
    }

    @Override
    public final void onRollback() {
        setData(snapshotData);
    }
}
