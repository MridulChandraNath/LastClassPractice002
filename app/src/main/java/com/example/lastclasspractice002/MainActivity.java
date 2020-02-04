package com.example.lastclasspractice002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText phoneET,passET;
    Button parseBtn;
    String url="http://demo.olivineltd.com/primary_attendance/api/admin/login";
    String phone,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneET=findViewById(R.id.phoneET);
        passET=findViewById(R.id.passET);
        phone=phoneET.getText().toString();
        pass=passET.getText().toString();

        parseBtn=findViewById(R.id.parseBtn);

        //Making Shared preferencess here
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPreferencess", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();


        


        parseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(MainActivity.this, "MOira ja", Toast.LENGTH_SHORT).show();
                                if (response=="Invalid Credentials") {
                                    Toast.makeText(MainActivity.this, "Invalid", Toast.LENGTH_SHORT).show();
                                }else{

                                    startActivity(new Intent(MainActivity.this,SecondActivity.class));
                                }

                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    String name=jsonObject.getString("users_name_en");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },new Response.ErrorListener(){


                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }


                })


                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>prams=new HashMap<>();
                        prams.put("mobile_no",phone);
                        prams.put("password",pass);
                        return super.getParams();
                    }
                };
                queue.add(stringRequest);
            }

        });


    }


}
