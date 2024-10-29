/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;

//Enum representing different types of rooms available
public enum RoomType {
    STANDARD(80), //Standard room
    DOUBLE(120), //Double room
    DELUXE(180), //Deluxe room
    ENSUITE(220); //Ensuite

    //Cost associated with each room type
    private final double cost;

    //Constructor to set cost of each room type
    RoomType(double cost) {
        this.cost = cost;
    }

    //Getter to access cost of selected room
    public double getCost() {
        return cost;
    }
}
