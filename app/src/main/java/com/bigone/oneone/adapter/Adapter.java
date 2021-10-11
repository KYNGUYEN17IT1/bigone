package com.bigone.oneone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bigone.oneone.model.ModelClass;
import com.bigone.oneone.R;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    int m=1;
    Context context;
    List<ModelClass> countrylist;

    public Adapter(Context context, List<ModelClass> countrylist) {
        this.context = context;
        this.countrylist = countrylist;
    }

    @NonNull
    @NotNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View views = LayoutInflater.from(context).inflate(R.layout.layout_item, null, false);
        return new ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter.ViewHolder holder, int position) {
        ModelClass modelClass=countrylist.get(position);
        if(m==1) {
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(modelClass.getCases())));

        } else if(m==2) {
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(modelClass.getRecovered())));

        } else if(m==3) {
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(modelClass.getDeaths())));

        } else {
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(modelClass.getActive())));

        }
        holder.country.setText(modelClass.getCountry());
    }

    @Override
    public int getItemCount() {
        return countrylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cases, country;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cases=itemView.findViewById(R.id.countrycase);
            country=itemView.findViewById(R.id.countryname);
        }
    }

    public void filter(String charText)
    {
        if(charText.equals("Số ca nhiễm"))
        {
            m=1;
        } else  if(charText.equals("Số ca khỏi bệnh"))
        {
            m=2;
        } else if(charText.equals("Số ca tử vong"))
        {
            m=3;
        } else
        {
            m=4;
        }
        notifyDataSetChanged();
    }
}
