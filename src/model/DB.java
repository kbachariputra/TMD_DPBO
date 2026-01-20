/*
 * ===================================================
 * Filename     : DB.java
 * Programmer   : Kasyful Haq Bachariputra
 * Date         : 6 June 2025
 * Email        : kasyfulhaqb@upi.edu
 * Deskripsi    : Package model untuk mengakses database
 * 
 * ===================================================
 */

package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tmd_dpbo";
    private static final String USER = "root";
    private static final String PASS = "";

    private Connection conn;

    /**
     * The constructor tries to connect to the database.
     * If it fails, it will throw an exception to let the calling class know.
     */
    public DB() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection successful.");
        } catch (ClassNotFoundException e) {
            // This helps debug if the MySQL Connector/J .jar file is missing.
            System.err.println("Database driver not found.");
            throw new SQLException("Database driver not found.", e);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            throw e;
        }
    }

    /**
     * A simple getter to provide the active connection to other classes.
     * @return The active database connection.
     */
    public Connection getConnection() {
        return this.conn;
    }

    /**
     * Closes the connection when it's no longer needed.
     */
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
