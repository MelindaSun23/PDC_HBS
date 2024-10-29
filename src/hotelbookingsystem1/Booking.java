/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;


public class Booking {

    private int id;
    private Customer customer;
    private RoomType roomType;
    private String inDate;
    private String outDate;
    private boolean Bfast;

    public Booking(Customer customer, RoomType roomType, String inDate, String outDate, boolean Bfast) {
        this(0, customer, roomType, inDate, outDate, Bfast);
    }

    public Booking(int id, Customer customer, RoomType roomType, String inDate, String outDate, boolean Bfast) {
        this.id = id;
        this.customer = customer;
        this.roomType = roomType;
        this.inDate = inDate;
        this.outDate = outDate;
        this.Bfast = Bfast;
    }

    // Getters
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

    Object getCheckInDate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    Object getCheckOutDate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    boolean hasBreakfast() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
