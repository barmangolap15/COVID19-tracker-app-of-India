package com.example.covid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.covid.MainActivity;
import com.example.covid.R;
import com.example.covid.adapter.CustomAdapter;
import com.example.covid.model.Prevention;

import java.util.ArrayList;
import java.util.List;

public class AboutCorona extends AppCompatActivity {

    RecyclerView preventionRecyclerView;
    List<Prevention> model;
    RecyclerView.Adapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_corona);

        toolbar = (Toolbar) findViewById(R.id.toolbar_state);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutCorona.this, MainActivity.class));
                finish();
            }
        });

        model = new ArrayList<>();
        preventionRecyclerView = findViewById(R.id.prevention_recyclerview);
        preventionRecyclerView.setHasFixedSize(true);
        preventionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        model.add(new Prevention(R.drawable.ic_hand_wash,"Wash your hands regularly with soap and water, or clean them with alcohol-based hand rub."));
        model.add(new Prevention(R.drawable.ic_distance,"Maintain at least 1 meter distance between you and people coughing or sneezing"));
        model.add(new Prevention(R.drawable.ic_face_touch,"Avoid Touching your face"));
        model.add(new Prevention(R.drawable.ic_sneezoing,"Cover your mouth and nose when coughing and sneezing"));
        model.add(new Prevention(R.drawable.ic_stay_home,"Stay home if you feel unwell"));
        model.add(new Prevention(R.drawable.ic_smoke_cigarette,"Refrain from smoking and other activities that weaken the Lungs"));
        model.add(new Prevention(R.drawable.ic_physical_distance,"Practice physical distancing by avoiding unnecessary travel and staying away from large group of people."));

        adapter = new CustomAdapter(model,getApplicationContext());

        preventionRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}