package eu.ba30.re.blocky.service.config.spark.db;

import java.sql.Types;

import org.apache.spark.sql.jdbc.JdbcDialect;
import org.apache.spark.sql.jdbc.JdbcType;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;

import scala.Option;

public class HsqlDbDialect extends JdbcDialect {
    @Override
    public boolean canHandle(String url) {
        return url.startsWith("jdbc:hsqldb");
    }

    @Override
    public Option<JdbcType> getJDBCType(DataType dt) {
        if (dt == DataTypes.StringType) {
            return Option.apply(new JdbcType("VARCHAR(255)", Types.VARCHAR));
        }
        return super.getJDBCType(dt);
    }
}
