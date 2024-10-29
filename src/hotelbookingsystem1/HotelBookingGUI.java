/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;



public class HotelBookingGUI extends JFrame {

    private static final Logger logger = Logger.getLogger(HotelBookingGUI.class.getName());
    private User currentUser;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private BookingManager bookingManager;
    private JTable bookingsTable;
    private DefaultTableModel tableModel;

    public HotelBookingGUI(User user) {
        super("Hotel Serenity Booking System");
        this.currentUser = user;
        this.bookingManager = new BookingManager();
        initializeGUI(); //Set up gui
        logger.info("GUI initialized for user: " + user.getUsername());
    }

    
    private void initializeGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(187, 245, 235)); 

        // Create and add panels
        mainPanel.add(createMainMenu(), "MainMenu");
        mainPanel.add(createBookingPanel(), "BookingPanel");
        mainPanel.add(createViewBookingsPanel(), "ViewBookings");

        // Add main panel to frame
        add(mainPanel);

        // Add menu bar
        setJMenuBar(createMenuBar());
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(187, 245, 235)); // Light mint green background

        // File Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setForeground(new Color(12, 0, 140)); 
        JMenuItem mainMenuItem = new JMenuItem("Main Menu");
        JMenuItem logoutMenuItem = new JMenuItem("Logout");

        mainMenuItem.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));
        logoutMenuItem.addActionListener(e -> handleLogout());

        fileMenu.add(mainMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(logoutMenuItem);

        // Bookings Menu
        JMenu bookingsMenu = new JMenu("Bookings");
        bookingsMenu.setForeground(new Color(12, 0, 140)); // Dark blue text
        JMenuItem newBookingItem = new JMenuItem("New Booking");
        JMenuItem viewBookingsItem = new JMenuItem("View Bookings");

        newBookingItem.addActionListener(e -> cardLayout.show(mainPanel, "BookingPanel"));
        viewBookingsItem.addActionListener(e -> {
            refreshBookingsTable();
            cardLayout.show(mainPanel, "ViewBookings");
        });

        bookingsMenu.add(newBookingItem);
        bookingsMenu.add(viewBookingsItem);

        menuBar.add(fileMenu);
        menuBar.add(bookingsMenu);

        // Admin Menu
        if (currentUser.isAdmin()) {
            JMenu adminMenu = new JMenu("Admin");
            adminMenu.setForeground(new Color(12, 0, 140)); // Dark blue text
            JMenuItem userManagement = new JMenuItem("Manage Users");
            userManagement.addActionListener(e -> showUserManagement());
            adminMenu.add(userManagement);
            menuBar.add(adminMenu);
        }

        return menuBar;
    }

    private JPanel createMainMenu() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(187, 245, 235)); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome to Hotel Serenity, " + currentUser.getUsername());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(12, 0, 140)); 
        panel.add(welcomeLabel, gbc);

        // Add some space
        panel.add(Box.createVerticalStrut(30), gbc);

        // Main buttons
        JButton bookButton = createStyledButton("Book a Room");
        JButton viewButton = createStyledButton("View Bookings");

        bookButton.addActionListener(e -> cardLayout.show(mainPanel, "BookingPanel"));
        viewButton.addActionListener(e -> {
            refreshBookingsTable();
            cardLayout.show(mainPanel, "ViewBookings");
        });

        panel.add(bookButton, gbc);
        panel.add(Box.createVerticalStrut(10), gbc);
        panel.add(viewButton, gbc);

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        
         button.setPreferredSize(new Dimension(200, 40));
         button.setForeground(new Color(12, 0, 140)); // Dark blue text

         return button;
    }



    private JPanel createBookingPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create form fields
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JComboBox<RoomType> roomTypeCombo = new JComboBox<>(RoomType.values());
        JFormattedTextField checkInField = createDateField();
        JFormattedTextField checkOutField = createDateField();
        JCheckBox breakfastBox = new JCheckBox("Include Breakfast (+$20)");

        // Add components to panel
        int gridy = 0;

        // Customer Information Section
        addSectionHeader(panel, "Customer Information", gbc, gridy++);

        addFormField(panel, "Name:", nameField, gbc, gridy++);
        addFormField(panel, "Email:", emailField, gbc, gridy++);
        addFormField(panel, "Phone:", phoneField, gbc, gridy++);
        addFormField(panel, "Address:", addressField, gbc, gridy++);

        // Booking Details Section
        addSectionHeader(panel, "Booking Details", gbc, gridy++);

        addFormField(panel, "Room Type:", roomTypeCombo, gbc, gridy++);
        addFormField(panel, "Check-in Date:", checkInField, gbc, gridy++);
        addFormField(panel, "Check-out Date:", checkOutField, gbc, gridy++);

        gbc.gridx = 0;
        gbc.gridy = gridy++;
        gbc.gridwidth = 2;
        panel.add(breakfastBox, gbc);

        // Add some space
        gbc.gridy = gridy++;
        panel.add(Box.createVerticalStrut(20), gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("Book Room");
        JButton clearButton = new JButton("Clear Form");
        JButton backButton = new JButton("Back to Main Menu");

        submitButton.addActionListener(e -> handleBookingSubmission(
                nameField, emailField, phoneField, addressField,
                roomTypeCombo, checkInField, checkOutField, breakfastBox
        ));

        clearButton.addActionListener(e -> clearBookingForm(
                nameField, emailField, phoneField, addressField,
                checkInField, checkOutField, breakfastBox
        ));

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);

        gbc.gridy = gridy;
        panel.add(buttonPanel, gbc);

        // Wrap in scroll pane
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Create wrapper panel
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(scrollPane, BorderLayout.CENTER);

        return wrapperPanel;
    }

    private void addSectionHeader(JPanel panel, String text, GridBagConstraints gbc, int gridy) {
        JLabel header = new JLabel(text);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 2;
        panel.add(header, gbc);
        gbc.gridwidth = 1;
    }

    private void addFormField(JPanel panel, String label, JComponent field, GridBagConstraints gbc, int gridy) {
        gbc.gridx = 0;
        gbc.gridy = gridy;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private JFormattedTextField createDateField() {
        JFormattedTextField field = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        field.setColumns(10);
        return field;
    }

    private JPanel createViewBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create table model
        String[] columns = {
            "Booking ID", "Customer Name", "Room Type",
            "Check-in", "Check-out", "Breakfast", "Total Cost"
        };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create table
        bookingsTable = new JTable(tableModel);
        bookingsTable.getTableHeader().setReorderingAllowed(false);

        // Style the table
        bookingsTable.setRowHeight(25);
        bookingsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        bookingsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton refreshButton = new JButton("Refresh");
        JButton backButton = new JButton("Back to Main Menu");

        refreshButton.addActionListener(e -> refreshBookingsTable());
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshBookingsTable() {
        tableModel.setRowCount(0);
        List<Booking> bookings = bookingManager.getAllBookings();
        for (Booking booking : bookings) {
            tableModel.addRow(new Object[]{
                booking.getId(),
                booking.getCustomer().getName(),
                booking.getRoomType(),
                booking.getinDate(),
                booking.getoutDate(),
                booking.isBfast() ? "Yes" : "No",
                String.format("$%.2f", CostCalculator.calculateCost(booking))
            });
        }
    }

    private void handleBookingSubmission(
            JTextField nameField, JTextField emailField, JTextField phoneField,
            JTextField addressField, JComboBox<RoomType> roomTypeCombo,
            JFormattedTextField checkInField, JFormattedTextField checkOutField,
            JCheckBox breakfastBox) {

        try {
            // Validate all inputs
            validateBookingInputs(nameField, emailField, phoneField, addressField,
                    checkInField, checkOutField);

            // Create customer
            Customer customer = new Customer(
                    nameField.getText().trim(),
                    emailField.getText().trim(),
                    phoneField.getText().trim(),
                    addressField.getText().trim()
            );

            // Create booking
            boolean success = bookingManager.createBooking(
                    customer,
                    (RoomType) roomTypeCombo.getSelectedItem(),
                    checkInField.getText(),
                    checkOutField.getText(),
                    breakfastBox.isSelected()
            );

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Booking successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                clearBookingForm(nameField, emailField, phoneField, addressField,
                        checkInField, checkOutField, breakfastBox);
                cardLayout.show(mainPanel, "MainMenu");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Room not available for selected dates",
                        "Booking Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            logger.severe("Booking submission error: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "An error occurred while processing your booking",
                    "System Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validateBookingInputs(
            JTextField nameField, JTextField emailField, JTextField phoneField,
            JTextField addressField, JFormattedTextField checkInField,
            JFormattedTextField checkOutField) {

        // Check for empty fields
        if (nameField.getText().trim().isEmpty()
                || emailField.getText().trim().isEmpty()
                || phoneField.getText().trim().isEmpty()
                || addressField.getText().trim().isEmpty()
                || checkInField.getText().trim().isEmpty()
                || checkOutField.getText().trim().isEmpty()) {
            throw new ValidationException("All fields must be filled out");
        }

        // Validate email
        ValidationUtils.validateEmail(emailField.getText().trim());

        // Validate phone
        ValidationUtils.validatePhone(phoneField.getText().trim());

        // Validate dates
        ValidationUtils.validateDates(checkInField.getText(), checkOutField.getText());
    }

    private void clearBookingForm(JTextField emailField, JTextField phoneField, JTextField addressField, JTextField addressField1, JFormattedTextField checkInField, JFormattedTextField checkOutField, JCheckBox breakfastBox) {
        Iterable<JTextField> fields = null;
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            logger.info("User logged out: " + currentUser.getUsername());
            new LoginGUI().setVisible(true);
            this.dispose();
        }
    }

    private void showUserManagement() {
        if (!currentUser.isAdmin()) {
            return;
        }
        // Implementation for user management dialog
        // This would show a dialog to manage users (add/remove/modify)
    }
}

