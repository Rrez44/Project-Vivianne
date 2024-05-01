package ENUMS;

public enum BusType {
    MiniBus("Mini Bus"),
    SingleDeck("Single Deck"),
    Coach("Coach"),
    DoubleDecker("Double Decker");

    private final String label;

    BusType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}