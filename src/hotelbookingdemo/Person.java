/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingdemo;

// abstract class for the person 
public abstract class Person { 
    private String name;
    private String email;
    private String phone;
    private String address;

    public Person(String name, String email, String phone, String address) { //constructor for user information
        this.name = name; //initialize name
        this.email = email; //initialize email
        this.phone = phone; //initialize phone
        this.address = address; //initialize address
    }

    // Getters
    public String getName() {
        return name; //return name
    }

    public String getEmail() {
        return email; //return email
    }

    public String getPhone() {
        return phone; //return phone
    }

    public String getAddress() {
        return address; //return address
    }
}
