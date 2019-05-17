package com.Markstein.Task;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Assignment_activity extends AppCompatActivity {
    TextView assignment_tv, tv2, tv4, tv6, tv8, tv10, tv12, tv14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_activity);
        tv2 = findViewById(R.id.tv2);
        tv4 = findViewById(R.id.tv4);
        tv6 = findViewById(R.id.tv6);
        tv8 = findViewById(R.id.tv8);
        tv10 = findViewById(R.id.tv10);
        tv12 = findViewById(R.id.tv12);
        tv14 = findViewById(R.id.tv14);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Assignment_activity.this, ProfileActivity.class));
            }
        });
        SharedPreferences sharedPreferences  = getSharedPreferences("UserId_Pref", Context.MODE_PRIVATE);
        final String UserId = sharedPreferences.getString("UserId", null);
        final String UserCategoryId = sharedPreferences.getString("UserCategoryId", null);
        final String AttendanceSessionId = sharedPreferences.getString("AttendanceSessionId", null);




        StringRequest request = new StringRequest(Request.Method.POST, "http://demoschool.eazyskool.com/api/user/AssignmentDetailsByUser?",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Log.d("Assign_response", response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("Data");

                            String AttendanceSession = jsonObject1.getString("AttendanceSession");

                            JSONArray jsonArray=jsonObject1.getJSONArray("HomeworkDetails");

                            JSONObject jsonObject2=jsonArray.getJSONObject(0);

                            String HomeworkDate = jsonObject2.getString("HomeworkDate");
                            String StartDate = jsonObject2.getString("StartDate");
                            String EndDate = jsonObject2.getString("EndDate");
                            String HomeworkHeader = jsonObject2.getString("HomeworkHeader");

//                            String SessionId = jsonObject1.getString("SessionId");
//                            String SessionId = jsonObject1.getString("SessionId");
//                            String SessionId = jsonObject1.getString("SessionId");
//                            String SessionId = jsonObject1.getString("SessionId");
//                            String SessionId = jsonObject1.getString("SessionId");

                            tv2.setText(AttendanceSession);
                            tv4.setText(HomeworkDate);
                            tv6.setText(StartDate);
                            tv8.setText(EndDate);
                            tv10.setText(HomeworkHeader);
                            tv12.setText(jsonObject2.getString("HomeworkDetail"));
                            tv14.setText(jsonObject2.getString("SubjectName"));
//                            tv12.setText(jsonObject2.getString("DOB"));
//                            tv14.setText(jsonObject2.getString("MobileNumber"));


                            Log.d("AssignmentResponse", HomeworkDate);
                        } catch (Exception e) {

                            Log.d("Assignment_error", String.valueOf(e));

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("UserId", UserId);
                params.put("UserCategoryId", UserCategoryId);
                params.put("AttendanceSessionId", AttendanceSessionId);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Assignment_activity.this);
        requestQueue.add(request);
    }




}