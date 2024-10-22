/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingdemo;

import java.io.*;
import java.util.Scanner;

//method to save booking details to bookings file
public class fileData {
    public void saveBookingToFile(Booking booking) { //using PW to create the bookings txt file
        try (PrintWriter writer = new PrintWriter(new FileOutputStream("bookings.txt", true))) {
            writer.println("Name: " + booking.getCustomer().getName());
            writer.println("Email: " + booking.getCustomer().getEmail());
            writer.println("Phone: " + booking.getCustomer().getPhone());
            writer.println("Address: " + booking.getCustomer().getAddress());
            writer.println("Room Type: " + booking.getRoomType());
            writer.println("Check-In Date: " + booking.getinDate());
            writer.println("Check-Out Date: " + booking.getoutDate());
            writer.println("Breakfast Included: " + booking.isBfast());
            writer.println("___________________________________");
        } catch (IOException e) {
            System.out.println("Error saving booking: " + e.getMessage());
        }
    }

    public void displayBookings() { //displays bookings
        try (Scanner scanner = new Scanner(new FileInputStream("bookings.txt"))) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("No bookings found."); //handles exceptions
        }
    }
}