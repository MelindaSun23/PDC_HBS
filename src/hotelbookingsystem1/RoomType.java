/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;


public enum RoomType {
    STANDARD(80),
    DOUBLE(120),
    DELUXE(180),
    ENSUITE(220);
// the costs
    private final double cost;

    RoomType(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }
}
