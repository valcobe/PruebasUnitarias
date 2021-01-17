package medicalconsultation;

import data.HealthCardID;
import medicalconsultation.exceptions.*;
import servicies.HealthNationalService;
import servicies.ScheduledVisitAgenda;

import java.net.ConnectException;
import java.util.Date;
import java.util.List;

public class ConsultationTerminal {

    HealthCardID healthCardID;
    HealthNationalService healthNationalService;
    ScheduledVisitAgenda scheduledVisitAgenda;
    MedicalPrescription medicalPrescription;
    List<ProductSpecification> prodList;
    ProductSpecification productSelected;

    public ConsultationTerminal() {
    }

    public void initRevision() throws HealthCardException, NotValidePrescriptionException, ConnectException {
        this.healthCardID = scheduledVisitAgenda.getHealthCardID();
        this.medicalPrescription = healthNationalService.getePrescription(this.healthCardID);
    }
    public void initPrescriptionEdition() throws AnyCurrentPrescriptionException, NotFinishedTreatmentException{
        if(this.medicalPrescription == null){throw new AnyCurrentPrescriptionException("Error: No hi ha cap prescripció en curs");}
        if(this.medicalPrescription.getEndDate().before(new Date())){throw new NotFinishedTreatmentException("Error: El tractament encara no ha finalitzat");}
    }
    public void searchForProducts(String keyWord) throws AnyKeyWordMedicineException, ConnectException{
        this.prodList = healthNationalService.getProductsByKW(keyWord);
    }
    public void selectProduct(int option) throws AnyMedicineSearchException, ConnectException{
        this.productSelected = healthNationalService.getProductSpecific(option);
    }
    public void enterMedicineGuidelines(String[] instruc) throws AnySelectedMedicineException, IncorrectTakingGuidelinesException{
        if(this.productSelected == null){throw new AnySelectedMedicineException("Error: No s'ha seleccionat cap medicament");}
        this.medicalPrescription.addLine(this.productSelected.getUPCcode(),instruc);
        this.prodList = null;
        this.productSelected = null;
    }
    public void enterTreatmentEndingDate(Date date) throws IncorrectEndingDateException {
        if(date.before(new Date())){throw new IncorrectEndingDateException("Error: La data és anterior a la data actual");}
        this.medicalPrescription.setPrescDate(new Date());
        this.medicalPrescription.setEndDate(date);
    }
    public void sendePrescription() throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription{
        this.medicalPrescription = healthNationalService.sendePrescription(this.medicalPrescription);
    }
    public void printePresc() throws printingException{}
}

