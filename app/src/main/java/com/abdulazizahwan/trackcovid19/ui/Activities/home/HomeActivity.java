package com.abdulazizahwan.trackcovid19.ui.home;

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

import com.abdulazizahwan.trackcovid19.R;

import com.abdulazizahwan.trackcovid19.ui.Model.CovidCountry;
import com.abdulazizahwan.trackcovid19.ui.country.CovidCountryAdapter;
import com.abdulazizahwan.trackcovid19.ui.details.CovidCountryDetail;
import com.abdulazizahwan.trackcovid19.ui.country.ItemClickSupport;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    ConstraintLayout MainUi;

    RecyclerView rvCovidCountry;
    ProgressBar progressBar;
    CovidCountryAdapter covidCountryAdapter;
    private TextView tvTotalConfirmed, tvTotalDeaths, tvTotalRecovered, tvLastUpdated;
    MenuItem sortcases,sortalpha,searchItem;

  
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

        //call list
        covidCountries = new ArrayList<>();

        // call Volley method
        getDataFromServerSortTotalCases();

        // call Volley
        getData();
    }



    private String getDate(long milliSecond){

        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");

        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);
        return formatter.format(calendar.getTime());
    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String url = "https://corona.lmao.ninja/v3/covid-19/all";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    tvTotalConfirmed.setText(refactorNumber(jsonObject.getString("cases")));
                    tvTotalDeaths.setText(refactorNumber(jsonObject.getString("deaths")));
                    tvTotalRecovered.setText(refactorNumber(jsonObject.getString("recovered")));
                    tvLastUpdated.setText(refactorNumber(jsonObject.getString("todayCases")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.d("Error Response", error.toString());
            }
        });

        queue.add(stringRequest);
    }
    private void getDataFromServerSortTotalCases() {
        String url = "https://corona-api.com/countries";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArrayt = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArrayt.length(); i++) {
                            JSONObject data = jsonArrayt.getJSONObject(i);

                            // Extract JSONObject inside JSONObject
                            JSONObject latestdata = data.getJSONObject("latest_data");

                            covidCountries.add(new CovidCountry(
                                    data.getString("name"),
                                    latestdata.getInt("confirmed"),
                                    latestdata.getString("confirmed"),
                                    latestdata.getString("deaths"),
                                    latestdata.getString("deaths"),
                                    latestdata.getString("recovered"),
                                    latestdata.getString("critical"),
                                    latestdata.getString("critical"),
                                    data.getString("code")

                            ));
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e(TAG, "onResponse: " + error);
                    }
                });
        Volley.newRequestQueue(getBaseContext()).add(stringRequest);
    }

    private void getDataFromServerSortAlphabet() {
        String url = "https://corona.lmao.ninja/v2/countries";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);

                            // Extract JSONObject inside JSONObject
                            JSONObject countryInfo = data.getJSONObject("countryInfo");

                            covidCountries.add(new CovidCountry(
                                    data.getString("country"), data.getInt("cases"),
                                    data.getString("todayCases"), data.getString("deaths"),
                                    data.getString("todayDeaths"), data.getString("recovered"),
                                    data.getString("active"), data.getString("critical"),""
                            ));
                        }


                        showRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e(TAG, "onResponse: " + error);
                    }
                });
        Volley.newRequestQueue(getBaseContext()).add(stringRequest);
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
