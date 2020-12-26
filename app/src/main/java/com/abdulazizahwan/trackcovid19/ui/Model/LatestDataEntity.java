package com.abdulazizahwan.trackcovid19.ui.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatestDataEntity {
    @Expose
    @SerializedName("calculated")
    private CalculatedEntity calculated;
    @Expose
    @SerializedName("critical")
    private String critical;
    @Expose
    @SerializedName("recovered")
    private String recovered;
    @Expose
    @SerializedName("confirmed")
    private String confirmed;
    @Expose
    @SerializedName("deaths")
    private String deaths;

    public CalculatedEntity getCalculated() {
        return calculated;
    }

    public void setCalculated(CalculatedEntity calculated) {
        this.calculated = calculated;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }
}
