package ENUMS;

public enum AreaCode {
    PRISTINA(1, "Pristina"),
    MITROVICA(2, "Mitrovica"),
    PEJA(3, "Peja"),
    PRIZREN(4, "Prizren"),
    FERIZAJ(5, "Ferizaj"),
    GJILAN(6, "Gjilan"),
    GJAKOVA(7, "Gjakova");

    private final int code;
    private final String location;

    AreaCode(int code, String location) {
        this.code = code;
        this.location = location;
    }

    public int getCode() {
        return code;
    }
    @Override
    public String toString() {
        return location;
    }
}
