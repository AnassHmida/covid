package com.hanedi.covid19.ui.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalculatedEntity  implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
