/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;

public class LoginGUI extends JFrame {
    private static final Logger logger = Logger.getLogger(LoginGUI.class.getName());
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginGUI() {
        super("Hotel Serenity - Login"); //Window title setup
        initializeGUI(); //Initialize GUI components
    }

    
    private void initializeGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close application when window is closed
        setSize(400, 300); //Set window size
        setLocationRelativeTo(null); // Center window on screen

        //Main panel to hold all components with grid layout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); //Padding
        gbc.fill = GridBagConstraints.HORIZONTAL; //Allow components to fill horizontally

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        mainPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> showRegisterDialog());

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        // Add main panel to frame
        add(mainPanel);

        // Add key listener for Enter key
        getRootPane().setDefaultButton(loginButton);
    }

    private void handleLogin() {
        String username = usernameField.getText(); //Get username input
        String password = new String(passwordField.getPassword()); //Get password input

        try {
            //Attempt authentication using username and password
            User user = UserManager.authenticateUser(username, password);
            if (user != null) {
                logger.info("User logged in successfully: " + username); //Log successful login
                SwingUtilities.invokeLater(() -> { // Open main GUI after login
                    HotelBookingGUI mainGUI = new HotelBookingGUI(user);
                    mainGUI.setVisible(true); //Show main GUI
                    this.dispose(); //Close login window
                });
            } else {
                //Warn fail login
                logger.warning("Failed login attempt for username: " + username);
                JOptionPane.showMessageDialog(this,
                    "Invalid username or password",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE); //Error message
            }
        } catch (Exception e) {
            //Handle unexpacted errors during login
            logger.severe("Login error: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                "An error occurred during login",
                "System Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showRegisterDialog() {
        //Create a registration dialog, gather new user info
        JDialog dialog = new JDialog(this, "Register New User", true); 
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); //Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Username field for registration
        JTextField newUsername = new JTextField(20);
        JPasswordField newPassword = new JPasswordField(20);
        JPasswordField confirmPassword = new JPasswordField(20);
        JButton registerBtn = new JButton("Register");

        // Username field
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        dialog.add(newUsername, gbc);

        // Password field
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        dialog.add(newPassword, gbc);

        // Confirm password field
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        dialog.add(confirmPassword, gbc);

        // Register button
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        dialog.add(registerBtn, gbc);

        //Action for registration button
        registerBtn.addActionListener(e -> {
            String username = newUsername.getText();
            String password = new String(newPassword.getPassword());
            String confirmPwd = new String(confirmPassword.getPassword());

                //Basic input checks
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                    "Please fill in all fields",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

             //Password confirm check
            if (!password.equals(confirmPwd)) {
                JOptionPane.showMessageDialog(dialog,
                    "Passwords do not match",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Attempt user registration
            if (UserManager.registerUser(username, password, false)) {
                logger.info("New user registered: " + username); //Log registration success
                JOptionPane.showMessageDialog(dialog,
                    "Registration successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE); // Notify success
                dialog.dispose(); //Close dialog on success
            } else {
                 //Handle failed registration
                logger.warning("Registration failed for username: " + username);
                JOptionPane.showMessageDialog(dialog,
                    "Registration failed. Username might be taken.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setSize(300, 200); //Set dialog size
        dialog.setLocationRelativeTo(this); // Center dialog on login window
        dialog.setVisible(true); //Display dialog
    }
}
