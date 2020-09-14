package com.example.covid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid.R;
import com.example.covid.model.Prevention;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    //create a list to pass our Model class
    List<Prevention> modelList;
    Context context;

    public CustomAdapter(List<Prevention> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate our custom view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prevention_item_list,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //bind all custom views by its position
        //to get the positions we call our Model class
        final Prevention model = modelList.get(position);
        holder.name.setText(model.getName());
        holder.imageView.setImageDrawable(context.getResources().getDrawable(model.getImage()));
    }
    @Override
    public int getItemCount() {
        return modelList.size();
    }
    //all the custom view will be hold here or initialize here inside MyViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_prevention);
            name = itemView.findViewById(R.id.preventionTv);
        }
    }
}