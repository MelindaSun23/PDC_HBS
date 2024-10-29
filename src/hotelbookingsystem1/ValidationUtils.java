/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidationUtils {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void validateEmail(String email) {
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Invalid email format");
        }
    }

    public static void validatePhone(String phone) {
        if (!phone.matches("\\d{8,}")) {
            throw new ValidationException("Phone number must have at least 8 digits");
        }
    }

    public static void validateDates(String checkIn, String checkOut) {
        try {
            Date inDate = dateFormat.parse(checkIn);
            Date outDate = dateFormat.parse(checkOut);
            Date today = new Date();

            if (inDate.before(today)) {
                throw new ValidationException("Check-in date cannot be in the past");
            }

            if (outDate.before(inDate)) {
                throw new ValidationException("Check-out date cannot be before check-in date");
            }
        } catch (ParseException e) {
            throw new ValidationException("Invalid date format. Use dd/MM/yyyy");
        }
    }
}
