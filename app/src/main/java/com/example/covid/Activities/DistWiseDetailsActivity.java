package com.example.covid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DistWiseDetailsActivity extends AppCompatActivity {

    private TextView daily_confirm_casesTv, total_confirm_casesTv, total_active_casesTv,
            daily_recover_casesTv, total_recover_casesTv, daily_death_casesTv,
            total_death_casesTv;

    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private Intent intent;
    private String state_name, dist_name, confirm_cases;
    private TextView zoneTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dist_wise_details);

        intent = getIntent();
        state_name = intent.getStringExtra("state_name");
        dist_name = intent.getStringExtra("dist_name");
        confirm_cases = intent.getStringExtra("confirm_cases");


        daily_confirm_casesTv = (TextView) findViewById(R.id.dist_daily_confirm_cases);
        total_confirm_casesTv = (TextView) findViewById(R.id.dist_total_confirm_cases);

        total_active_casesTv = (TextView) findViewById(R.id.dist_total_active_cases);

        daily_recover_casesTv = (TextView) findViewById(R.id.dist_daily_recover_cases);
        total_recover_casesTv = (TextView) findViewById(R.id.dist_total_recover_cases);

        daily_death_casesTv = (TextView) findViewById(R.id.dist_daily_death_cases);
        total_death_casesTv = (TextView) findViewById(R.id.dist_total_death_cases);

        zoneTv = findViewById(R.id.zone);

        toolbar = (Toolbar) findViewById(R.id.dist_details_toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(dist_name + ": " + confirm_cases);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DistWiseDetailsActivity.this, StateWiseDetailsActivity.class);
                intent.putExtra("state_name", state_name);
                startActivity(intent);
                finish();
            }
        });



        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);


        getDistDetails();
    }

    private void getDistDetails() {
        String url = "https://api.covid19india.org/state_district_wise.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject state_dataObject = response.getJSONObject(state_name);
                    JSONObject distObject = state_dataObject.getJSONObject("districtData");
                    JSONObject particular_distData = distObject.getJSONObject(dist_name);
                    JSONObject daily_datObject = particular_distData.getJSONObject("delta");


                    String total_confirm = particular_distData.getString("confirmed");
                    String daily_confirm = daily_datObject.getString("confirmed");

                    String total_active_cases = particular_distData.getString("active");

                    String total_recover = particular_distData.getString("recovered");
                    String daily_recover= daily_datObject.getString("recovered");

                    String total_death = particular_distData.getString("deceased");
                    String daily_death = daily_datObject.getString("deceased");


                    total_confirm_casesTv.setText(total_confirm);
                    daily_confirm_casesTv.setText("[+" + daily_confirm+ "]");

                    total_active_casesTv.setText(total_active_cases);

                    total_recover_casesTv.setText(total_recover);
                    daily_recover_casesTv.setText("[+" +daily_recover+ "]");

                    total_death_casesTv.setText(total_death);
                    daily_death_casesTv.setText("[+" +daily_death+ "]");

                    getZone();
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(DistWiseDetailsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DistWiseDetailsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

        int socketTime = 7000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(retryPolicy);

        // request

        RequestQueue requestQueue = Volley.newRequestQueue(DistWiseDetailsActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    private void getZone() {
        String url = "https://api.covid19india.org/zones.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("zones");

                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if(jsonObject.getString("district").equals(dist_name)) {
                            String data = jsonObject.getString("zone");
                            if(data.equals("Red")) {
                                zoneTv.setTextColor(getResources().getColor(R.color.red));
                                zoneTv.setText(dist_name + " is " +data + " zone");
                            } else if(data.equals("Orange")) {
                                zoneTv.setTextColor(getResources().getColor(R.color.orange));
                                zoneTv.setText(dist_name + " is " +data + " zone");
                            } else if(data.equals("Green")) {
                                zoneTv.setTextColor(getResources().getColor(R.color.green));
                                zoneTv.setText(dist_name + " is " +data + " zone");
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DistWiseDetailsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }
}