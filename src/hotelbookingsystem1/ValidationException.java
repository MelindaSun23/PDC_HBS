/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;

//Exception for validation errors
public class ValidationException extends RuntimeException {

    //Constructor that takes a message for exception
    public ValidationException(String message) { //Pass message to superclass
        super(message);
    }
}
