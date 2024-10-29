/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package hotelbookingsystem1;

import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;


import java.io.File;

import org.junit.Test;

public class FileManagerTest {
    
    private FileManager fileManager;
    private Customer testCustomer;
    private Booking testBooking;
    
    @Before
    public void setUp() {
        fileManager = new FileManager();
        testCustomer = new Customer("Jerry Johnson", "Happygolucky@hotmail.com", "9372615821", "200 Mountain Pl");
        testBooking = new Booking(101, testCustomer, RoomType.STANDARD, "01/11/2024", "03/11/2024", true);
    }

    @Test
    public void testSaveBookingToFile() {
        // Test saving a booking to file
        fileManager.saveBookingToFile(testBooking);
        
        File bookingFile = new File("bookings.txt");
        assertTrue("Booking file should be created", bookingFile.exists());
    }

    @Test
    public void testSaveUserToFile() {
        // Test saving a user to file
        fileManager.saveUserToFile("testUser", true);
        
        File userFile = new File("users.txt");
        assertTrue("User file should be created", userFile.exists());
    }

    @Test
    public void testCreateDailyBackup() {
        // Test creating backup
        fileManager.createDailyBackup();
        
        File backupDir = new File("backup");
        assertTrue("Backup directory should be created", backupDir.exists());
        assertTrue("Backup directory should be a directory", backupDir.isDirectory());
    }

    @Test
    public void testReadBookingsFromFile() {
        // First save a booking
        fileManager.saveBookingToFile(testBooking);
        
        // Then read bookings
        List<String> bookings = fileManager.readBookingsFromFile();
        assertNotNull("Bookings list should not be null", bookings);
        assertFalse("Bookings list should not be empty", bookings.isEmpty());
    }
}