import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        try {
            Connection connection = DatabaseConfig.getConnection();
            System.out.println("Connected to Database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
