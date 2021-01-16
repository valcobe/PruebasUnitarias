package medicalconsultation;

public class MedicinePrescriptionLine {
    private TakingGuideline tg;

    public MedicinePrescriptionLine(TakingGuideline tg) {
        this.tg = tg;
    }

    public TakingGuideline getTg() {
        return tg;
    }

    public void setTg(TakingGuideline tg) {
        this.tg = tg;
    }

}
