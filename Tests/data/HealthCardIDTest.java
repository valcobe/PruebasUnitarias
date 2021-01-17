package data;

import data.exceptions.FormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthCardIDTest {

    HealthCardID cardID1 = new HealthCardID("BBBBBBBBQR648597807024000012");
    HealthCardID cardID2 = new HealthCardID("BBBBBBBBAG541214555211116987");
    HealthCardID cardID3 = new HealthCardID("BBBBBBBBQR648597807024000012");

    HealthCardIDTest() throws FormatException {
    }

    @Test
    void getPersonalIDTest() {
        assertEquals("BBBBBBBBQR648597807024000012", cardID1.getPersonalID());
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
        String s = "HealthCardID{personal code='BBBBBBBBQR648597807024000012'}";
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