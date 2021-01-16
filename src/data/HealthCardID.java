package data;

import servicies.exceptions.FormatException;

final public class HealthCardID {

    private final String personalID;

    public HealthCardID(String code) throws FormatException {
        if (code == null){throw new NullPointerException("Error: El codi es nul");}
        if (code.length() != 28){ throw new FormatException("Error: Format incorrecte"); }
        this.personalID = code;
    }

    public String getPersonalID() {
        return personalID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthCardID hcardID = (HealthCardID) o;
        return personalID.equals(hcardID.personalID);
    }

    @Override
    public int hashCode() {
        return personalID.hashCode();
    }

    @Override public String toString() {
        return "HealthCardID{" + "personal code='" + personalID + '\'' + '}';
    }
}
