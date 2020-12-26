package com.abdulazizahwan.trackcovid19.ui.DataSets;

import com.abdulazizahwan.trackcovid19.ui.Model.AllCovidData;
import com.abdulazizahwan.trackcovid19.ui.Model.CovidDataList;
import com.abdulazizahwan.trackcovid19.ui.Model.CovidDataResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CovidAPI {


    @GET(" https://corona.lmao.ninja/v3/covid-19/all")
    Call<AllCovidData> getAllCovidData(

    );

    @GET("http://corona-api.com/countries/{code}")
    Call<CovidDataResponse> getCovidDataByCountry(
            @Path("code") String data
    );

    @GET(" https://corona-api.com/countries")
    Call<CovidDataList> getCovidCountriesList(
    );



}
