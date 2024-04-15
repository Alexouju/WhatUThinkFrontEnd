package com.pyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView forgotPasswordTextView;
    EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize views
        usernameEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        
        forgotPasswordTextView.setPaintFlags(forgotPasswordTextView.getPaintFlags() | android.graphics.Paint.UNDERLINE_TEXT_FLAG);

        TouchColorChangeListener touchListener = new TouchColorChangeListener(forgotPasswordTextView);
        forgotPasswordTextView.setOnTouchListener(touchListener);


        forgotPasswordTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.forgotPasswordTextView) {

        }

    }

    public void loginPressed(View view) {
        final String usernameOrEmail = usernameEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.106/login";

        // Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Check if login was successful
                        if (response.equals("Login successful")) {
                            // Navigate to LoginActivity
                            //Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            //startActivity(intent);
                            //finish(); // Finish MainActivity
                        } else {
                            // Display error message
                            Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Log.e("VOLLEY_ERROR", error.toString());
                Toast.makeText(MainActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Pass parameters to the backend
                Map<String, String> params = new HashMap<>();
                params.put("username_or_email", usernameOrEmail);
                params.put("password", password);
                return params;
            }
        };

        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }

    public void registerPressed(View view) {
        // go to RegisterActivity
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}