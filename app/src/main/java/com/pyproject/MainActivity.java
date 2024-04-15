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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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

        String url = "http://192.168.0.106/login"; // Ensure this is the correct URL

        // Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String message = jsonResponse.optString("message", null);
                            if ("Login successful".equals(message)) {
                                // Navigate to LoginActivity
                                Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
                                startActivity(intent);
                                finish(); // Finish MainActivity
                            } else {
                                // If "message" is not as expected or missing, handle it appropriately
                                String error = jsonResponse.optString("error", "Unknown response from server");
                                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // Log the exception and show a generic error message
                            Log.e("VOLLEY_ERROR", "Could not parse JSON response: " + response);
                            Toast.makeText(MainActivity.this, "Could not process server response", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = "Error occurred";
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject jsonObject = new JSONObject(responseBody);
                        errorMessage = jsonObject.optString("error", errorMessage);
                    } catch (UnsupportedEncodingException | JSONException e) {
                        Log.e("VOLLEY_ERROR", e.toString());
                    }
                }
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
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

    public void continueAsGuestPressed(View view) {
        Intent intent = new Intent(this, ProductsActivity.class);
        startActivity(intent);
        finish();
    }
}