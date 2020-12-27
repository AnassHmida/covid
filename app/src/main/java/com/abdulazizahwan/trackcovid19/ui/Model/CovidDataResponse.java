package com.abdulazizahwan.trackcovid19.ui.Model;

import java.util.List;

public  class CovidDataResponse {

    private boolean _cacheHit;
    private DataEntity data;

    public boolean get_cacheHit() {
        return _cacheHit;
    }

    public void set_cacheHit(boolean _cacheHit) {
        this._cacheHit = _cacheHit;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private List<TimelineEntity> timeline;
        private Latest_dataEntity latest_data;
        private TodayEntity today;
        private String updated_at;
        private int population;
        private String code;
        private String name;
        private CoordinatesEntity coordinates;

        public List<TimelineEntity> getTimeline() {
            return timeline;
        }

        public void setTimeline(List<TimelineEntity> timeline) {
            this.timeline = timeline;
        }

        public Latest_dataEntity getLatest_data() {
            return latest_data;
        }

        public void setLatest_data(Latest_dataEntity latest_data) {
            this.latest_data = latest_data;
        }

        public TodayEntity getToday() {
            return today;
        }

        public void setToday(TodayEntity today) {
            this.today = today;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
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

    public static class TimelineEntity {
        private boolean is_in_progress;
        private String new_deaths;
        private String new_recovered;
        private String new_confirmed;
        private String recovered;
        private String active;
        private String confirmed;
        private String deaths;
        private String date;
        private String updated_at;

        public boolean getIs_in_progress() {
            return is_in_progress;
        }

        public void setIs_in_progress(boolean is_in_progress) {
            this.is_in_progress = is_in_progress;
        }

        public boolean isIs_in_progress() {
            return is_in_progress;
        }

        public String getNew_deaths() {
            return new_deaths;
        }

        public void setNew_deaths(String new_deaths) {
            this.new_deaths = new_deaths;
        }

        public String getNew_recovered() {
            return new_recovered;
        }

        public void setNew_recovered(String new_recovered) {
            this.new_recovered = new_recovered;
        }

        public String getNew_confirmed() {
            return new_confirmed;
        }

        public void setNew_confirmed(String new_confirmed) {
            this.new_confirmed = new_confirmed;
        }

        public String getRecovered() {
            return recovered;
        }

        public void setRecovered(String recovered) {
            this.recovered = recovered;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }

    public static class Latest_dataEntity {
        private CalculatedEntity calculated;
        private String critical;
        private String recovered;
        private String confirmed;
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

    public static class CalculatedEntity {
        private int cases_per_million_population;
        private double recovery_rate;
        private double death_rate;

        public int getCases_per_million_population() {
            return cases_per_million_population;
        }

        public void setCases_per_million_population(int cases_per_million_population) {
            this.cases_per_million_population = cases_per_million_population;
        }

        public double getRecovery_rate() {
            return recovery_rate;
        }

        public void setRecovery_rate(double recovery_rate) {
            this.recovery_rate = recovery_rate;
        }

        public double getDeath_rate() {
            return death_rate;
        }

        public void setDeath_rate(double death_rate) {
            this.death_rate = death_rate;
        }
    }

    public static class TodayEntity {
        private String confirmed;
        private String deaths;

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

    public static class CoordinatesEntity {
        private float longitude;
        private float latitude;

        public float getLongitude() {
            return longitude;
        }

        public void setLongitude(float longitude) {
            this.longitude = longitude;
        }

        public float getLatitude() {
            return latitude;
        }

        public void setLatitude(float latitude) {
            this.latitude = latitude;
        }
    }
}
