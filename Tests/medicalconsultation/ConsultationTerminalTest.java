package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import medicalconsultation.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicies.HealthNationalService;
import servicies.ScheduledVisitAgenda;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ConsultationTerminalTest {
    ConsultationTerminal consultationTerminal = new ConsultationTerminal();

    public static class HealthNationalServiceTestDoble implements HealthNationalService {
        HashMap<HealthCardID,MedicalPrescription> pacients = new HashMap<>();
        HashMap<String, ProductSpecification> catalegProductes = new HashMap<>();
        List<ProductSpecification> productesTrobats = new ArrayList<>();

        @Override
        public MedicalPrescription getePrescription(HealthCardID hcID) throws HealthCardException, NotValidePrescriptionException, ConnectException {
            if(!pacients.containsKey(hcID)){throw new HealthCardException("Error: L'identificador del pacient no és incorrecte");}
            if(pacients.get(hcID).getPrescCode()==0){throw new NotValidePrescriptionException("Error: El pacient no té cap eReceta associada");}
            return pacients.get(hcID);
        }

        @Override
        public List<ProductSpecification> getProductsByKW(String keyWord) throws AnyKeyWordMedicineException, ConnectException {
            productesTrobats = new ArrayList<>();
            Iterator itr = catalegProductes.keySet().iterator();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                if(key.contains(keyWord))
                    productesTrobats.add(catalegProductes.get(key));
            }
            if(productesTrobats.size()==0){throw new AnyKeyWordMedicineException("Error: No s'ha trobat cap medicament");}
            return productesTrobats;
        }

        @Override
        public ProductSpecification getProductSpecific(int opt) throws AnyMedicineSearchException, ConnectException {
            if(productesTrobats.size()==0){throw new AnyMedicineSearchException("Error: No s'ha realitzat cap cerca");}
            return productesTrobats.get(opt);
        }

        @Override
        public MedicalPrescription sendePrescription(MedicalPrescription ePresc) throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription {
            ePresc.setPrescCode(ePresc.getPrescCode()+1);
            return ePresc;
        }
    }


    public static class ScheduledVisitAgendaTestDoble implements ScheduledVisitAgenda {
        HealthCardID cardID1 = new HealthCardID("BBBBBBBBQR648597807024000012");

        public ScheduledVisitAgendaTestDoble() throws FormatException {
        }

        @Override
        public HealthCardID getHealthCardID() throws HealthCardException {
            return cardID1;
        }
    }

    @BeforeEach
    public void BeforeEach() throws FormatException, IncorrectTakingGuidelinesException {
        consultationTerminal.healthNationalService = new HealthNationalServiceTestDoble();
        consultationTerminal.scheduledVisitAgenda = new ScheduledVisitAgendaTestDoble();

        HashMap<HealthCardID,MedicalPrescription> pacients = new HashMap<>();
        HashMap<String, ProductSpecification> catalegProductes = new HashMap<>();

        Date date1 = new GregorianCalendar(2019, Calendar.FEBRUARY, 11).getTime();
        Date date2 = new GregorianCalendar(2019, Calendar.APRIL, 11).getTime();

        MedicalPrescription medicalPrescription1 = new MedicalPrescription(7,date1,date2,
                new HealthCardID("BBBBBBBBQR648597807024000012"),new DigitalSignature("signedmetge1".getBytes()));
        MedicalPrescription medicalPrescription2 = new MedicalPrescription(2,date1,date2,
                new HealthCardID("BBBBBBBBQR648597807024000013"),new DigitalSignature("signedmetge1".getBytes()));
        MedicalPrescription medicalPrescription3 = new MedicalPrescription(5,date1,date2,
                new HealthCardID("BBBBBBBBQR648597807024000014"),new DigitalSignature("signedmetge1".getBytes()));
        MedicalPrescription medicalPrescription4 = new MedicalPrescription(8,date1,date2,
                new HealthCardID("BBBBBBBBQR648597807024000015"),new DigitalSignature("signedmetge1".getBytes()));

        String[] instruct = new String [] {"BEFOREBREAKFAST","3.0","instruccions","2.0","6.0","DAY"};
        String[] instruct2 = new String [] {"AFTERLUNCH","5.0","instruccions2","1.0","8.0","WEEK"};
        String[] instruct3 = new String [] {"BEFOREDINNER","2.0","instruccions3","6.0","3.0","MONTH"};

        medicalPrescription1.addLine(new ProductID("prod12345678"),instruct2);
        medicalPrescription1.addLine(new ProductID("prod12345679"),instruct3);
        medicalPrescription1.addLine(new ProductID("prod12345671"),instruct);
        medicalPrescription2.addLine(new ProductID("prod12345678"),instruct2);
        medicalPrescription2.addLine(new ProductID("prod12345672"),instruct2);
        medicalPrescription2.addLine(new ProductID("prod12345671"),instruct3);
        medicalPrescription3.addLine(new ProductID("prod12345671"),instruct2);
        medicalPrescription3.addLine(new ProductID("prod12345673"),instruct);
        medicalPrescription4.addLine(new ProductID("prod12345678"),instruct3);

        pacients.put(new HealthCardID("BBBBBBBBQR648597807024000012"),medicalPrescription1);
        pacients.put(new HealthCardID("BBBBBBBBQR648597807024000013"),medicalPrescription2);
        pacients.put(new HealthCardID("BBBBBBBBQR648597807024000014"),medicalPrescription3);
        pacients.put(new HealthCardID("BBBBBBBBQR648597807024000015"),medicalPrescription4);

        catalegProductes.put("paracetamol",new ProductSpecification(new ProductID("prod12345678"),"des1",new BigDecimal("3.0")));
        catalegProductes.put("ibuprofeno",new ProductSpecification(new ProductID("prod12345679"),"ibuprofeno past",new BigDecimal("2.0")));
        catalegProductes.put("espedifen",new ProductSpecification(new ProductID("prod12345671"),"des3",new BigDecimal("6.0")));
        catalegProductes.put("ibuprofeno sobres",new ProductSpecification(new ProductID("prod12345672"),"ibuprofeno en sobres",new BigDecimal("1.5")));
        catalegProductes.put("dalsy azul",new ProductSpecification(new ProductID("prod12345647"),"dalsy color azul",new BigDecimal("4.0")));
        catalegProductes.put("dalsy naranja",new ProductSpecification(new ProductID("prod12345615"),"dalsy color naranja",new BigDecimal("4.0")));
        catalegProductes.put("dalsy modificado",new ProductSpecification(new ProductID("prod12345622"),"dalsy modificado",new BigDecimal("4.0")));

        ((HealthNationalServiceTestDoble)consultationTerminal.healthNationalService).pacients = pacients;
        ((HealthNationalServiceTestDoble)consultationTerminal.healthNationalService).catalegProductes = catalegProductes;
    }

    @Test
    public void procesConsultaTest() throws NotValidePrescriptionException, HealthCardException, ConnectException, FormatException, AnyKeyWordMedicineException, AnyMedicineSearchException, AnySelectedMedicineException, IncorrectTakingGuidelinesException, NotCompletedMedicalPrescription, NotValidePrescription, eSignatureException, IncorrectEndingDateException {
        Date datafin = new GregorianCalendar(2021, Calendar.APRIL, 11).getTime();
        String[] instruct = new String [] {"DURINGDINNER","8.0","instruccions5","9.0","1.0","DAY"};
        String[] instruc2 = new String [] {"BEFORELUNCH","3.0","instruccions8","6.0","1.0","WEEK"};
        ProductID productID;

        // test initRevision
        consultationTerminal.initRevision();
        assertTrue(consultationTerminal.healthCardID.equals(new HealthCardID("BBBBBBBBQR648597807024000012")));
        assertTrue(consultationTerminal.medicalPrescription.getHcID().equals(new HealthCardID("BBBBBBBBQR648597807024000012")));

        // test searchForProducts i selectProduct
        consultationTerminal.searchForProducts("dalsy");
        assertEquals(3,consultationTerminal.prodList.size());
        consultationTerminal.selectProduct(0);
        assertTrue(consultationTerminal.productSelected.getDescription().equals("dalsy color naranja"));
        consultationTerminal.selectProduct(1);
        assertTrue(consultationTerminal.productSelected.getDescription().equals("dalsy color azul"));
        consultationTerminal.selectProduct(2);
        assertTrue(consultationTerminal.productSelected.getDescription().equals("dalsy modificado"));

        // test enterMedicineGuidelines
        consultationTerminal.enterMedicineGuidelines(instruct);
        consultationTerminal.searchForProducts("ibuprofeno");
        assertEquals(2,consultationTerminal.prodList.size());
        consultationTerminal.selectProduct(0);
        assertEquals("ibuprofeno past",consultationTerminal.productSelected.getDescription());
        consultationTerminal.selectProduct(1);
        assertEquals("ibuprofeno en sobres",consultationTerminal.productSelected.getDescription());

        productID = consultationTerminal.productSelected.getUPCcode();
        assertFalse(consultationTerminal.medicalPrescription.getmPLines().containsKey(productID));
        consultationTerminal.enterMedicineGuidelines(instruct);
        assertTrue(consultationTerminal.medicalPrescription.getmPLines().containsKey(productID));

        // test generar nou PrescCode
        consultationTerminal.enterTreatmentEndingDate(datafin);
        assertEquals(7,consultationTerminal.medicalPrescription.getPrescCode());
        consultationTerminal.sendePrescription();
        assertEquals(8,consultationTerminal.medicalPrescription.getPrescCode());
    }
}