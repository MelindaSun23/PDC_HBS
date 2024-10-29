/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;


public class CostCalculator {

    private static final double BREAKFAST_COST = 20.0;

    public static double calculateCost(Booking booking) {
        double baseCost = booking.getRoomType().getCost();
        return booking.isBfast() ? baseCost + BREAKFAST_COST : baseCost;
    }
}
