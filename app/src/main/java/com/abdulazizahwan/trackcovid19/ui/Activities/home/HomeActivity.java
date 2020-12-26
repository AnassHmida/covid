package com.abdulazizahwan.trackcovid19.ui.Activities.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdulazizahwan.trackcovid19.R;

import com.abdulazizahwan.trackcovid19.ui.DataSets.CovidAPI;
import com.abdulazizahwan.trackcovid19.ui.Model.AllCovidData;
import com.abdulazizahwan.trackcovid19.ui.Model.CovidCountry;

import com.abdulazizahwan.trackcovid19.ui.Activities.Adapters.CovidCountryAdapter;
import com.abdulazizahwan.trackcovid19.ui.Activities.details.CovidCountryDetail;

import com.abdulazizahwan.trackcovid19.ui.Model.CovidDataList;
import com.abdulazizahwan.trackcovid19.ui.Model.CovidDataResponse;
import com.abdulazizahwan.trackcovid19.ui.Model.DataEntity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    ConstraintLayout MainUi;

    RecyclerView rvCovidCountry;
    ProgressBar progressBar;
    CovidCountryAdapter covidCountryAdapter;
    private TextView tvTotalConfirmed, tvTotalDeaths, tvTotalRecovered, tvLastUpdated;
    MenuItem sortcases,sortalpha,searchItem;

    private CovidAPI covidAPI;
    List<CovidCountry> covidCountries;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        tvTotalConfirmed = findViewById(R.id.textView5);
        tvTotalDeaths = findViewById(R.id.textView7);
        tvTotalRecovered = findViewById(R.id.textView6);
        tvLastUpdated = findViewById(R.id.textView8);
        progressBar = findViewById(R.id.progress_circular_home);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbarlayout);

        rvCovidCountry =  findViewById(R.id.country_ui);
        MainUi=  findViewById(R.id.main_ui);

        rvCovidCountry.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvCovidCountry.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.line_divider));
        rvCovidCountry.addItemDecoration(dividerItemDecoration);
        setupApi();
        //call list
        covidCountries = new ArrayList<>();

        // call Volley method
        getDataFromServerSortTotalCases();

        // call Volley
 getAllCovidData();


    }

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


    private String getDate(long milliSecond){

        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");

        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);
        return formatter.format(calendar.getTime());
    }

    private void getAllCovidData() {
        covidAPI.getAllCovidData().enqueue(new Callback<AllCovidData>() {

            @Override
            public void onResponse(Call<AllCovidData> call, retrofit2.Response<AllCovidData> response) {

                if (response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    tvTotalConfirmed.setText(refactorNumber(String.valueOf(response.body().getCases())));
                    tvTotalDeaths.setText(refactorNumber(String.valueOf(response.body().getDeaths())));
                    tvTotalRecovered.setText(refactorNumber(String.valueOf(response.body().getRecovered())));
                    tvLastUpdated.setText(refactorNumber(String.valueOf(response.body().getTodayCases())));
                }
            }

            @Override
            public void onFailure(Call<AllCovidData> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                String msg = "Failed to fetch data ";
                tvTotalConfirmed.setText(msg);
                tvTotalDeaths.setText(msg);
                tvTotalRecovered.setText(msg);
                tvLastUpdated.setText(msg);
            }
        });
    }


    private void getDataFromServerSortTotalCases() {


        covidAPI.getCovidCountriesList().enqueue(new Callback<CovidDataList>() {
            @Override
            public void onResponse(Call<CovidDataList> call, retrofit2.Response<CovidDataList> response) {

                if (response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    CovidDataList responseData  = response.body();


                    for (DataEntity data : responseData.getData()
                         ) {
                        covidCountries.add(new CovidCountry(
                                data.getName(),
                                Integer.parseInt(data.getLatestData().getConfirmed()),
                                data.getToday().getConfirmed(),
                                data.getLatestData().getDeaths(),
                                data.getToday().getDeaths(),
                               data.getLatestData().getRecovered(),
                                data.getLatestData().getCritical(),
                                data.getLatestData().getCritical(),
                               data.getCode()
                        ));
                    }



                    }

                    Log.d(TAG, "onResponse: +"+covidCountries.get(0).getmCases());
                    // sort descending
                    Collections.sort(covidCountries, new Comparator<CovidCountry>() {
                        @Override
                        public int compare(CovidCountry o1, CovidCountry o2) {
                            Log.d(TAG, "compare: "+o1.getmCases()+""+o2.getmCases());
                            if (o1.getmCases() >= o2.getmCases()) {
                                return -1;
                            } else {
                                return 1;
                            }
                        }
                    });

                    // Action Bar Title
                    // getActivity().setTitle(jsonArray.length() + " countries");

                    showRecyclerView();

                }


            @Override
            public void onFailure(Call<CovidDataList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "onResponse: " + t.getMessage());
            }
        });
    }




    private void getDataFromServerSortAlphabet() {


        covidAPI.getCovidCountriesList().enqueue(new Callback<CovidDataList>() {
            @Override
            public void onResponse(Call<CovidDataList> call, retrofit2.Response<CovidDataList> response) {

                if (response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    CovidDataList responseData  = response.body();


                    for (DataEntity data : responseData.getData()
                    ) {
                        covidCountries.add(new CovidCountry(
                                data.getName(),
                                Integer.parseInt(data.getLatestData().getConfirmed()),
                                data.getToday().getConfirmed(),
                                data.getLatestData().getDeaths(),
                                data.getToday().getDeaths(),
                                data.getLatestData().getRecovered(),
                                data.getLatestData().getCritical(),
                                data.getLatestData().getCritical(),
                                data.getCode()
                        ));
                    }

                }


                showRecyclerView();
            }


            @Override
            public void onFailure(Call<CovidDataList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "onResponse: " + t.getMessage());
            }
        });
    }


    private void showRecyclerView() {
        covidCountryAdapter = new CovidCountryAdapter(covidCountries, getApplicationContext());
        rvCovidCountry.setAdapter(covidCountryAdapter);

        ItemClickSupport.addTo(rvCovidCountry).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedCovidCountry(covidCountries.get(position));
            }
        });
    }

    private void displayProgressbars(){



    }

    private void showSelectedCovidCountry(CovidCountry covidCountry) {
        Intent covidCovidCountryDetail = new Intent(getApplicationContext() ,CovidCountryDetail.class);
        covidCovidCountryDetail.putExtra("EXTRA_COVID", covidCountry);
        startActivity(covidCovidCountryDetail);
    }

    @Override
    public  boolean  onCreateOptionsMenu(@NonNull final Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.country_menu, menu);
         searchItem = menu.findItem(R.id.action_search);
         sortalpha = menu.findItem(R.id.action_sort_alpha);
         sortcases = menu.findItem(R.id.action_sort_cases);

    sortalpha.setVisible(false);
    sortcases.setVisible(false);





        SearchView searchView = new SearchView(getBaseContext());
        searchView.setQueryHint("Search...");
        searchView.setMaxWidth(Integer.MAX_VALUE);


        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                MainUi.setVisibility(View.GONE);
                rvCovidCountry.setVisibility(View.VISIBLE);
                sortalpha.setVisible(true);
                sortcases.setVisible(true);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                MainUi.setVisibility(View.VISIBLE);
                rvCovidCountry.setVisibility(View.GONE);
                sortalpha.setVisible(false);
                sortcases.setVisible(false);
                return true;
            }
        });





        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (covidCountryAdapter != null) {
                    covidCountryAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });


        searchItem.setActionView(searchView);
       return  super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        searchItem = menu.findItem(R.id.action_search);
        sortalpha = menu.findItem(R.id.action_sort_alpha);
        sortcases = menu.findItem(R.id.action_sort_cases);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_alpha:
                Toast.makeText(getApplicationContext() ,"Sort Alphabetically", Toast.LENGTH_SHORT).show();
                covidCountries.clear();
                progressBar.setVisibility(View.VISIBLE);
               getDataFromServerSortAlphabet();
                return true;

            case R.id.action_sort_cases:
                Toast.makeText(getApplicationContext(), "Sort by Total Cases", Toast.LENGTH_SHORT).show();
               covidCountries.clear();
                progressBar.setVisibility(View.VISIBLE);
             getDataFromServerSortTotalCases();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    * Maj3oula mbech tconverti el number min  2787896 lel 2 787 896
    *
    * */
    private String refactorNumber(String value){

        DecimalFormatSymbols customSymbols = DecimalFormatSymbols.getInstance(Locale.US);
     //   customSymbols.setGroupingSeparator(' ');
        return new DecimalFormat("#,###", customSymbols).format(Integer.parseInt(value));
    }
}
