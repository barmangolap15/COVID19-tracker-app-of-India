package com.example.covid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid.Activities.DistWiseDetailsActivity;
import com.example.covid.R;
import com.example.covid.model.DistModel;

import java.util.ArrayList;
import java.util.List;

public class DistrictListAdapter extends RecyclerView.Adapter<DistrictListAdapter.MyViewHolder> implements Filterable {

    private List<DistModel> arrayList;
    private List<DistModel> fullArrayList;
    Context context;
    public DistrictListAdapter(Context context, List<DistModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.fullArrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dist_list_item, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final String state_name = fullArrayList.get(position).getState_name();
        final String confirm_cases = fullArrayList.get(position).getConfirm_cases();
        final String dist_name = fullArrayList.get(position).getDist_name();
        holder.counterTv.setText(String.valueOf(position + 1));
        holder.dist_nameTv.setText(dist_name);
        holder.confirm_casesTv.setText(confirm_cases);


        holder.dist_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DistWiseDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("state_name", state_name);
                intent.putExtra("dist_name", dist_name);
                intent.putExtra("confirm_cases", confirm_cases);

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return fullArrayList.size();
    }



    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if(charString.isEmpty()) {
                    fullArrayList = arrayList;
                } else {
                    List<DistModel> filteredList = new ArrayList<>();
                    for(DistModel row: arrayList) {
                        if(row.getDist_name().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    fullArrayList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = fullArrayList;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                fullArrayList = (ArrayList<DistModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dist_nameTv, confirm_casesTv, counterTv;
        CardView dist_cardView;
        public MyViewHolder(View view) {
            super(view);

            this.dist_nameTv = (TextView) view.findViewById(R.id.dist_nameTv);
            this.confirm_casesTv = (TextView) view.findViewById(R.id.dist_confirm_cases);
            this.counterTv = (TextView) view.findViewById(R.id.counter);
            this.dist_cardView = (CardView) view.findViewById(R.id.dist_card_view);
        }
    }

}