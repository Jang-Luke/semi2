package kr.co.khacademy.semi2.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.SQLException;

@UtilityClass
public class DataSourceUtils {

    private static final HikariConfig hikariConfig = new HikariConfig("/datasource.properties");
    private static final HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

    public static Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }
}
