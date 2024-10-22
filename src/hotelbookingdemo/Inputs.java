/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingdemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Inputs {
    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
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
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            System.out.print(prompt);
            input = scanner.nextLine().trim().toLowerCase();
        }
        return input.equals("yes");
    }

    public String getPhoneInput(String prompt) {
        System.out.print(prompt);
        String phone = scanner.nextLine();
        while (!phone.matches("\\d{8,}")) {  // Checks if phone number has at least 8 digits
            System.out.println("Invalid phone number. It should have at least 8 digits.");
            System.out.print(prompt);
            phone = scanner.nextLine();
        }
        return phone;
    }

    public String getDateInput(String prompt) {
        System.out.print(prompt);
        String dateStr = scanner.nextLine();
        try {
            dateFormat.setLenient(false);
            while (true) {
                dateFormat.parse(dateStr);
                if (dateFormat.parse(dateStr).before(dateFormat.parse("30/08/2024"))) {
                    System.out.println("Date must be after 30/08/2024. Please enter a valid date.");
                    System.out.print(prompt);
                    dateStr = scanner.nextLine();
                } else {
                    break;
                }
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter the date in dd/MM/yyyy format.");
            return getDateInput(prompt);  // Recursively call the method until valid input is provided
        }
        return dateStr;
    }
}
