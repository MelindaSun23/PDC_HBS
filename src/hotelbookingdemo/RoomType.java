/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingdemo;


//enum for the room type and also calculates the final cost 
public enum RoomType {
    STANDARD(80),
    DOUBLE(120),
    DELUXE(180),
    ENSUITE(220);

    private final double cost; //cost of room type

    //constructor to initialize cost
    RoomType(double cost) {
        this.cost = cost;
    }
    //method to get cost of room type
    public double getCost() {
        return cost;
    }
}
