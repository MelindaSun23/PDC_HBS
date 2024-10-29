/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package hotelbookingsystem1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.After;

public class InputsTest {
    
    private Inputs inputs;
    
    @Before
    public void setUp() {
        inputs = new Inputs();
    }

    @Test
    public void testGetStringInput_ValidInput() {
        String input = "Sophie Red\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        Inputs instance = new Inputs();
        String result = instance.getStringInput("Enter name: ");
        assertEquals("Sophie Red", result);
    }

    @Test(expected = ValidationException.class)
    public void testGetStringInput_EmptyInput() {
        String input = "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        Inputs instance = new Inputs();
        instance.getStringInput("Enter name: ");
    }

    @Test
    public void testGetIntInput_ValidInput() {
        String input = "42\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        Inputs instance = new Inputs();
        int result = instance.getIntInput("Enter number: ");
        assertEquals(42, result);
    }

    @Test
    public void testGetBooleanInput_ValidYesInput() {
        String input = "yes\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        Inputs instance = new Inputs();
        boolean result = instance.getBooleanInput("Enter yes/no: ");
        assertTrue(result);
    }

    @Test
    public void testGetBooleanInput_ValidNoInput() {
        String input = "no\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        Inputs instance = new Inputs();
        boolean result = instance.getBooleanInput("Enter yes/no: ");
        assertFalse(result);
    }

    @Test
    public void testGetPhoneInput_ValidInput() {
        String input = "0123456789\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        Inputs instance = new Inputs();
        String result = instance.getPhoneInput("Enter phone: ");
        assertEquals("0123456789", result);
    }

    @Test
    public void testGetDateInput_ValidInput() {
        String input = "25/12/2024\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        Inputs instance = new Inputs();
        String result = instance.getDateInput("Enter date: ");
        assertEquals("25/12/2024", result);
    }

    @After
    public void restoreSystemInput() {
        System.setIn(System.in);
    }
}