package hotelbookingsystem1;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HotelBookingSystemTest {
    private static BookingManager bookingManager;
    private static Customer testCustomer;
    private static SimpleDateFormat dateFormat;

    @BeforeClass
    public static void setUpClass() {
        DatabaseManager.initializeDatabase();
        bookingManager = new BookingManager();
        testCustomer = new Customer("Test User", "test@test.com", "12345678", "Test Address");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Test
    public void testDatabaseConnection() {
        try (Connection conn = DatabaseManager.getConnection()) {
            assertNotNull(conn);
            assertFalse(conn.isClosed());
        } catch (SQLException e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }

    @Test
    public void testUserAuthentication() {
        User user = UserManager.authenticateUser("admin", "admin123");
        assertNotNull(user);
        assertTrue(user.isAdmin());
    }

    @Test
    public void testInvalidAuthentication() {
        User user = UserManager.authenticateUser("invalid", "invalid");
        assertNull(user);
    }

    @Test
    public void testUserRegistration() {
        String username = "testuser" + System.currentTimeMillis();
        boolean result = UserManager.registerUser(username, "password123", false);
        assertTrue(result);
    }

    

    @Test
    public void testCostCalculation() {
        Booking booking = new Booking(
            testCustomer,
            RoomType.STANDARD,
            "01/12/2024",
            "03/12/2024",
            true
        );
        double expectedCost = RoomType.STANDARD.getCost() + 20; // Room cost + breakfast
        assertEquals(expectedCost, CostCalculator.calculateCost(booking), 0.01);
    }

    @Test
    public void testGetAllBookings() {
        List<Booking> bookings = bookingManager.getAllBookings();
        assertNotNull(bookings);
        assertFalse(bookings.isEmpty());
    }

    @Test(expected = ValidationException.class)
    public void testInvalidEmailValidation() {
        ValidationUtils.validateEmail("invalid-email");
    }

    @Test
    public void testValidEmailValidation() {
        ValidationUtils.validateEmail("valid@email.com");
        // If no exception is thrown, test passes
    }

    @Test(expected = ValidationException.class)
    public void testInvalidPhoneValidation() {
        ValidationUtils.validatePhone("123");
    }

    @Test
    public void testValidPhoneValidation() {
        ValidationUtils.validatePhone("12345678");
        // If no exception is thrown, test passes
    }

    @Test(expected = ValidationException.class)
    public void testInvalidDateValidation() {
        ValidationUtils.validateDates("01/01/2023", "01/01/2024");
    }

    @Test
    public void testValidDateValidation() {
        String futureDate = dateFormat.format(new Date(System.currentTimeMillis() + 86400000L));
        ValidationUtils.validateDates(futureDate, futureDate);
        // If no exception is thrown, test passes
    }
}