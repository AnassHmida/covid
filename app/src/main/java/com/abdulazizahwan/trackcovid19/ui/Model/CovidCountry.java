package com.abdulazizahwan.trackcovid19.ui.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class CovidCountry implements Parcelable {
    String mCovidCountry, mTodayCases, mDeaths, mTodayDeaths, mRecovered, mActive, mCritical,mCode;
    int mCases;
    CalculatedEntity calculated;
    private String casesPerMillionPopulation;
    private String recoveryRate;
    private String deathRate;

    public String getCasesPerMillionPopulation() {
        return casesPerMillionPopulation;
    }

    public void setCasesPerMillionPopulation(String casesPerMillionPopulation) {
        this.casesPerMillionPopulation = casesPerMillionPopulation;
    }

    public String getRecoveryRate() {
        return recoveryRate;
    }

    public void setRecoveryRate(String recoveryRate) {
        this.recoveryRate = recoveryRate;
    }

    public String getDeathRate() {
        return deathRate;
    }

    public void setDeathRate(String deathRate) {
        this.deathRate = deathRate;
    }

    public CovidCountry(String mCovidCountry, int mCases, String mTodayCases, String mDeaths, String mTodayDeaths, String mRecovered, String mActive, String mCritical, String CountryCode, String casesPerMillionPopulation, String recoveryRate, String deathRate) {
        this.mCovidCountry = mCovidCountry;
        this.mCases = mCases;
        this.mTodayCases = mTodayCases;
        this.mDeaths = mDeaths;
        this.mTodayDeaths = mTodayDeaths;
        this.mRecovered = mRecovered;
        this.mActive = mActive;
        this.mCritical = mCritical;
        this.mCode = CountryCode;
        this.casesPerMillionPopulation = casesPerMillionPopulation;
        this.recoveryRate = recoveryRate;
        this.deathRate = deathRate;

    }

    public CalculatedEntity getCalculated() {
        return calculated;
    }

    public void setCalculated(CalculatedEntity calculated) {
        this.calculated = calculated;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmCovidCountry() {
        return mCovidCountry;
    }

    public int getmCases() {
        return mCases;
    }

    public String getmTodayCases() {
        return mTodayCases;
    }

    public String getmDeaths() {
        return mDeaths;
    }

    public String getmTodayDeaths() {
        return mTodayDeaths;
    }

    public String getmRecovered() {
        return mRecovered;
    }

    public String getmActive() {
        return mActive;
    }

    public String getmCritical() {
        return mCritical;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCovidCountry);
        dest.writeInt(this.mCases);
        dest.writeString(this.mTodayCases);
        dest.writeString(this.mDeaths);
        dest.writeString(this.mTodayDeaths);
        dest.writeString(this.mRecovered);
        dest.writeString(this.mActive);
        dest.writeString(this.mCritical);
        dest.writeString(this.mCode);
        dest.writeString(this.casesPerMillionPopulation);
        dest.writeString(this.recoveryRate);
        dest.writeString(this.deathRate);


    }

    protected CovidCountry(Parcel in) {
        this.mCovidCountry = in.readString();
        this.mCases = in.readInt();
        this.mTodayCases = in.readString();
        this.mDeaths = in.readString();
        this.mTodayDeaths = in.readString();
        this.mRecovered = in.readString();
        this.mActive = in.readString();
        this.mCritical = in.readString();
        this.mCode = in.readString();
        this.casesPerMillionPopulation = in.readString();
        this.recoveryRate = in.readString();
        this.deathRate = in.readString();

    }

    public static final Creator<CovidCountry> CREATOR = new Creator<CovidCountry>() {
        @Override
        public CovidCountry createFromParcel(Parcel source) {
            return new CovidCountry(source);
        }

        @Override
        public CovidCountry[] newArray(int size) {
            return new CovidCountry[size];
        }
    };
}
