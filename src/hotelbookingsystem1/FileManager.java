/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FileManager {

    private static final Logger logger = Logger.getLogger(FileManager.class.getName()); //Logger for tracking file operations
    private static final String BOOKINGS_FILE = "bookings.txt"; //File for saving bookings
    private static final String USERS_FILE = "users.txt"; //File for saving user registration
    private static final String BACKUP_DIR = "backup/"; //Directory for backup
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //Date format for logging

    public FileManager() { //Constructor to create backup directory if it doe not exist
        createBackupDirectory();
    }
//Method to create backup directory

    private void createBackupDirectory() {
        File backupDir = new File(BACKUP_DIR);
        if (!backupDir.exists()) {
            backupDir.mkdir();
            logger.info("Backup directory created");
        }
    }
//Method to save a bookings details to a file

    public void saveBookingToFile(Booking booking) {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKINGS_FILE, true))) {
            writer.write("=== Booking Details ===\n");
            writer.write("Booking ID: " + booking.getId() + "\n");
            writer.write("Customer Name: " + booking.getCustomer().getName() + "\n");
            writer.write("Email: " + booking.getCustomer().getEmail() + "\n");
            writer.write("Phone: " + booking.getCustomer().getPhone() + "\n");
            writer.write("Address: " + booking.getCustomer().getAddress() + "\n");
            writer.write("Room Type: " + booking.getRoomType() + "\n");
            writer.write("Check-In Date: " + booking.getinDate() + "\n");
            writer.write("Check-Out Date: " + booking.getoutDate() + "\n");
            writer.write("Breakfast Included: " + booking.isBfast() + "\n");
            writer.write("Total Cost: $" + CostCalculator.calculateCost(booking) + "\n");
            writer.write("=====================================\n\n");
            writer.flush(); //Ensure all data is written to file
            logger.info("Booking saved to file: " + booking.getId()); //Log successful
        } catch (IOException e) {
            logger.severe("Error saving booking to file: " + e.getMessage()); //zLog any errors
        }
    }
//Method to save user registration details to a file

    public void saveUserToFile(String username, boolean isAdmin) {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write("=== User Registration ===\n");
            writer.write("Username: " + username + "\n");
            writer.write("Admin: " + isAdmin + "\n");
            writer.write("Registration Date: " + dateFormat.format(new java.util.Date()) + "\n");
            writer.write("=====================================\n\n");
            writer.flush();
            logger.info("User registration saved to file: " + username);
        } catch (IOException e) {
            logger.severe("Error saving user to file: " + e.getMessage());
        }
    }
//Method to create daily backup of bookings file

    public void createDailyBackup() {
        String backupFileName = BACKUP_DIR + "backup_"
                + dateFormat.format(new java.util.Date()) + ".txt";

        try ( BufferedReader reader = new BufferedReader(new FileReader(BOOKINGS_FILE));  BufferedWriter writer = new BufferedWriter(new FileWriter(backupFileName))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + "\n"); //Write each line to backup file
            }
            writer.flush(); //Ensure all data written
            logger.info("Daily backup created: " + backupFileName); //Log successful
        } catch (IOException e) {
            logger.severe("Error creating backup: " + e.getMessage()); //Log any errors
        }
    }
    //Method to read bookings from file into a list

    public List<String> readBookingsFromFile() {
        List<String> bookings = new ArrayList<>(); //List to hold booking data
        try ( BufferedReader reader = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bookings.add(line); //Add each line to list
            }
        } catch (IOException e) {
            logger.severe("Error reading bookings from file: " + e.getMessage()); //Log any errors
        }
        return bookings; //Return List of bookings
    }
}
