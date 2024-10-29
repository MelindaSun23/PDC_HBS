/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class BookingManager implements Bookable {
    private static final Logger logger = Logger.getLogger(BookingManager.class.getName());
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final FileManager fileManager; //Managing file interactions

    public BookingManager() {
        this.fileManager = new FileManager(); //Initializes file manager
    }

    @Override
    public boolean createBooking(Customer customer, RoomType roomType, String checkIn, String outDate, boolean breakfast) {
        Connection conn = null;
        try {
            //Validate dates
            ValidationUtils.validateDates(checkIn, outDate); //Check if dates are valid
            
            if (!isRoomAvailable(roomType, checkIn, outDate)) {
                logger.warning("Room not available for selected dates"); //Ensure room availability
                return false;
            }

            conn = DatabaseManager.getConnection(); //Open database connection
            conn.setAutoCommit(false); //Disable auto commit

            // Save customer and get ID
            int customerId = saveCustomer(conn, customer);
            
            // Create booking
            String sql = "INSERT INTO Bookings (customer_id, room_type, check_in_date, check_out_date, breakfast, total_cost) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, customerId); //Customer id
                ps.setString(2, roomType.toString()); //Room type string
                ps.setDate(3, new java.sql.Date(dateFormat.parse(checkIn).getTime())); //Check in date
                ps.setDate(4, new java.sql.Date(dateFormat.parse(outDate).getTime())); // Check out date
                ps.setBoolean(5, breakfast); //Breakfast option
                
                // Calculate and set total cost
                Booking tempBooking = new Booking(customer, roomType, checkIn, outDate, breakfast);
                double totalCost = CostCalculator.calculateCost(tempBooking);
                ps.setDouble(6, totalCost);
                
                ps.executeUpdate(); //Run inster query
                
                // Get booking ID and save to file
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int bookingId = rs.getInt(1); //Get generated booking id
                    Booking newBooking = new Booking(bookingId, customer, roomType, checkIn, outDate, breakfast);
                    fileManager.saveBookingToFile(newBooking); //Save booking details
                }
            }
            
            conn.commit(); //Commit transaction
            logger.info("Booking created successfully"); //Log success
            return true;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback(); //Rollback if error
                } catch (SQLException ex) {
                    logger.severe("Error rolling back transaction: " + ex.getMessage());
                }
            }
            logger.severe("Error creating booking: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close(); //Close connecting in any case
                } catch (SQLException e) {
                    logger.severe("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    private int saveCustomer(Connection conn, Customer customer) throws SQLException {
        String sql = "INSERT INTO Customers (name, email, phone, address) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, customer.getName()); //Customer name
            ps.setString(2, customer.getEmail()); //Customer email
            ps.setString(3, customer.getPhone()); //Customer phone number
            ps.setString(4, customer.getAddress()); //Customer address
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Return genreated customer id
            }
            throw new SQLException("Failed to get customer ID"); //Error if failed
        }
    }

    public boolean isRoomAvailable(RoomType roomType, String checkIn, String checkOut) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "SELECT COUNT(*) FROM Bookings WHERE room_type = ? " +
                        "AND ((check_in_date <= ? AND check_out_date >= ?) " +
                        "OR (check_in_date <= ? AND check_out_date >= ?))";
            
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                Date inDate = dateFormat.parse(checkIn); //Parse check in date
                Date outDate = dateFormat.parse(checkOut); //Parse check out date
                
                ps.setString(1, roomType.toString()); //Room type filter
                ps.setDate(2, new java.sql.Date(outDate.getTime())); //Outdate range
                ps.setDate(3, new java.sql.Date(inDate.getTime())); //In date range
                ps.setDate(4, new java.sql.Date(inDate.getTime())); //In date overlap check
                ps.setDate(5, new java.sql.Date(outDate.getTime())); //Outdate overlap check
                
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) == 0; //Room is available if count is 0
                }
            }
        } catch (SQLException | ParseException e) {
            logger.severe("Error checking room availability: " + e.getMessage());
        }
        return false;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>(); //List to hold all bookings
        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "SELECT b.*, c.* FROM Bookings b " +
                        "JOIN Customers c ON b.customer_id = c.id " +
                        "ORDER BY b.check_in_date"; //Sql query
            
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) { //Execute query
                
                while (rs.next()) {
                    Customer customer = new Customer(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address")
                    );
                    
                    Booking booking = new Booking(
                        rs.getInt("id"),
                        customer,
                        RoomType.valueOf(rs.getString("room_type")),
                        dateFormat.format(rs.getDate("check_in_date")),
                        dateFormat.format(rs.getDate("check_out_date")),
                        rs.getBoolean("breakfast")
                    );
                    
                    bookings.add(booking); //Add each booking to list
                }
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving bookings: " + e.getMessage());
        }
        return bookings; //Return list of bookings
    } 
}