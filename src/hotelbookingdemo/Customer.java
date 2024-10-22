/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingdemo;

// customer class that extends person class
public class Customer extends Person {
    //constructor for customer class
    public Customer(String name, String email, String phone, String address) {
        
        super(name, email, phone, address);//call to the person constructor (superclass)
    }
}
