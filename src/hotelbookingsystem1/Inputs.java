/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Logger;

public class Inputs {
    private static final Logger logger = Logger.getLogger(Inputs.class.getName());
    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public String getStringInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            throw new ValidationException("Input cannot be empty");
        }
        return input;
    }

    public int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            logger.warning("Invalid integer input received");
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return input;
    }

    public boolean getBooleanInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim().toLowerCase();
        while (!input.equals("yes") && !input.equals("no")) {
            logger.warning("Invalid boolean input received: " + input);
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            System.out.print(prompt);
            input = scanner.nextLine().trim().toLowerCase();
        }
        return input.equals("yes");
    }

    public String getPhoneInput(String prompt) {
        System.out.print(prompt);
        String phone = scanner.nextLine().trim();
        try {
            ValidationUtils.validatePhone(phone);
            return phone;
        } catch (ValidationException e) {
            logger.warning("Invalid phone input received: " + phone);
            System.out.println(e.getMessage());
            return getPhoneInput(prompt);
        }
    }

    public String getDateInput(String prompt) {
        System.out.print(prompt);
        String dateStr = scanner.nextLine().trim();
        try {
            ValidationUtils.validateDates(dateStr, dateStr);
            return dateStr;
        } catch (ValidationException e) {
            logger.warning("Invalid date input received: " + dateStr);
            System.out.println(e.getMessage());
            return getDateInput(prompt);
        }
    }
}
