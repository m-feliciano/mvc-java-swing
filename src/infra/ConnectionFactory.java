package infra;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

// Factory using c3p0
public class ConnectionFactory {

    private DataSource dataSource;

    public ConnectionFactory() {
        try {
            ComboPooledDataSource comboSource = new ComboPooledDataSource();
            Properties props = loadProperties();
            comboSource.setJdbcUrl(props.getProperty("dburl"));
            comboSource.setUser(props.getProperty("user"));
            comboSource.setPassword(props.getProperty("password"));
            comboSource.setMaxPoolSize(5); // pool size

            this.dataSource = comboSource;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static Properties loadProperties() throws IOException {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public Connection getConnection() {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
