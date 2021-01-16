package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import medicalconsultation.exceptions.IncorrectTakingGuidelinesException;
import medicalconsultation.exceptions.ProductNotInPrescription;
import org.junit.jupiter.api.Test;
import servicies.exceptions.FormatException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MedicalPrescriptionTest {
    Date date1 = new GregorianCalendar(2019, Calendar.FEBRUARY, 11).getTime();
    Date date2 = new GregorianCalendar(2019, Calendar.APRIL, 11).getTime();
    MedicalPrescription Mp = new MedicalPrescription(1234,date1,date2,new HealthCardID("BBBBBBBBQR648597807024000012"), new DigitalSignature("SIGNED".getBytes()));

    MedicalPrescriptionTest() throws FormatException {
    }

    @Test
    void addLineTest() throws FormatException, IncorrectTakingGuidelinesException {
        String[] instruct = new String [] {"BEFOREBREAKFAST","3.0","instruccions","2.0","6.0","DAY"};

        Mp.addLine(new ProductID("prod12345678"),instruct);
        Mp.addLine(new ProductID("prod11155528"),instruct);
        Mp.addLine(new ProductID("prod45788887"),instruct);

        assertTrue(Mp.getmPLines().containsKey(new ProductID("prod12345678")));
        assertTrue(Mp.getmPLines().containsKey(new ProductID("prod11155528")));
        assertTrue(Mp.getmPLines().containsKey(new ProductID("prod45788887")));
        assertFalse(Mp.getmPLines().containsKey(new ProductID("prod11122255")));
        assertFalse(Mp.getmPLines().containsKey(new ProductID("prod33665484")));

    }

    @Test
    void modifyLineTest() throws FormatException, IncorrectTakingGuidelinesException, ProductNotInPrescription {
        String[] instruct = new String [] {"BEFOREBREAKFAST","3.0","instruccions","2.0","6.0","DAY"};
        String[] instruct2 = new String [] {"AFTERLUNCH","5.0","instruccions2","1.0","8.0","WEEK"};

        Mp.addLine(new ProductID("prod12345678"),instruct);
        TakingGuideline tg = Mp.getmPLines().get(new ProductID("prod12345678")).getTg();

        assertEquals(tg.getdMoment().name(),"BEFOREBREAKFAST");
        assertEquals(tg.getDuration(),3.0);
        assertEquals(tg.getInstructions(),"instruccions");
        assertEquals(tg.getPosology().getDose(),2.0);
        assertEquals(tg.getPosology().getFreq(),6.0);
        assertEquals(tg.getPosology().getFreqUnit().name(),"DAY");

        Mp.modifyLine(new ProductID("prod12345678"),instruct2);
        tg = Mp.getmPLines().get(new ProductID("prod12345678")).getTg();
        assertEquals(tg.getdMoment().name(),"AFTERLUNCH");
        assertEquals(tg.getDuration(),5.0);
        assertEquals(tg.getInstructions(),"instruccions2");
        assertEquals(tg.getPosology().getDose(),1.0);
        assertEquals(tg.getPosology().getFreq(),8.0);
        assertEquals(tg.getPosology().getFreqUnit().name(),"WEEK");
    }

    @Test
    void removeLineTest() throws FormatException, IncorrectTakingGuidelinesException, ProductNotInPrescription {
        String[] instruct = new String [] {"BEFOREBREAKFAST","3.0","instruccions","2.0","6.0","DAY"};

        Mp.addLine(new ProductID("prod12345678"),instruct);
        Mp.addLine(new ProductID("prod11155528"),instruct);

        assertTrue(Mp.getmPLines().containsKey(new ProductID("prod12345678")));
        assertTrue(Mp.getmPLines().containsKey(new ProductID("prod11155528")));

        Mp.removeLine(new ProductID("prod12345678"));
        Mp.removeLine(new ProductID("prod11155528"));

        assertFalse(Mp.getmPLines().containsKey(new ProductID("prod12345678")));
        assertFalse(Mp.getmPLines().containsKey(new ProductID("prod11155528")));
    }

    @Test
    void ProductNotInPrescriptionTest() {
        String[] instruct = new String [] {"BEFOREBREAKFAST","3.0","instruccions","2.0","6.0","DAY"};

        // al modificar línia de prescripció
        Exception exception = assertThrows(ProductNotInPrescription.class, () -> Mp.modifyLine(new ProductID("prod12345678"),instruct));
        assertTrue(exception.getMessage().contains("Error: No s'ha trobat el producte en cap línia de prescripció"));

        // al borrar línia de prescripció
        exception = assertThrows(ProductNotInPrescription.class, () -> Mp.removeLine(new ProductID("prod12345678")));
        assertTrue(exception.getMessage().contains("Error: No s'ha trobat el producte en cap línia de prescripció"));
    }
}