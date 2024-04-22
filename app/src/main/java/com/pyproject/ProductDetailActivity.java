package com.pyproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {

    private RecyclerView rvReviews;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Initialize views...
        TextView tvProductName = findViewById(R.id.tvProductName);
        TextView tvAiDescription = findViewById(R.id.tvAiDescription);
        TextView tvProductDescription = findViewById(R.id.tvProductDescription);
        TextView tvProductSpecifications = findViewById(R.id.tvProductSpecifications);
        rvReviews = findViewById(R.id.rvReviews);

        if (getIntent().hasExtra("PRODUCT_JSON")) {
            String productJson = getIntent().getStringExtra("PRODUCT_JSON");
            Gson gson = new Gson();

            Product product = gson.fromJson(productJson, Product.class);

            tvProductName.setText(product.getName());
            tvAiDescription.setText(product.getAiDescription() != null ? product.getAiDescription() : "No AI Description");
            tvProductDescription.setText(product.getDescription());

            String specificationsString = getSpecificationsString((Map<String, String>) product.getSpecifications());
            tvProductSpecifications.setText(specificationsString);


            Log.d("ProductDetailActivity", "Product name: " + product.getName());

            // Initialize and set up the ReviewAdapter for RecyclerView
            reviewAdapter = new ReviewAdapter(this, product.getReviews());
            rvReviews.setLayoutManager(new LinearLayoutManager(this));
            rvReviews.setAdapter(reviewAdapter);
        }
    }
    private String getSpecificationsString(Map<String, String> specsMap) {
        if (specsMap == null || specsMap.isEmpty()) {
            return "No specifications provided";
        }

        StringBuilder specsStringBuilder = new StringBuilder();

        for (Map.Entry<String, String> entry : specsMap.entrySet()) {
            specsStringBuilder.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\n"); // Append a newline after each spec item
        }

        return specsStringBuilder.toString().trim(); // Trim to remove the last newline
    }
}