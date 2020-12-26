package com.abdulazizahwan.trackcovid19.ui.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CovidDataList {


    @Expose
    @SerializedName("_cacheHit")
    private boolean Cachehit;
    @Expose
    @SerializedName("data")
    private List<DataEntity> data;

    public boolean getCachehit() {
        return Cachehit;
    }

    public void setCachehit(boolean Cachehit) {
        this.Cachehit = Cachehit;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }
}

