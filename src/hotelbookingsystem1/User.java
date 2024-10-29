/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbookingsystem1;

public class User {

    private int id; //Unique user if
    private String username; //username for login
    private boolean isAdmin; //admin privillages flag

    //Constructor initializes user with id, username, and admin status
    public User(int id, String username, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    } //get users id

    public String getUsername() {
        return username;
    } //get users username

    public boolean isAdmin() {
        return isAdmin;
    } //check if user has admin rights
}
