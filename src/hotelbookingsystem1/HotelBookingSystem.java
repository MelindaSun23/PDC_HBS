/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hotelbookingsystem1;

import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


// Main class
public class HotelBookingSystem {
    private static final Logger logger = Logger.getLogger(HotelBookingSystem.class.getName());

    public static void main(String[] args) {
        try {
            // Set look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            logger.info("Starting Hotel Booking System");
            
            // Initialize database
            DatabaseManager.initializeDatabase();
            
            // Launch application
            SwingUtilities.invokeLater(() -> {
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
            });
        } catch (Exception e) {
            logger.severe("Failed to start application: " + e.getMessage()); //Log and show error if startup fails
            JOptionPane.showMessageDialog(null, 
                "Failed to start application: " + e.getMessage(),
                "Startup Error",
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}

