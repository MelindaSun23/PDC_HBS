/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;


import java.util.logging.Logger;
import java.sql.*;

public class UserManager {

    private static final Logger logger = Logger.getLogger(UserManager.class.getName());
    private static FileManager fileManager = new FileManager();

    public static User authenticateUser(String username, String password) {
        try ( Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM Users WHERE username = ?";
            try ( PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    if (PasswordHasher.verifyPassword(password, storedHash)) {
                        return new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getBoolean("is_admin")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            logger.severe("Authentication failed: " + e.getMessage());
            throw new RuntimeException("Authentication failed", e);
        }
        return null;
    }

    public static boolean registerUser(String username, String password, boolean isAdmin) {
        try ( Connection conn = DatabaseManager.getConnection()) {
            String query = "INSERT INTO Users (username, password, is_admin) VALUES (?, ?, ?)";
            try ( PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, username);
                ps.setString(2, PasswordHasher.hashPassword(password));
                ps.setBoolean(3, isAdmin);

                int result = ps.executeUpdate();
                if (result > 0) {
                    fileManager.saveUserToFile(username, isAdmin);
                    logger.info("User registered successfully: " + username);
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.severe("User registration failed: " + e.getMessage());
        }
        return false;
    }
}
