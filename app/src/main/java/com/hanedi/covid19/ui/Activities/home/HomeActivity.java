package com.hanedi.covid19.ui.Activities.home;

import android.content.Intent;
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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanedi.covid19.R;

import com.hanedi.covid19.ui.DataSets.CovidAPI;
import com.hanedi.covid19.ui.Model.AllCovidData;
import com.hanedi.covid19.ui.Model.CovidCountry;

import com.hanedi.covid19.ui.Activities.Adapters.CovidCountryAdapter;
import com.hanedi.covid19.ui.Activities.details.CovidCountryDetailActivity;

import com.hanedi.covid19.ui.Model.CovidDataList;
import com.hanedi.covid19.ui.Model.DataEntity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

/**
 *
 *      Hethi onCreate , bech ysir feha intiilaization mta3 el views , w bech na3mel appele lel
 *
 *          setupApi(); bech nraka7 initilization lel API mta3 el retorfit
 *
 *         getDataFromServerSortTotalCases(); bech n7ather el lista mta3i tkoun 7athra kif tenzel 3al bouton mta3 el recherche
 *
*          getAllCovidData(); , bech tjibli el items global bech yet7atou fil home ( mta3 Global deaths w global recovered )
*
 *
 * */
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

        AllCovidData covidCountry = getIntent().getParcelableExtra("EXTRA_COVID");
        getAllCovidData(covidCountry);

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
    private void getAllCovidData(AllCovidData covidCountry) {



                if (covidCountry != null) {
                    progressBar.setVisibility(View.GONE);
                    tvTotalConfirmed.setText(covidCountry.getCases());
                    tvTotalDeaths.setText(covidCountry.getDeaths());
                    tvTotalRecovered.setText(covidCountry.getRecovered());
                    tvLastUpdated.setText(covidCountry.getTodayCases());
                }


    }


    /**
     * Tharba mta3 API bech yjib el Data mta3 el lista mta3 el recherche loula kif tenzel 3al bouton
     *
     *      * La79i9a lahna ken tchouf fil oncreate , hethi yet3mlelha ghadi appele bech tjib lista meloul lel recherche mba3ed ken t7eb zeid
     * ta3meleha appele ki tenzel 3al bouton mta3 el filtre bel number of cases
     * */
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
                                data.getCode(),
                                String.valueOf(data.getLatestData().getCalculated().getCasesPerMillionPopulation()),
                                String.valueOf(data.getLatestData().getCalculated().getRecoveryRate()),
                                String.valueOf(data.getLatestData().getCalculated().getDeathRate())

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

                    showRecyclerView();

                }


            @Override
            public void onFailure(Call<CovidDataList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "onResponse: " + t.getMessage());
            }
        });
    }

/**
 *
 *
 * Tharba 3al API bech yjiblek el list mfeltriya bil ALPHABET A- Z
 *
 * */


    private void getDataFromServerSortAlphabet() {


        covidAPI.getCovidCountriesList().enqueue(new Callback<CovidDataList>() {
            @Override
            public void onResponse(Call<CovidDataList> call, retrofit2.Response<CovidDataList> response) {

                if (response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    CovidDataList responseData  = response.body();


                    for (DataEntity data : responseData.getData()
                    ) {

                        Log.d(TAG, "onResponse: "+String.valueOf(data.getLatestData().getCalculated().getCasesPerMillionPopulation()));
                        covidCountries.add(new CovidCountry(
                                data.getName(),
                                Integer.parseInt(data.getLatestData().getConfirmed()),
                                data.getToday().getConfirmed(),
                                data.getLatestData().getDeaths(),
                                data.getToday().getDeaths(),
                                data.getLatestData().getRecovered(),
                                data.getLatestData().getCritical(),
                                data.getLatestData().getCritical(),
                                data.getCode(),
                                String.valueOf(data.getLatestData().getCalculated().getCasesPerMillionPopulation()),
                                String.valueOf(data.getLatestData().getCalculated().getRecoveryRate()),
                                String.valueOf(data.getLatestData().getCalculated().getDeathRate())

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
/**
 * Lahna juste intiolaization Lel Lista mta3 el ITEMs eli bech yothhrou fil lista mta3 el recherche , w yzid onclick listener 3al items eli
 *  kif bech tenzel 3ala item fel lista thehzek lel details
 *
 *  hanou y7ather fil adapter mta3 el items w ba3ed y7ot fil listener 3al item
 *
 * */

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



    /**
     *
     * Lahna intent bech yemchi lel lista mta3 el details bil informatiosn eli 3andou 3al country heka
     *
     * */
    private void showSelectedCovidCountry(CovidCountry covidCountry) {
        Intent covidCovidCountryDetail = new Intent(getApplicationContext() , CovidCountryDetailActivity.class);
        covidCovidCountryDetail.putExtra("EXTRA_COVID", covidCountry);
        startActivity(covidCovidCountryDetail);
    }
    /**
     * Hetheya juste appele lel menu bech yothhor fil toolbar
     *
     * Fil setOnActionExpandListener , 3amel faza enou ken nzel 3al bouton mta3 el recherche , bech ysir appele lel onMenuItemActionExpandw
     * Bech ywarrih el interface mta3 el Lista , , w ken nzed 3al boutonmta3 Back , bech ysir appelle lel  onMenuItemActionCollapse,
     *  w bech yarja3 el interface mta3 el home el 3adeya $
     *
     *
     * */

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


/**
 *
 *
 * lahna mabadeltech barcha , chouf el tuto mta3ek
 *
 *
 * */


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


    /***
     *
     * Lahna chnouwa ysir kif tenzel 3al bouton mta3 el filtre
     *
     *
     * */
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
