/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package hotelbookingsystem1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class BookingManagerTest {
    
    private BookingManager bookingManager;
    
    @Before
    public void setUp() {
        bookingManager = new BookingManager();
        new Customer("Ruby Jane", "RubyJanerules@gmail.com", "0123456789", "52 green St");
    }

    @Test
    public void testCreateBooking_NullCustomer() {
        // Test creating a booking with null customer
        boolean result = bookingManager.createBooking(
            null, 
            RoomType.STANDARD, 
            "01/11/2024", 
            "03/11/2024", 
            true
        );
        
        assertFalse("Booking with null customer should fail", result);
    }


    @Test
    public void testGetAllBookings() {
        // Test retrieving all bookings
        List<Booking> bookings = bookingManager.getAllBookings();
        assertNotNull("Booking list should not be null", bookings);
    }
}