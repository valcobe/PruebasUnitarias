package data;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DigitalSignatureTest {

    @Test
    public void equalsTest(){
        DigitalSignature d1 = new DigitalSignature("SIGNED".getBytes());
        DigitalSignature d2 = new DigitalSignature("SIGNED".getBytes());
        DigitalSignature d3 = new DigitalSignature("SIGNET".getBytes());
        assertTrue(d1.equals(d2));
        assertFalse(d1.equals(d3));
    }

    @Test
    public void hashCodeTest(){
        DigitalSignature d1 = new DigitalSignature("SIGNED".getBytes());
        DigitalSignature d2 = new DigitalSignature("SIGNED".getBytes());
        assertTrue(d1.hashCode() == d2.hashCode());
    }

    @Test
    public void toStringTest(){
        DigitalSignature d1 = new DigitalSignature("SIGNED".getBytes());
        String s = "Signature{signature='" + Arrays.toString("SIGNED".getBytes()) + "'}";
        assertEquals(s, d1.toString());
    }
}