package com.hanedi.covid19.ui.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.hanedi.covid19.R;
import com.hanedi.covid19.ui.Activities.home.HomeActivity;
import com.hanedi.covid19.ui.DataSets.CovidAPI;
import com.hanedi.covid19.ui.Model.AllCovidData;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

public class IntroActivity extends Activity {

    private CovidAPI covidAPI;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);


        setupApi();
        getAllCovidData();

    }


    /**
     *Initialisation lel Retro fit
     *
     */

    private void setupApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        covidAPI = new retrofit2.Retrofit.Builder()
                .client(client)
                .baseUrl("http://corona-api.com/countries/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CovidAPI.class);
    }

    /**
     * Tharba mta3 el API loula bech tjib el global  data , number mta3 el recovered gloval etc , bech yet7atou filHome Acitivity
     * */
    private void getAllCovidData() {
        covidAPI.getAllCovidData().enqueue(new Callback<AllCovidData>() {

            @Override
            public void onResponse(Call<AllCovidData> call, retrofit2.Response<AllCovidData> response) {

                if (response.body() != null) {

                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            response.body().setCases(refactorNumber(String.valueOf(response.body().getCases())));
                            response.body().setRecovered(refactorNumber(String.valueOf(response.body().getRecovered())));
                            response.body().setDeaths(refactorNumber(String.valueOf(response.body().getDeaths())));
                            response.body().setTodayCases(refactorNumber(String.valueOf(response.body().getTodayCases())));
                            Intent covidCovidHome = new Intent(getApplicationContext() , HomeActivity.class);
                            covidCovidHome.putExtra("EXTRA_COVID", response.body());
                            startActivity(covidCovidHome);
                        }
                    }, 2000);



                }
            }

            @Override
            public void onFailure(Call<AllCovidData> call, Throwable t) {


                final Handler handler = new Handler(Looper.getMainLooper());

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent covidCovidHome = new Intent(getApplicationContext() , HomeActivity.class);
                        covidCovidHome.putExtra("EXTRA_COVID",new AllCovidData("Failed to fetch Data","Failed to fetch Data","Failed to fetch Data","Failed to fetch Data"));
                        startActivity(covidCovidHome);
                    }
                }, 2000);

            }
        });
    }
    /**
     * Hethi Maj3oula mbech tconverti el number par exmple min  2787896 lel 2,787,896
     *
     * */

    private String refactorNumber(String value){

        DecimalFormatSymbols customSymbols = DecimalFormatSymbols.getInstance(Locale.US);
        //   customSymbols.setGroupingSeparator(' ');
        return new DecimalFormat("#,###", customSymbols).format(Integer.parseInt(value));
    }
}
