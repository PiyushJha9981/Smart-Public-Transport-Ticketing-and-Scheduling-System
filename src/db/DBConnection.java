package db;
import java.sql.*;

public class DBConnection {
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3307/smart_transport",
            "Piyush",
            "9350Ab@@"
        );
    }
}