package data;

import org.junit.jupiter.api.Test;
import servicies.exceptions.FormatException;

import static org.junit.jupiter.api.Assertions.*;

class HealthCardIDTest {

    HealthCardID cardID1 = new HealthCardID("123456789012");
    HealthCardID cardID2 = new HealthCardID("987654321098");
    HealthCardID cardID3 = new HealthCardID("123456789012");

    HealthCardIDTest() throws FormatException {
    }

    @Test
    void getPersonalIDTest() {
        assertEquals("123456789012", cardID1.getPersonalID());
    }

    @Test
    void equalsTest() {
        assertFalse(cardID1.equals(cardID2));
        assertTrue(cardID1.equals(cardID3));
    }

    @Test
    void hashCodeTest() {
        assertFalse(cardID1.hashCode()==cardID2.hashCode());
        assertTrue(cardID1.hashCode()==cardID3.hashCode());
    }

    @Test
    void toStringTest() {
        String s = "HealthCardID{personal code='123456789012'}";
        assertEquals(s, cardID1.toString());
    }

    @Test
    void ProductIDNullTest() {
        Exception exception = assertThrows(NullPointerException.class, () -> new HealthCardID(null));
        assertTrue(exception.getMessage().contains("Error: El codi es nul"));
    }

    @Test
    void IncorrectFormatCodeTest() {
        Exception exception = assertThrows(FormatException.class, () -> new HealthCardID("123456"));
        assertTrue(exception.getMessage().contains("Error: Format incorrecte"));
    }
}