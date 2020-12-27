package com.hanedi.covid19.ui.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hanedi.covid19.R;
import com.hanedi.covid19.ui.Model.CovidCountry;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CovidCountryAdapter extends RecyclerView.Adapter<CovidCountryAdapter.ViewHolder> implements Filterable {

    private List<CovidCountry> covidCountries;
    private List<CovidCountry> covidCountriesFull;
    private static String[] suffix = new String[]{"","K", "M", "B", "T"};
    private static int MAX_LENGTH = 4;

    private Context context;

    public CovidCountryAdapter(List<CovidCountry> covidCountries, Context context) {
        this.covidCountries = covidCountries;
        this.context = context;
        covidCountriesFull = new ArrayList<>(covidCountries);
    }

    @NonNull
    @Override
    public CovidCountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_covid_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CovidCountryAdapter.ViewHolder holder, int position) {
        CovidCountry covidCountry = covidCountries.get(position);
if(covidCountry.getmTodayCases().equals("0")){
    holder.tvNewCases.setVisibility(View.GONE);
}else{
    holder.tvNewCases.setText("+"+ refactorNumber(covidCountry.getmTodayCases()));
}


        holder.tvTotalCases.setText(format(covidCountry.getmCases()));
        holder.tvCountryName.setText(covidCountry.getmCovidCountry());
        // Glide
        Glide.with(context)
                .load("https://www.countryflags.io/"+covidCountry.getmCode()+"/flat/64.png")
                .apply(new RequestOptions().override(240, 160))
                .into(holder.imgCountryFlag);

    }

    @Override
    public int getItemCount() {
        return covidCountries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTotalCases, tvCountryName,tvNewCases;
        ImageView imgCountryFlag;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNewCases = itemView.findViewById(R.id.tvNewCases);
            tvTotalCases = itemView.findViewById(R.id.tvTotalCases);
            tvCountryName = itemView.findViewById(R.id.tvCountryName);
            imgCountryFlag = itemView.findViewById(R.id.imgCountryFlag);

        }
    }

    /**
     * Tconverti min 2000000 lel 2M
     *
     * */

    private static String format(int number) {
        String r = new DecimalFormat("##0E0").format(number);
        r = r.replaceAll("E[0-9]", suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]);
        while(r.length() > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]")){
            r = r.substring(0, r.length()-2) + r.substring(r.length() - 1);
        }
        return r;
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
    public Filter getFilter() {
        return covidCountriesFilter;
    }

    private Filter covidCountriesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CovidCountry> filteredCovidCountry = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredCovidCountry.addAll(covidCountriesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CovidCountry itemCovidCountry : covidCountriesFull) {
                    if (itemCovidCountry.getmCovidCountry().toLowerCase().contains(filterPattern)) {
                        filteredCovidCountry.add(itemCovidCountry);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredCovidCountry;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            covidCountries.clear();
            covidCountries.addAll((List) results.values);
            notifyDataSetChanged();
        }


    };


}
