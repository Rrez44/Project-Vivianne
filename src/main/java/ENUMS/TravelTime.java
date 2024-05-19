package ENUMS;

public enum TravelTime {

    PRISTINA_GJILAN("1:30"),
    PRISTINA_MITROVICA("1:00"),
    PRISTINA_PEJA("1:00"),
    PRISTINA_PRIZREN("1:30"),
    PRISTINA_FERIZAJ("1:00"),
    PRISTINA_GJAKOVA("2:00"),
    MITROVICA_PEJA("1:00"),
    MITROVICA_PRIZREN("1:00"),
    MITROVICA_FERIZAJ("2:00"),
    MITROVICA_GJILAN("2:00"),
    MITROVICA_GJAKOVA("2:00"),
    PEJA_PRIZREN("2:00"),
    PEJA_FERIZAJ("1:00"),
    PEJA_GJILAN("1:00"),
    PEJA_GJAKOVA("1:00"),
    PEJA_PRISTINA("1:00"),
    PRIZREN_FERIZAJ("2:00"),
    PRIZREN_GJILAN("2:00"),
    PRIZREN_GJAKOVA("2:00"),
    FERIZAJ_GJILAN("2:00"),
    FERIZAJ_GJAKOVA("1:00"),
    GJILAN_GJAKOVA("1:00");


    String time ;
    TravelTime(String time){
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
