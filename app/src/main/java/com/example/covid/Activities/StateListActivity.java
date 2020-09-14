package com.example.covid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid.MainActivity;
import com.example.covid.R;
import com.example.covid.adapter.StateListAdapter;
import com.example.covid.model.StateModels;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StateListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private StateListAdapter stateListAdapter;
    private MaterialSearchView materialSearchView;
    private List<StateModels> arrayList;
    private ProgressDialog progressDialog;
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar_state);
        setSupportActionBar(toolbar);


        searchView = findViewById(R.id.search_view);


        getSupportActionBar().setTitle("Select a State");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StateListActivity.this, MainActivity.class));
                finish();
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        arrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.state_wise_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getStateData();
    }

    private void getStateData() {
        String url = "https://api.covid19india.org/data.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("statewise");


                            for (int i = 1; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                StateModels stateModels = new StateModels(
                                        jsonObject.getString("state"),
                                        jsonObject.getString("confirmed")
                                );

                                arrayList.add(stateModels);

                            }


                            stateListAdapter = new StateListAdapter(getApplicationContext(), arrayList);
                            stateListAdapter.setHasStableIds(true);
                            recyclerView.setAdapter(stateListAdapter);
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(StateListActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StateListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                    }
                }
        );

        int socketTime = 7000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        jsonObjectRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue = Volley.newRequestQueue(StateListActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.state_toolbar, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        searchView.setMenuItem(menuItem);
        searchView.setHint("Search Corona States By States..");

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                stateListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                stateListAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            case R.id.about_corona:
                startActivity(new Intent(getApplicationContext(), AboutCorona.class));
                return true;
            case R.id.about_app:
                startActivity(new Intent(getApplicationContext(), AboutApp.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if(searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }

    }
}