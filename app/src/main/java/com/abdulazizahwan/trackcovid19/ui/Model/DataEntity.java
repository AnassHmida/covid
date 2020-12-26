package com.abdulazizahwan.trackcovid19.ui.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataEntity {
    @Expose
    @SerializedName("latest_data")
    private LatestDataEntity latestData;
    @Expose
    @SerializedName("today")
    private TodayEntity today;
    @Expose
    @SerializedName("updated_at")
    private String updatedAt;
    @Expose
    @SerializedName("population")
    private int population;
    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("coordinates")
    private CoordinatesEntity coordinates;

    public LatestDataEntity getLatestData() {
        return latestData;
    }

    public void setLatestData(LatestDataEntity latestData) {
        this.latestData = latestData;
    }

    public TodayEntity getToday() {
        return today;
    }

    public void setToday(TodayEntity today) {
        this.today = today;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordinatesEntity getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesEntity coordinates) {
        this.coordinates = coordinates;
    }
}
