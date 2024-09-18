package se.lexicon.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLconnection {

    private static final String url = "jdbc:mysql://localhost:3306/todoit";
    private static final String username = "root";
    private static final String password = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        }catch (SQLException e) {
            System.out.println("Could not connect to the DB");
        }
        return connection;
    }
}