package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import medicalconsultation.exceptions.IncorrectTakingGuidelinesException;
import medicalconsultation.exceptions.ProductNotInPrescription;

import java.util.Date;
import java.util.HashMap;

public class MedicalPrescription {// A class that represents medical prescription
    private int prescCode;
    private Date prescDate;
    private Date endDate;
    private HealthCardID hcID; // the healthcard ID of the patient
    private DigitalSignature eSign; // the eSignature of the doctor
    private HashMap<ProductID,MedicinePrescriptionLine> mPLines;

    public MedicalPrescription(int prescCode, Date prescDate, Date endDate, HealthCardID hcID, DigitalSignature eSign) {
        this.prescCode = prescCode;
        this.prescDate = prescDate;
        this.endDate = endDate;
        this.hcID = hcID;
        this.eSign = eSign;
        this.mPLines = new HashMap<>();
    }

    public MedicalPrescription() {
    }

    public void addLine(ProductID prodID, String[] instruc) throws IncorrectTakingGuidelinesException {
        if(instruc.length!=6){throw new IncorrectTakingGuidelinesException("Error: No s'han introduit les dades correctament");}
        TakingGuideline tg = new TakingGuideline();
        Posology p = new Posology();
        // posology
        p.setDose(Float.valueOf(instruc[3]));
        p.setFreq(Float.valueOf(instruc[4]));
        p.setFreqUnit(FqUnit.valueOf(instruc[5]));

        // TakingGuideline
        tg.setdMoment(dayMoment.valueOf(instruc[0]));
        tg.setDuration(Float.valueOf(instruc[1]));
        tg.setInstructions(instruc[2]);
        tg.setPosology(p);

        MedicinePrescriptionLine mpLine = new MedicinePrescriptionLine(tg);
        mPLines.put(prodID,mpLine);
    }

    public void modifyLine(ProductID prodID, String[] instruc) throws ProductNotInPrescription, IncorrectTakingGuidelinesException {
        if(!mPLines.containsKey(prodID)){throw new ProductNotInPrescription("Error: No s'ha trobat el producte en cap línia de prescripció");}
        if(instruc.length!=0)
            addLine(prodID,instruc);
    }

    public void removeLine(ProductID prodID) throws ProductNotInPrescription {
        if(!mPLines.containsKey(prodID)){throw new ProductNotInPrescription("Error: No s'ha trobat el producte en cap línia de prescripció");}
        mPLines.remove(prodID);
    }

    public int getPrescCode() {
        return prescCode;
    }

    public void setPrescCode(int prescCode) {
        this.prescCode = prescCode;
    }

    public Date getPrescDate() {
        return prescDate;
    }

    public void setPrescDate(Date prescDate) {
        this.prescDate = prescDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public HealthCardID getHcID() {
        return hcID;
    }

    public void setHcID(HealthCardID hcID) {
        this.hcID = hcID;
    }

    public DigitalSignature geteSign() {
        return eSign;
    }

    public void seteSign(DigitalSignature eSign) {
        this.eSign = eSign;
    }

    public HashMap<ProductID, MedicinePrescriptionLine> getmPLines() {
        return mPLines;
    }

    public void setmPLines(HashMap<ProductID, MedicinePrescriptionLine> mPLines) {
        this.mPLines = mPLines;
    }
}
