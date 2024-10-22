/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hotelbookingdemo;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class BookingManager implements Bookable {
    private List<Booking> bookings;
    private fileData fileData;
    private Inputs inputs;
    private Map<RoomType, List<Booking>> roomAvailability; // Map to track room availability

    public BookingManager() {
        bookings = loadBookings();
        fileData = new fileData();
        inputs = new Inputs();
        roomAvailability = new HashMap<>();
        initializeRoomAvailability(); // Initialize the map
    }

    private void initializeRoomAvailability() {
        for (RoomType type : RoomType.values()) {
            roomAvailability.put(type, new ArrayList<>()); // Initialize empty lists for each room type
        }
        // Populate the map with existing bookings
        for (Booking booking : bookings) {
            roomAvailability.get(booking.getRoomType()).add(booking);
        }
    }

    public void start() {
        boolean running = true;
        while (running) {
            try {
                System.out.println("Welcome to Hotel Serenity! Here are all the options\n");
                System.out.println("1. Book a room");
                System.out.println("2. View existing bookings");
                System.out.println("3. Exit program");
                int c = inputs.getIntInput("\nWhat would you like to do today?");
                switch (c) {
                    case 1 -> bookRoom();
                    case 2 -> fileData.displayBookings();
                    case 3 -> running = false;
                    default -> System.out.println("Invalid number, Please choose between 1-3");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    @Override
    public void bookRoom() {
        try {
            String name = inputs.getStringInput("Full Name: ");
            String email = inputs.getStringInput("Email: ");
            String phone = inputs.getPhoneInput("Phone number: ");
            String address = inputs.getStringInput("Address: ");

            Customer customer = new Customer(name, email, phone, address);

            System.out.println("Room Types: 1. STANDARD 2. DOUBLE 3. DELUXE 4. ENSUITE");
            int roomChoice = inputs.getIntInput("Room type: ");
            RoomType roomType = RoomType.values()[roomChoice - 1];

            String inDate = inputs.getDateInput("Check in date (dd/mm/yyyy): ");
            String outDate = inputs.getDateInput("Check out date (dd/mm/yyyy): ");
            boolean Bfast = inputs.getBooleanInput("Breakfast buffet (extra $20)? (yes/no): ");

            if (isRoomAvailable(roomType, inDate, outDate)) {
                Booking booking = new Booking(customer, roomType, inDate, outDate, Bfast);
                bookings.add(booking);
                roomAvailability.get(roomType).add(booking); // Update the map
                fileData.saveBookingToFile(booking);
                double totalCost = CostCalculator.calculateCost(booking);
                System.out.println("Booking made! Total comes to $:" + totalCost);
            } else {
                System.out.println("Room for these dates have already been reserved, please try again");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private boolean isRoomAvailable(RoomType roomType, String inDate, String outDate) {
        List<Booking> bookingsForType = roomAvailability.get(roomType);
        for (Booking booking : bookingsForType) {
            if ((inDate.compareTo(booking.getoutDate()) < 0) && (outDate.compareTo(booking.getinDate()) > 0)) {
                return false;
            }
        }
        return true;
    }

    private List<Booking> loadBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream("bookings.txt"))) {
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine().split(": ")[1];
                String email = scanner.nextLine().split(": ")[1];
                String phone = scanner.nextLine().split(": ")[1];
                String address = scanner.nextLine().split(": ")[1];
                String roomType = scanner.nextLine().split(": ")[1];
                String inDate = scanner.nextLine().split(": ")[1];
                String outDate = scanner.nextLine().split(": ")[1];
                boolean Bfast = Boolean.parseBoolean(scanner.nextLine().split(": ")[1]);
                bookings.add(new Booking(new Customer(name, email, phone, address), RoomType.valueOf(roomType), inDate, outDate, Bfast));
                if (scanner.hasNextLine()) scanner.nextLine(); // Skip separator line
            }
        } catch (FileNotFoundException e) {
            System.out.println("No bookings found.");
        }
        return bookings;
    }
}
