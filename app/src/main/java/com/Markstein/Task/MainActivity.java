package com.Markstein.Task;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btn;
    EditText userName, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.login_btn);
        userName = findViewById(R.id.user_name);
        password = findViewById(R.id.user_password);

        //====================session check==========================
        SharedPreferences sharedPreferences  = getSharedPreferences("UserId_Pref", Context.MODE_PRIVATE);
        String UserExist = sharedPreferences.getString("UserId", null);

        if (UserExist != null) {
        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        finish();
        }


        //=============================================================

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String UserName = String.valueOf(userName.getText());
                Log.d("username", UserName);
                final String Password = String.valueOf(password.getText());
                StringRequest request = new StringRequest(Request.Method.POST, "http://demoschool.eazyskool.com/api/user/GetCheckUserLogin?",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String StatusCode = jsonObject.getString("StatusCode");
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("Data");
                                    String UserId = jsonObject1.getString("UserId");
                                    String UserCategoryId = jsonObject1.getString("UserCategoryId");
                                    String AttendanceSessionId = jsonObject1.getString("AttendanceSessionId");



                                    //=========Session Management and param==========================
                                    SharedPreferences sharedPreferences  = getSharedPreferences("UserId_Pref", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("UserId", UserId);
                                    editor.putString("UserCategoryId", UserCategoryId);
                                    editor.putString("AttendanceSessionId", AttendanceSessionId);

                                    editor.commit();
                                    //=========================================================


                                    Log.d("Response", response);

                                    if (StatusCode.equals("0")) {
                                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else{
                                        Toast.makeText(MainActivity.this, "Check Username and Password", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {

                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                     //  Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("UserName", UserName);
                        params.put("UserPassword", Password);


                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(request);
            }

        });


    }
}
