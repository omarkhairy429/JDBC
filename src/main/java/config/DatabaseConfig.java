package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class DatabaseConfig {
    public final static String url = "jdbc:postgresql://localhost:5432/internship_management_system";

    public final static String username = "postgres";
    public final static String password = "omarqwer1";

    public  static Connection getConnection() throws SQLException{
        try {
            return DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e) {
            throw e;
        }
    }
}
