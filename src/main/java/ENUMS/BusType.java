package ENUMS;

public enum BusType {
    MINIBUS("Mini Bus"),
    SINGLE_DECKER("Single Deck"),
    COACH("Coach"),
    DOUBLE_DECKER("Double Decker");

    private final String label;

    BusType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}