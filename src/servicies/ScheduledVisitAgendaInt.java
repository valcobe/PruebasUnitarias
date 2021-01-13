package servicies;

import data.HealthCardID;
import servicies.exceptions.HealthCardException;

public interface ScheduledVisitAgendaInt {
    HealthCardID getHealthCardID() throws HealthCardException;
}
