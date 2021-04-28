package com.example.valutes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterValute extends RecyclerView.Adapter<AdapterValute.ViewHolder> {
    ArrayList<Country> countries=new ArrayList<>();
    private Context context;
    private  final LayoutInflater inflater;

    public AdapterValute(Context context,ArrayList<Country> countries) {
        super();
        this.context=context;
        this.countries=countries;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public AdapterValute.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.onevalute, parent, false) ;
        return new AdapterValute.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( AdapterValute.ViewHolder holder, int position) {
        Country country=countries.get(position);
        holder.Value.setText(country.value);
        holder.Name.setText(country.name);
        holder.ImageView.setImageBitmap(country.pick);

    }


    @Override
    public int getItemCount() {
        return countries.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView Value,Name;
        final ImageView ImageView;

        ViewHolder(View view){
            super(view);
            Value = (TextView) view.findViewById(R.id.value);
            Name = (TextView) view.findViewById(R.id.name);
            ImageView = (ImageView) view.findViewById(R.id.pick);

        }
    }
}
