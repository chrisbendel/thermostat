package com.cb.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdateDetails {
    private int ambientTemp;
    private int heatTemp;
    private int coolTemp;
    private String mode;
    private boolean schedule;
    private LocalDateTime lastAlertTs;

    // Would prefer to use reflection but keeping this simple for brevity of exercise
    public String getPropertyValue(String field) {
        switch (field) {
            case "ambientTemp":
                return Integer.toString(getAmbientTemp());
            case "heatTemp":
                return Integer.toString(getHeatTemp());
            case "coolTemp":
                return Integer.toString(getCoolTemp());
            case "mode":
                return getMode();
            case "schedule":
                return Boolean.toString(isSchedule());
            case "lastAlertTs":
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return getLastAlertTs().format(formatter);
            default:
                return "Field " + field + " does not exist.";
        }
    }

    public int getAmbientTemp() {
        return ambientTemp;
    }

    public void setAmbientTemp(int ambientTemp) {
        this.ambientTemp = ambientTemp;
    }

    public int getHeatTemp() {
        return heatTemp;
    }

    public void setHeatTemp(int heatTemp) {
        this.heatTemp = heatTemp;
    }

    public int getCoolTemp() {
        return coolTemp;
    }

    public void setCoolTemp(int coolTemp) {
        this.coolTemp = coolTemp;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isSchedule() {
        return schedule;
    }

    public void setSchedule(boolean schedule) {
        this.schedule = schedule;
    }

    public LocalDateTime getLastAlertTs() {
        return lastAlertTs;
    }

    public void setLastAlertTs(LocalDateTime lastAlertTs) {
        this.lastAlertTs = lastAlertTs;
    }
}
