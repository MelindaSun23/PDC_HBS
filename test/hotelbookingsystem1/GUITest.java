package hotelbookingsystem1;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import javax.swing.*;
import java.awt.*;

public class GUITest {
    private static LoginGUI loginGUI;
    private static HotelBookingGUI bookingGUI;

    @BeforeClass
    public static void setUpClass() {
        DatabaseManager.initializeDatabase();
    }

    @Test
    public void testLoginGUICreation() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                loginGUI = new LoginGUI();
                assertNotNull(loginGUI);
                assertTrue(loginGUI.isDisplayable());
                Component[] components = loginGUI.getContentPane().getComponents();
                assertTrue(components.length > 0);
            }
        });
    }

    @Test
    public void testBookingGUICreation() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                User testUser = new User(1, "testUser", false);
                bookingGUI = new HotelBookingGUI(testUser);
                assertNotNull(bookingGUI);
                assertTrue(bookingGUI.isDisplayable());
                JMenuBar menuBar = bookingGUI.getJMenuBar();
                assertNotNull(menuBar);
            }
        });
    }
}