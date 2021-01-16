package data;

import medicalconsultation.exceptions.FormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductIDTest {

    ProductID prodID1 = new ProductID("prod12345678");
    ProductID prodID2 = new ProductID("prod98765432");
    ProductID prodID3 = new ProductID("prod12345678");

    ProductIDTest() throws FormatException {
    }

    @Test
    void getProductIDTest() {
        assertEquals("prod12345678", prodID1.getProductID());
    }

    @Test
    void equalsTest() {
        assertFalse(prodID1.equals(prodID2));
        assertTrue(prodID1.equals(prodID3));
    }

    @Test
    void hashCodeTest() {
        assertFalse(prodID1.hashCode()==prodID2.hashCode());
        assertTrue(prodID1.hashCode()==prodID3.hashCode());
    }

    @Test
    void toStringTest() {
        String s = "ProductID{product code='prod12345678'}";
        assertEquals(s, prodID1.toString());
    }

    @Test
    void ProductIDNullTest() {
        Exception exception = assertThrows(NullPointerException.class, () -> new ProductID(null));
        assertTrue(exception.getMessage().contains("Error: El codi es nul"));
    }

    @Test
    void IncorrectFormatCodeTest() {
        Exception exception = assertThrows(FormatException.class, () -> new ProductID("123456"));
        assertTrue(exception.getMessage().contains("Error: Format incorrecte"));
    }
}