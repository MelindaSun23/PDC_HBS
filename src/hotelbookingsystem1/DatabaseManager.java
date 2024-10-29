/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;


import java.util.logging.Logger;
import java.sql.*;

public class DatabaseManager {
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String JDBC_URL = "jdbc:derby:hotelDB;create=true";
    private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());
    
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            logger.severe("Derby driver not found: " + e.getMessage());
            throw new RuntimeException("Derby driver not found", e);
        }
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection()) {
            createTables(conn);
            createDefaultAdmin(conn);
            logger.info("Database initialized successfully");
        } catch (SQLException e) {
            logger.severe("Failed to initialize database: " + e.getMessage());
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    private static void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Users table
            stmt.execute("CREATE TABLE Users (" +
                "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY " +
                "(START WITH 1, INCREMENT BY 1) PRIMARY KEY, " +
                "username VARCHAR(50) UNIQUE, " +
                "password VARCHAR(100), " +
                "is_admin BOOLEAN)");

            // Customers table
            stmt.execute("CREATE TABLE Customers (" +
                "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY " +
                "(START WITH 1, INCREMENT BY 1) PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "email VARCHAR(100), " +
                "phone VARCHAR(20), " +
                "address VARCHAR(200))");

            // Bookings table
            stmt.execute("CREATE TABLE Bookings (" +
                "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY " +
                "(START WITH 1, INCREMENT BY 1) PRIMARY KEY, " +
                "customer_id INTEGER, " +
                "room_type VARCHAR(20), " +
                "check_in_date DATE, " +
                "check_out_date DATE, " +
                "breakfast BOOLEAN, " +
                "total_cost DOUBLE, " +
                "FOREIGN KEY (customer_id) REFERENCES Customers(id))");
        } catch (SQLException e) {
            // If tables already exist, just log it and continue
            if (e.getSQLState().equals("X0Y32")) {
                logger.info("Tables already exist");
            } else {
                throw e;
            }
        }
    }

    private static void createDefaultAdmin(Connection conn) throws SQLException {
        String checkAdmin = "SELECT COUNT(*) FROM Users WHERE username = 'admin'";
        try (PreparedStatement ps = conn.prepareStatement(checkAdmin)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                String insertAdmin = "INSERT INTO Users (username, password, is_admin) VALUES (?, ?, ?)";
                try (PreparedStatement insertPs = conn.prepareStatement(insertAdmin)) {
                    insertPs.setString(1, "admin");
                    insertPs.setString(2, PasswordHasher.hashPassword("admin123"));
                    insertPs.setBoolean(3, true);
                    insertPs.executeUpdate();
                    logger.info("Default admin account created");
                }
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }
}