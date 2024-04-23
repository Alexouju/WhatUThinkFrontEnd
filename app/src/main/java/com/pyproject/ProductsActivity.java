package com.pyproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProductsActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productAdapter = new ProductAdapter(this, productList, product -> {

            Intent intent = new Intent(ProductsActivity.this, ProductDetailActivity.class);

            // Serialize the product object to JSON (as a simple method for passing complex data)
            Gson gson = new Gson();
            String productJson = gson.toJson(product);
            intent.putExtra("PRODUCT_JSON", productJson);

            startActivity(intent);
        });
        recyclerView.setAdapter(productAdapter);

        fetchProducts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_products, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Listener for search query text changes
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Called when the user submits the query
                searchProducts(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Called when the search text changes
                searchProducts(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void fetchProducts() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.172/all-products"; // Replace with your actual URL

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ProductsActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                productAdapter.setProductList(productList);
                            }
                        });
                        // Parse the JSON response into a List of Product objects
                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(Product.class, new ProductDeserializer())
                                .create();

                        productList.clear(); // Clear the existing product list

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject productJson = response.getJSONObject(i);
                                String key = productJson.keys().next();
                                Product product = gson.fromJson(productJson.getJSONObject(key).toString(), Product.class);
                                productList.add(product);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.d("Response", "Number of products fetched: " + productList.size());
                        // Update the adapter with the new product list
                        productAdapter.setProductList(productList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error fetching products: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);
    }

    public void goToAddProductActivity(View view) {
        Intent intent = new Intent(ProductsActivity.this, AddNewProductActivity.class);

        startActivity(intent);
    }

    private static class ProductDeserializer implements JsonDeserializer<Product> {
        @Override
        public Product deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Log.d("Deserializer", "Deserializing: " + json.toString());
            JsonObject productObject = json.getAsJsonObject();
            String aiDescription = productObject.has("ai_description") && !productObject.get("ai_description").isJsonNull() ? productObject.get("ai_description").getAsString() : null;
            String description = productObject.get("description").getAsString();
            String name = productObject.get("name").getAsString();
            JsonElement specsElement = productObject.get("specifications");

            Map<String, String> specifications = new HashMap<>();
            String specificationsStr = "Not specified";
            if (specsElement != null) {
                if (specsElement.isJsonObject()) {
                    Type specsType = new TypeToken<Map<String, String>>(){}.getType();
                    specifications = context.deserialize(specsElement, specsType);
                } else if (specsElement.isJsonPrimitive() && specsElement.getAsJsonPrimitive().isString()) {
                    specificationsStr = specsElement.getAsString();
                    // The specificationsStr can be added to the Map with a generic key or handled separately
                    specifications.put("General", specificationsStr);
                }
            }

            // Assuming `Review` is a properly structured class based on your JSON
            Type reviewListType = new TypeToken<List<Review>>(){}.getType();
            List<Review> reviews = context.deserialize(productObject.get("reviews"), reviewListType);

            return new Product(name, description, aiDescription, specifications, reviews, new ArrayList<String>());
        }
    }

    private void searchProducts(String query) {
        // Filter productList based on query (assumes productList is all available products)
        if (query == null || query.isEmpty()) {
            productAdapter.setProductList(productList);
        } else {
            List<Product> filteredList = new ArrayList<>();
            for (Product product : productList) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(product);
                }
            }
            productAdapter.setProductList(filteredList);
        }
    }
}