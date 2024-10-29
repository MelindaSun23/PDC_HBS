/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;

public class Booking {

    private int id; //Unique booking id
    private Customer customer; //Customer info
    private RoomType roomType; //Room type chosen by customer
    private String inDate; //Check in data
    private String outDate; //Check out date
    private boolean Bfast; //Breakfast option

    public Booking(Customer customer, RoomType roomType, String inDate, String outDate, boolean Bfast) {
        this(0, customer, roomType, inDate, outDate, Bfast); //Calls main constructer with default id
    }

    public Booking(int id, Customer customer, RoomType roomType, String inDate, String outDate, boolean Bfast) {
        this.id = id; //Set booking id
        this.customer = customer; //Set customer info
        this.roomType = roomType; //Set room type
        this.inDate = inDate; //Set check in date
        this.outDate = outDate; //Set check out date
        this.Bfast = Bfast; //Set breakfast option
    }

    // Getters, retrieve booking id, customer info, room type, check in date, check out date, and checking if breakfast has been included
    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public String getinDate() {
        return inDate;
    }

    public String getoutDate() {
        return outDate;
    }

    public boolean isBfast() {
        return Bfast;
    }
}
