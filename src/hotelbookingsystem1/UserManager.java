/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;


import java.util.logging.Logger;
import java.sql.*;

public class UserManager {
    private static final Logger logger = Logger.getLogger(UserManager.class.getName());
    private static FileManager fileManager = new FileManager(); //Handles user file operations

    // Authenticates user by checking username and password
    public static User authenticateUser(String username, String password) { 
        try (Connection conn = DatabaseManager.getConnection()) { // Establish DB connection
            String query = "SELECT * FROM Users WHERE username = ?"; //Query to find user
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, username); //Set username parameter
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()) { //If user exists
                    String storedHash = rs.getString("password"); //Retrieve stored hashed password
                    if (PasswordHasher.verifyPassword(password, storedHash)) {
                        return new User(
                            rs.getInt("id"), //User id
                            rs.getString("username"), //Username
                            rs.getBoolean("is_admin") //Admin status
                        );
                    }
                }
            }
        } catch (SQLException e) {
            logger.severe("Authentication failed: " + e.getMessage()); //Log authentication fail
            throw new RuntimeException("Authentication failed", e); //Throw runtime exception
        }
        return null; //Null if authentication fails
    }

    //Reguster new user and save info
    public static boolean registerUser(String username, String password, boolean isAdmin) {
        try (Connection conn = DatabaseManager.getConnection()) { //Establish database connection
            String query = "INSERT INTO Users (username, password, is_admin) VALUES (?, ?, ?)"; //Query for insertion
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, username); //Set username
                ps.setString(2, PasswordHasher.hashPassword(password)); //Hash and set passsword
                ps.setBoolean(3, isAdmin); //Set admin status
                
                int result = ps.executeUpdate(); //Execute insertion
                if (result > 0) { //Check if successful
                    fileManager.saveUserToFile(username, isAdmin); //save user info into file
                    logger.info("User registered successfully: " + username); //Log success
                    return true; //Return true if success
                }
            }
        } catch (SQLException e) {
            logger.severe("User registration failed: " + e.getMessage()); // Log registration failure
        }
        return false; // Return false if fails
    }
}
