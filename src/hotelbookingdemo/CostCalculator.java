/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingdemo;

//class to calculate the costs,includes the cost if the user wants breakfast 
public class CostCalculator {
    //method to calculate booking total cost
    public static double calculateCost(Booking booking) {
        double cost = booking.getRoomType().getCost(); //initialize cost with room type
        if (booking.isBfast()) { //check if breakfast is inculded, add breakfast cost if true
            cost += 20; //adding cost
        }
        return cost; //returning total cost
    }
}