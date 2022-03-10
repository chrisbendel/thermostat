package com.cb.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class ThermostatUpdate {
    private LocalDateTime updateTime;
    private UpdateDetails update;

    public void merge(UpdateDetails previousUpdate) {

        UpdateDetails currentUpdate = this.getUpdate();
        // updateTime will always be available, just merge update details
        currentUpdate.setAmbientTemp(
            currentUpdate.getAmbientTemp() == 0 ? previousUpdate.getAmbientTemp() : currentUpdate.getAmbientTemp()
        );
        currentUpdate.setHeatTemp(
            currentUpdate.getHeatTemp() == 0 ? previousUpdate.getHeatTemp() : currentUpdate.getHeatTemp()
        );
        currentUpdate.setCoolTemp(
            currentUpdate.getCoolTemp() == 0 ? previousUpdate.getCoolTemp() : currentUpdate.getCoolTemp()
        );
        currentUpdate.setMode(
            currentUpdate.getMode() == null ? previousUpdate.getMode() : currentUpdate.getMode()
        );
        currentUpdate.setSchedule(
            currentUpdate.isSchedule() ? previousUpdate.isSchedule() : currentUpdate.isSchedule()
        );
        currentUpdate.setLastAlertTs(
            currentUpdate.getLastAlertTs() == null ? previousUpdate.getLastAlertTs() : currentUpdate.getLastAlertTs()
        );
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @JsonSerialize(as= UpdateDetails.class)
    public UpdateDetails getUpdate() {
        return update;
    }

    public void setUpdate(UpdateDetails update) {
        this.update = update;
    }
}