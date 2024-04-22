package com.pyproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final List<Product> productList;
    private final LayoutInflater inflater;



    public interface OnItemClickListener {
        void onItemClick(Product product);
    }
    private final OnItemClickListener listener;


    public ProductAdapter(Context context, List<Product> productList, OnItemClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.productList = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product currentProduct = productList.get(position);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(currentProduct));
        holder.productNameTextView.setText(currentProduct.getName());
        holder.productDescriptionTextView.setText(currentProduct.getDescription());
    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        } else {
            return 0;
        }
    }

    public void setProductList(List<Product> products) {
        productList.clear();
        productList.addAll(products);
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        final TextView productNameTextView;
        final TextView productDescriptionTextView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.product_name_text_view);
            productDescriptionTextView = itemView.findViewById(R.id.product_description_text_view);
        }
    }


}
