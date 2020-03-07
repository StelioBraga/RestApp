package com.example.rest.utils;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rest.R;
import com.example.rest.models.Pais;

import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.List;

public class PaisAdapter extends RecyclerView.Adapter<PaisAdapter.PaisViewHolder> {
    private List<Pais> paisList;
    private Context mContext;
    String formatString;

    public PaisAdapter( List<Pais> paisList, Context mContext){
        this.paisList = paisList;
        this.mContext = mContext;
    }



    @Override
    public PaisAdapter.PaisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        final View view = inflater.inflate(R.layout.countries_post, viewGroup, false);
        return new PaisAdapter.PaisViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PaisViewHolder holder, int position) {
        Pais pais = paisList.get(position);
        holder.tv_name.setText(pais.getName());
        holder.tv_capital.setText(pais.getCapital());
        holder.tv_nativeName.setText(pais.getNativeName());
        holder.tv_population.setText(String.valueOf(pais.getPopulation()));
        holder.tv_area.setText(String.valueOf(pais.getArea()));
        holder.tv_flag.setText(String.valueOf(pais.getFlag()));
        holder.tv_sub_region.setText(pais.getSub_region());
        holder.tv_region.setText(pais.getRegion());

        for (int i = 0; i < pais.getTimezones().length(); i++) {
            try {
                String time = String.valueOf(pais.getTimezones().get(i)) ;
                formatString += String.format(",%s", String.format(time));
                formatString = formatString.replace("null,","");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        holder.tv_timezones.setText(formatString);
    }

    @Override
    public int getItemCount() {
        return paisList.size();
    }

    public class PaisViewHolder extends RecyclerView.ViewHolder {
        public PaisAdapter myAdapter;
        TextView tv_name,tv_capital,tv_region, tv_sub_region,tv_population, tv_area,tv_timezones,tv_nativeName, tv_flag;
        public PaisViewHolder(@NonNull View itemView, final PaisAdapter myAdapter) {
            super(itemView);
            this.myAdapter = myAdapter;
            tv_name        = itemView.findViewById(R.id.tv_name);
            tv_capital     = itemView.findViewById(R.id.tv_capital);
            tv_region      = itemView.findViewById(R.id.tv_region);
            tv_sub_region  = itemView.findViewById(R.id.tv_sub_region);
            tv_area        = itemView.findViewById(R.id.tv_area);
            tv_population  = itemView.findViewById(R.id.tv_population);
            tv_timezones   = itemView.findViewById(R.id.tv_timezone);
            tv_nativeName  = itemView.findViewById(R.id.tv_nativeName);
            tv_flag        = itemView.findViewById(R.id.tv_flag);
        }
    }
}
