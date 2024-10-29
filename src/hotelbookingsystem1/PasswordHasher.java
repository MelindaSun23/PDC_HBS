/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;

import java.util.logging.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PasswordHasher {

    private static final Logger logger = Logger.getLogger(PasswordHasher.class.getName());

    //Hashes a password using SHA-256 and encodes it in Base64
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");  //Get SHA-256 message digest instance
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Error hashing password: " + e.getMessage());  // Log if SHA-256 is not available and throw RuntimeException
            throw new RuntimeException("Error hashing password", e);
        }
    }
//Verifies a password by hashing and comparing to a stored hashed password

    public static boolean verifyPassword(String password, String hashedPassword) {
        String newHash = hashPassword(password);
        return newHash.equals(hashedPassword); //Returns true if hashes match
    }
}
