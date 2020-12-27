package com.hanedi.covid19.ui.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TodayEntity {
    @Expose
    @SerializedName("confirmed")
    private String confirmed;
    @Expose
    @SerializedName("deaths")
    private String deaths;


    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getDeaths() {
        return deaths;
    }

}
