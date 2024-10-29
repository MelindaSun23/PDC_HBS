/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidationUtils { //Utility class for validation methods

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void validateEmail(String email) { //Validates email format 
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Invalid email format"); //Throw exception for invalid format
        }
    }

    public static void validatePhone(String phone) { //Validates phone number to ensure it has at least 8 digits
        if (!phone.matches("\\d{8,}")) {
            throw new ValidationException("Phone number must have at least 8 digits");
        }
    }

    public static void validateDates(String checkIn, String checkOut) { //Validates check-in check-out dates
        try {
            Date inDate = dateFormat.parse(checkIn); //Parse check in
            Date outDate = dateFormat.parse(checkOut); //Parse checkvout
            Date today = new Date(); //Get todays date

            //Check if check in date is in the past
            if (inDate.before(today)) {
                throw new ValidationException("Check-in date cannot be in the past");
            }

            //Check if check out date is before check in date
            if (outDate.before(inDate)) {
                throw new ValidationException("Check-out date cannot be before check-in date");
            }
        } catch (ParseException e) {
            throw new ValidationException("Invalid date format. Use dd/MM/yyyy"); //Handle parse exception
        }
    }
}
