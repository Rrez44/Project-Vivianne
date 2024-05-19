package model;

import ENUMS.AreaCode;

public class AreaCodeStatistic {
    private AreaCode areaCode;
    private String totalLines;
    private String SuccessRate;
    private String HoursTraveled;

    public AreaCodeStatistic(AreaCode areaCode, String totalLines, String successRate, String hoursTraveled) {
        this.areaCode = areaCode;
        this.totalLines = totalLines;
        SuccessRate = successRate;
        HoursTraveled = hoursTraveled;
    }

    public AreaCode getAreaCode() {
        return areaCode;
    }

    public String getTotalLines() {
        return totalLines;
    }

    public String getSuccessRate() {
        return SuccessRate;
    }

    public String getHoursTraveled() {
        return HoursTraveled;
    }
}
