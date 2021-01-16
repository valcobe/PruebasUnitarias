package servicies;


import data.HealthCardID;
import medicalconsultation.exceptions.HealthCardException;

public interface ScheduledVisitAgenda {
    HealthCardID getHealthCardID() throws HealthCardException;
}
