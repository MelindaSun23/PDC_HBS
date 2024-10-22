/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingdemo;

//booking class handles bookings
public class Booking {
    private Customer customer; //customer who makes booking
    private RoomType roomType; //type of room
    private String inDate; //check in date
    private String outDate; // check out date
    private boolean Bfast; //breakfast

// Constructor to initialize details for booking
    public Booking(Customer customer, RoomType roomType, String inDate, String outDate, boolean Bfast) {
        this.customer = customer;
        this.roomType = roomType;
        this.inDate = inDate;
        this.outDate = outDate;
        this.Bfast = Bfast;
    }

//getter method for customer
    public Customer getCustomer() {
        return customer;
    }
//getter for room type
    public RoomType getRoomType() {
        return roomType;
    }
//getter check in date
    public String getinDate() {
        return inDate;
    }
//getter check out date
    public String getoutDate() { 
        return outDate;
    }
//getter for breakfast option
    public boolean isBfast() { 
        return Bfast;
    }
}