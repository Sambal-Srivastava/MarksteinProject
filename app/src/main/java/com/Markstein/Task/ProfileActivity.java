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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
   String UserId;
   String UserCategoryId;
    String AttendanceSessionId;
    TextView assignment_tv, tv2, tv4, tv6, tv8, tv10, tv12, tv14, tv16, tv18, tv20, tv22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        assignment_tv = findViewById(R.id.asgn_tv);
        tv2 = findViewById(R.id.tv2);
        tv4 = findViewById(R.id.tv4);
        tv6 = findViewById(R.id.tv6);
        tv8 = findViewById(R.id.tv8);
        tv10 = findViewById(R.id.tv10);
        tv12 = findViewById(R.id.tv12);
        tv14 = findViewById(R.id.tv14);

        tv16 = findViewById(R.id.tv16);
        tv18 = findViewById(R.id.tv18);
        tv20 = findViewById(R.id.tv20);
        tv22 = findViewById(R.id.tv22);


        assignment_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, Assignment_activity.class));
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();



        SharedPreferences sharedPreferences  = getSharedPreferences("UserId_Pref", Context.MODE_PRIVATE);
        final String UserId = sharedPreferences.getString("UserId", null);
        final String UserCategoryId = sharedPreferences.getString("UserCategoryId", null);
        final String AttendanceSessionId = sharedPreferences.getString("AttendanceSessionId", null);


            StringRequest request = new StringRequest(Request.Method.POST, "http://demoschool.eazyskool.com/api/user/UserDetailsByUser?",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject jsonObject1 = jsonObject.getJSONObject("Data");
                                JSONObject jsonObject2 = jsonObject1.getJSONObject("employeeDetails");
                                tv2.setText(jsonObject2.getString("EmployeeId"));
                                tv4.setText(jsonObject2.getString("UserId"));
                                tv6.setText(jsonObject2.getString("SchoolName"));
                                tv8.setText(jsonObject2.getString("AttendanceSession"));
                                tv10.setText(jsonObject2.getString("EmployeeName"));
                                tv12.setText(jsonObject2.getString("Gender"));
                                tv14.setText(jsonObject2.getString("DOB"));

                                tv16.setText(jsonObject2.getString("MaritalStatus"));
                                tv18.setText(jsonObject2.getString("PlaceOfBirth"));
                                tv20.setText(jsonObject2.getString("NationalityName"));
                                tv22.setText(jsonObject2.getString("MobileNumber"));


                                Log.d("ProfileActivityResponse", response);
                            } catch (Exception e) {

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

            RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
            requestQueue.add(request);
        }
    }




