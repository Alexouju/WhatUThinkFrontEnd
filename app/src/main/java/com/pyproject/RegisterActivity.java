package com.pyproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText usernameEditText, emailEditText, passwordEditText;
    Button registerButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        backButton = findViewById(R.id.backButton);

        // Set click listeners
        registerButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registerButton) {
            // Handle register button click
            registerUser();
        } else if (v.getId() == R.id.backButton) {
            // Handle back button click
            getOnBackPressedDispatcher().onBackPressed();
        }
    }

    private void registerUser() {
        final String username = usernameEditText.getText().toString().trim();
        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.102.240/register";  // Verify this is the correct IP and port

        // Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String message = jsonResponse.optString("message", "Unknown response from server");

                            // Display message from server
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // If the response is not JSON, or if there's a JSON exception, output the raw response
                            Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = "Unknown error occurred";
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject jsonObject = new JSONObject(responseBody);
                        errorMessage = jsonObject.optString("error", errorMessage);
                    } catch (UnsupportedEncodingException | JSONException e) {
                        // Log and/or handle the exception appropriately
                        e.printStackTrace();
                    }
                }
                Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                Log.e("VOLLEY_ERROR", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Pass parameters to the backend
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                // Include other parameters if needed, e.g., admin flag
                return params;
            }
        };

        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }
}
