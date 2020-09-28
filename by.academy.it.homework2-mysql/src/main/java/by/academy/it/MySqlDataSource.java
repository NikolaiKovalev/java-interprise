package by.academy.it;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlDataSource {

    static Properties properties = new Properties();
    private static final String url = "jdbc:mysql://localhost:3306/homework2";
    private static final String testUrl = "jdbc:mysql://localhost:3306/homework2_test";

    static {
        properties.put("user", "root");
        properties.put("password", "root");
        properties.put("useSSL", "false");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, properties);
    }

    public static Connection getTestConnection() throws SQLException {
        return DriverManager.getConnection(testUrl, properties);
    }
}
