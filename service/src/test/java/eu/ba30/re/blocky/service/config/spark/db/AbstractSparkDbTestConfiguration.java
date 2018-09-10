package eu.ba30.re.blocky.service.config.spark.db;

import java.util.Properties;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.jdbc.JdbcDialects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import eu.ba30.re.blocky.service.config.AbstractTestConfiguration;
import eu.ba30.re.blocky.service.impl.spark.db.SparkDbTransactionManager;

@ComponentScan({ "eu.ba30.re.blocky.service.impl.spark.db.repository" })
abstract class AbstractSparkDbTestConfiguration extends AbstractTestConfiguration {
    private static final String EMBEDDED_DATABASE_URL = "jdbc:hsqldb:mem:testdb";

    @Bean
    @Autowired
    public SparkSession sparkSession() {
        JdbcDialects.registerDialect(new HsqlDbDialect());

        return SparkSession.builder()
                .appName("Unit tests")
                .master("local")
                .getOrCreate();
    }

    @Bean
    public String jdbcConnectionUrl() {
        return EMBEDDED_DATABASE_URL;
    }

    @Bean
    public Properties jdbcConnectionProperties() {
        return new Properties();
    }

    @Bean
    public SparkDbTransactionManager sparkTransactionManager() {
        return new SparkDbTransactionManager();
    }
}
