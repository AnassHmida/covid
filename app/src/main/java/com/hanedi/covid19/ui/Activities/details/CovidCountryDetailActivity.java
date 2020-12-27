package com.hanedi.covid19.ui.Activities.details;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.hanedi.covid19.R;
import com.hanedi.covid19.ui.DataSets.CovidAPI;
import com.hanedi.covid19.ui.Model.CovidCountry;
import com.hanedi.covid19.ui.Model.CovidDataResponse;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class CovidCountryDetailActivity extends AppCompatActivity {

    TextView tvDetailCountryName, tvDetailTotalCases, tvDetailTodayCases, tvDetailTotalDeaths,
            tvDetailTodayDeaths, tvDetailTotalRecovered, tvDetailTotalActive, tvDetailTotalCritical,tvDetailRecoveryRate,tvDetailDeathRate,tvDetailPerMillion,
    smallDetailTotalCases ,smallDetailTotalRecovered,smallDetailDeathCases,smallDetailTotalDeathCases,
    smallDetailTotalnewCases ,smallDetailTotalnewRecovered;
String mCode;
    String mCountry;


    private CovidAPI covidAPI;

    private LineChart lineChart;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_covid_country_detail);

        lineChart = findViewById(R.id.activity_main_linechart);

        CovidCountry covidCountry = getIntent().getParcelableExtra("EXTRA_COVID");
        mCode = covidCountry.getmCode();
        mCountry = covidCountry.getmCovidCountry();


        /**
         *
         * Configuration mta3 el Line chart
         *
         * SetupApi mta3 el retrifit
         *
         * getCovidData() mta3  chart bech yjib le itezmzouet
         *
         *
         * */


        configureLineChart();
        setupApi();
        getCovidData();


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.toolbarlayout, null);

        ImageView actionbar_img = (ImageView)v.findViewById(R.id.title_img);
        actionbar_img.setVisibility(View.VISIBLE);
        TextView actionbar_title = (TextView)v.findViewById(R.id.action_bar_image);


        // Glide
        Glide.with(getApplicationContext())
                .load("https://www.countryflags.io/"+covidCountry.getmCode()+"/flat/64.png")
                .apply(new RequestOptions().override(240, 160))
                .into(actionbar_img);

        actionbar_title.setText(mCountry);

        actionBar.setCustomView(v);

        // call view
        tvDetailCountryName = findViewById(R.id.tvDetailCountryName);
        tvDetailTotalCases = findViewById(R.id.textView19);
        tvDetailTodayCases = findViewById(R.id.tvDetailTodayCases);
        tvDetailTotalDeaths = findViewById(R.id.tvDetailTotalDeaths);
        tvDetailTodayDeaths = findViewById(R.id.tvDetailTodayDeaths);
        tvDetailTotalRecovered = findViewById(R.id.tvDetailTotalRecovered);
        tvDetailTotalActive = findViewById(R.id.tvDetailTotalActive);
        tvDetailTotalCritical = findViewById(R.id.tvDetailTotalCritical);
        tvDetailRecoveryRate = findViewById(R.id.textView23);
        tvDetailDeathRate = findViewById(R.id.textView21);
        tvDetailPerMillion = findViewById(R.id.textView25);
        smallDetailTotalCases = findViewById(R.id.cases_new);
        smallDetailTotalnewCases = findViewById(R.id.cases_lastest);


        smallDetailTotalRecovered = findViewById(R.id.recovered);
        smallDetailTotalnewRecovered = findViewById(R.id.recovered_latest);

        smallDetailDeathCases  = findViewById(R.id.deaths);
        smallDetailTotalDeathCases = findViewById(R.id.deaths_latest);




        // set text view
        tvDetailCountryName.setText(covidCountry.getmCovidCountry());
        tvDetailTotalCases.setText(refactorNumber(String.valueOf(covidCountry.getmCases())));
        tvDetailTodayCases.setText(covidCountry.getmTodayCases());
        tvDetailTotalDeaths.setText(covidCountry.getmDeaths());
        tvDetailTodayDeaths.setText(covidCountry.getmTodayDeaths());
        tvDetailTotalRecovered.setText(covidCountry.getmRecovered());
        tvDetailTotalActive.setText(covidCountry.getmActive());
        tvDetailTotalCritical.setText(covidCountry.getmCritical());
        tvDetailRecoveryRate.setText(String. format("%.2f", Double.valueOf(covidCountry.getRecoveryRate()))+"%");
        tvDetailDeathRate.setText(String. format("%.2f", Double.valueOf(covidCountry.getDeathRate()))+"%");
        if(!covidCountry.getCasesPerMillionPopulation().equals("0")){
            tvDetailPerMillion.setText("( "+covidCountry.getCasesPerMillionPopulation()+" Per Million )");
        }



        smallDetailTotalCases.setText(refactorNumber(String.valueOf(covidCountry.getmCases())));
        if(!covidCountry.getmTodayCases().equals("0")){
            smallDetailTotalnewCases.setText("+"+covidCountry.getmTodayCases());
        }


        smallDetailTotalRecovered.setText(refactorNumber(String.valueOf(covidCountry.getmRecovered())));


        smallDetailDeathCases.setText(refactorNumber(String.valueOf(covidCountry.getmDeaths())));
        if(!covidCountry.getmTodayCases().equals("0")){
            smallDetailTotalDeathCases.setText("+"+covidCountry.getmTodayDeaths());
        }

    }





    /**
     *
     * Maj3oula mbech tconverti el number min  2787896 lel 2 787 896
     *
     **/
    private String refactorNumber(String value){

        DecimalFormatSymbols customSymbols = DecimalFormatSymbols.getInstance(Locale.US);
        //   customSymbols.setGroupingSeparator(' ');
        return new DecimalFormat("#,###", customSymbols).format(Integer.parseInt(value));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    private void configureLineChart() {
        Description desc = new Description();
        desc.setText("Covid in "+mCountry);
        lineChart.setDescription(desc);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM", Locale.ENGLISH);

            @Override
            public String getFormattedValue(float value) {
                long millis = (long) value * 1000L;
                return mFormat.format(new Date(millis));
            }
        });
    }

    private void setupApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        covidAPI = new retrofit2.Retrofit.Builder()
                .baseUrl("http://corona-api.com/countries/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CovidAPI.class);
    }

    private void getCovidData() {

        covidAPI.getCovidDataByCountry(mCode).enqueue(new Callback<CovidDataResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<CovidDataResponse> call, Response<CovidDataResponse> response) {
                ArrayList<Entry> pricesHigh = new ArrayList<>();
                ArrayList<Entry> pricesLow = new ArrayList<>();
                ArrayList<Entry> pricesClose = new ArrayList<>();

                if (response.body() != null) {
                    for (int i = 0; i < response.body().getData().getTimeline().size(); i++) {
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        Date date = null;
                        try {
                            date = format.parse(response.body().getData().getTimeline().get(i).getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        float x = date.getTime()/1000;
                        float y = Float.parseFloat(response.body().getData().getTimeline().get(i).getConfirmed());
                        if (y != 0f) {
                            pricesHigh.add(new Entry(x, Float.parseFloat(response.body().getData().getTimeline().get(i).getConfirmed())));
                            pricesLow.add(new Entry(x, Float.parseFloat(response.body().getData().getTimeline().get(i).getDeaths())));
                            pricesClose.add(new Entry(x, Float.parseFloat(response.body().getData().getTimeline().get(i).getRecovered())));
                        }
                    }
                    Comparator<Entry> comparator = new Comparator<Entry>() {
                        @Override
                        public int compare(Entry o1, Entry o2) {
                            return Float.compare(o1.getX(), o2.getX());
                        }
                    };

                    pricesHigh.sort(comparator);
                    pricesLow.sort(comparator);
                    pricesClose.sort(comparator);

                    setLineChartData(pricesHigh, pricesLow, pricesClose);
                }
            }

            @Override
            public void onFailure(Call<CovidDataResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
            }
        });
    }

    private void setLineChartData(ArrayList<Entry> pricesHigh, ArrayList<Entry> pricesLow, ArrayList<Entry> pricesClose) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();


            LineDataSet highLineDataSet = new LineDataSet(pricesHigh,  " Confirmed ");


            highLineDataSet.setLineWidth(5f);
            highLineDataSet.setColor(getResources().getColor(R.color.confirmed));
            highLineDataSet.setCircleHoleColor(Color.GREEN);
            highLineDataSet.setCircleColor(R.color.colorAccent);
            highLineDataSet.setHighLightColor(Color.RED);
            highLineDataSet.setDrawValues(false);
            highLineDataSet.setCircleRadius(10f);
            highLineDataSet.setCircleColor(Color.YELLOW);
            highLineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            highLineDataSet.setCubicIntensity(0.2f);
            highLineDataSet.setDrawCircles(false);
            ArrayList<ILineDataSet> iLineDataSetArrayList = new ArrayList<>();
            iLineDataSetArrayList.add(highLineDataSet);
            LineData lineData = new LineData(iLineDataSetArrayList);
            lineData.setValueTextSize(13f);
            lineData.setValueTextColor(Color.BLACK);
            dataSets.add(highLineDataSet);
            LineDataSet lowLineDataSet = new LineDataSet(pricesLow,  " Dead ");
            lowLineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lowLineDataSet.setLineWidth(5f);
            lowLineDataSet.setColor(getResources().getColor(R.color.dead));
            lowLineDataSet.setCircleHoleColor(Color.GREEN);
            lowLineDataSet.setCircleColor(R.color.colorAccent);
            lowLineDataSet.setHighLightColor(Color.RED);
            lowLineDataSet.setDrawValues(false);
            lowLineDataSet.setCircleRadius(10f);
            lowLineDataSet.setCircleColor(Color.YELLOW);
            lowLineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lowLineDataSet.setCubicIntensity(0.2f);
            lowLineDataSet.setDrawCircles(false);
            lineData.setValueTextSize(13f);
            lineData.setValueTextColor(Color.BLACK);
            dataSets.add(lowLineDataSet);
            LineDataSet closeLineDataSet = new LineDataSet(pricesClose,  " Recovered");
            closeLineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            closeLineDataSet.setLineWidth(5f);
            closeLineDataSet.setColor(getResources().getColor(R.color.recovered));
            closeLineDataSet.setCircleHoleColor(Color.GREEN);
            closeLineDataSet.setCircleColor(R.color.colorAccent);
            closeLineDataSet.setHighLightColor(Color.RED);
            closeLineDataSet.setDrawValues(false);
            closeLineDataSet.setCircleRadius(10f);
            closeLineDataSet.setCircleColor(Color.YELLOW);
            closeLineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            closeLineDataSet.setCubicIntensity(0.2f);
            closeLineDataSet.setDrawCircles(false);
            lineData.setValueTextSize(13f);
            lineData.setValueTextColor(Color.BLACK);
            dataSets.add(closeLineDataSet);
            lineData = new LineData(dataSets);
            lineChart.setData(lineData);
            lineChart.invalidate();
    }
}
