package medicalconsultation;

import data.ProductID;
import org.junit.jupiter.api.Test;
import servicies.exceptions.FormatException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductSpecificationTest {

    ProductSpecification pSpec = new ProductSpecification(new ProductID("prod12345678"), "Description1", new BigDecimal("10.05"));

    ProductSpecificationTest() throws FormatException {
    }

    @Test
    void getUPCcodeTest() throws FormatException {
        assertEquals(new ProductID("prod12345678"), pSpec.getUPCcode());
    }

    @Test
    void getDescriptionTest() throws FormatException {
        assertEquals("Description1", pSpec.getDescription());
    }

    @Test
    void getPriceTest() throws FormatException {
        assertEquals(new BigDecimal("10.05"), pSpec.getPrice());
    }
}