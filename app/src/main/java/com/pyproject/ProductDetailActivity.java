package com.pyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.gson.Gson;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        if (getIntent().hasExtra("PRODUCT_JSON")) {
            String productJson = getIntent().getStringExtra("PRODUCT_JSON");
            Gson gson = new Gson();

            Product product = gson.fromJson(productJson, Product.class);
            // Now use 'product' to update your views with product information
        }
    }
}