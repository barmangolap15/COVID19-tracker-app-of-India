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

import com.example.covid.Activities.StateWiseDetailsActivity;
import com.example.covid.R;
import com.example.covid.model.StateModels;

import java.util.ArrayList;
import java.util.List;

public class StateListAdapter extends RecyclerView.Adapter<StateListAdapter.MyViewHolder> implements Filterable {

    private List<StateModels> arrayList;
    Context context;
    private List<StateModels> fullArraylist;
    int counter = 1;

    public StateListAdapter(Context context, List<StateModels> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.fullArraylist = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.state_list_item, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.counterTv.setText(String.valueOf(counter ++));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final String state_name = fullArraylist.get(position).getState_name();
        final String confirm_cases = fullArraylist.get(position).getConfirm_cases();

        holder.state_nameTv.setText(state_name);
        holder.confirm_casesTv.setText(confirm_cases);


        holder.state_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StateWiseDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("state_name", state_name);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return fullArraylist.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if(charString.isEmpty()) {
                    fullArraylist = arrayList;
                } else {
                    List<StateModels> filteredList = new ArrayList<>();
                    for(StateModels row: arrayList) {
                        if(row.getState_name().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    fullArraylist = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = fullArraylist;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                fullArraylist = (ArrayList<StateModels>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView state_nameTv, confirm_casesTv, counterTv;
        CardView state_cardView;
        public MyViewHolder(View view) {
            super(view);

            this.state_nameTv = (TextView) view.findViewById(R.id.state_nameTv);
            this.confirm_casesTv = (TextView) view.findViewById(R.id.confirm_cases);
            this.counterTv = (TextView) view.findViewById(R.id.counter);
            this.state_cardView = (CardView) view.findViewById(R.id.state_card_view);
        }
    }

}