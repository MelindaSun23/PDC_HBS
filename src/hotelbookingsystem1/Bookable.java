/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;

public interface Bookable {
// Books a room for customer with given dates, room type, and breakfast option

    boolean createBooking(Customer customer, RoomType roomType,
            String checkIn, String outDate, boolean breakfast);
}
