package com.abdulazizahwan.trackcovid19.ui.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalculatedEntity {
    @Expose
    @SerializedName("cases_per_million_population")
    private int casesPerMillionPopulation;
    @Expose
    @SerializedName("recovery_rate")
    private double recoveryRate;
    @Expose
    @SerializedName("death_rate")
    private double deathRate;

    public int getCasesPerMillionPopulation() {
        return casesPerMillionPopulation;
    }

    public void setCasesPerMillionPopulation(int casesPerMillionPopulation) {
        this.casesPerMillionPopulation = casesPerMillionPopulation;
    }

    public double getRecoveryRate() {
        return recoveryRate;
    }

    public void setRecoveryRate(double recoveryRate) {
        this.recoveryRate = recoveryRate;
    }

    public double getDeathRate() {
        return deathRate;
    }

    public void setDeathRate(double deathRate) {
        this.deathRate = deathRate;
    }
}
