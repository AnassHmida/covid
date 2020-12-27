package com.hanedi.covid19.ui.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class AllCovidData implements Parcelable {

    private String updated;
    private String cases;
    private String todayCases;
    private String deaths;
    private String todayDeaths;
    private String recovered;
    private String todayRecovered;
    private String active;
    private String critical;
    private String casesPerOneMillion;
    private String deathsPerOneMillion;
    private String tests;
    private String testsPerOneMillion;
    private String population;
    private String oneCasePerPeople;
    private String oneDeathPerPeople;
    private String oneTestPerPeople;
    private String activePerOneMillion;
    private String recoveredPerOneMillion;
    private String criticalPerOneMillion;
    private String affectedCountries;

    public AllCovidData(String cases, String todayCases, String deaths, String recovered) {
        this.cases = cases;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    protected AllCovidData(Parcel in) {
        updated = in.readString();
        cases = in.readString();
        todayCases = in.readString();
        deaths = in.readString();
        todayDeaths = in.readString();
        recovered = in.readString();
        todayRecovered = in.readString();
        active = in.readString();
        critical = in.readString();
        casesPerOneMillion = in.readString();
        deathsPerOneMillion = in.readString();
        tests = in.readString();
        testsPerOneMillion = in.readString();
        population = in.readString();
        oneCasePerPeople = in.readString();
        oneDeathPerPeople = in.readString();
        oneTestPerPeople = in.readString();
        activePerOneMillion = in.readString();
        recoveredPerOneMillion = in.readString();
        criticalPerOneMillion = in.readString();
        affectedCountries = in.readString();
    }

    public static final Creator<AllCovidData> CREATOR = new Creator<AllCovidData>() {
        @Override
        public AllCovidData createFromParcel(Parcel in) {
            return new AllCovidData(in);
        }

        @Override
        public AllCovidData[] newArray(int size) {
            return new AllCovidData[size];
        }
    };

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(String todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public String getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public void setCasesPerOneMillion(String casesPerOneMillion) {
        this.casesPerOneMillion = casesPerOneMillion;
    }

    public String getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(String deathsPerOneMillion) {
        this.deathsPerOneMillion = deathsPerOneMillion;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public void setTestsPerOneMillion(String testsPerOneMillion) {
        this.testsPerOneMillion = testsPerOneMillion;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getOneCasePerPeople() {
        return oneCasePerPeople;
    }

    public void setOneCasePerPeople(String oneCasePerPeople) {
        this.oneCasePerPeople = oneCasePerPeople;
    }

    public String getOneDeathPerPeople() {
        return oneDeathPerPeople;
    }

    public void setOneDeathPerPeople(String oneDeathPerPeople) {
        this.oneDeathPerPeople = oneDeathPerPeople;
    }

    public String getOneTestPerPeople() {
        return oneTestPerPeople;
    }

    public void setOneTestPerPeople(String oneTestPerPeople) {
        this.oneTestPerPeople = oneTestPerPeople;
    }

    public String getActivePerOneMillion() {
        return activePerOneMillion;
    }

    public void setActivePerOneMillion(String activePerOneMillion) {
        this.activePerOneMillion = activePerOneMillion;
    }

    public String getRecoveredPerOneMillion() {
        return recoveredPerOneMillion;
    }

    public void setRecoveredPerOneMillion(String recoveredPerOneMillion) {
        this.recoveredPerOneMillion = recoveredPerOneMillion;
    }

    public String getCriticalPerOneMillion() {
        return criticalPerOneMillion;
    }

    public void setCriticalPerOneMillion(String criticalPerOneMillion) {
        this.criticalPerOneMillion = criticalPerOneMillion;
    }

    public String getAffectedCountries() {
        return affectedCountries;
    }

    public void setAffectedCountries(String affectedCountries) {
        this.affectedCountries = affectedCountries;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(updated);
        parcel.writeString(cases);
        parcel.writeString(todayCases);
        parcel.writeString(deaths);
        parcel.writeString(todayDeaths);
        parcel.writeString(recovered);
        parcel.writeString(todayRecovered);
        parcel.writeString(active);
        parcel.writeString(critical);
        parcel.writeString(casesPerOneMillion);
        parcel.writeString(deathsPerOneMillion);
        parcel.writeString(tests);
        parcel.writeString(testsPerOneMillion);
        parcel.writeString(population);
        parcel.writeString(oneCasePerPeople);
        parcel.writeString(oneDeathPerPeople);
        parcel.writeString(oneTestPerPeople);
        parcel.writeString(activePerOneMillion);
        parcel.writeString(recoveredPerOneMillion);
        parcel.writeString(criticalPerOneMillion);
        parcel.writeString(affectedCountries);
    }
}
