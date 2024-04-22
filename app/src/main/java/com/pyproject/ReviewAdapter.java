package com.pyproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final List<Review> reviewList;
    private final LayoutInflater inflater;

    // Constructor
    public ReviewAdapter(Context context, List<Review> reviewList) {
        this.inflater = LayoutInflater.from(context);
        this.reviewList = reviewList;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review currentReview = reviewList.get(position);
        holder.userTextView.setText(currentReview.getUser());
        holder.commentTextView.setText(currentReview.getComment());
        holder.ratingTextView.setText(String.valueOf(currentReview.getRating()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    // Provide a reference to the views for each data item
    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        final TextView userTextView;
        final TextView commentTextView;
        final TextView ratingTextView;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            userTextView = itemView.findViewById(R.id.tvUserName);
            commentTextView = itemView.findViewById(R.id.tvComment);
            ratingTextView = itemView.findViewById(R.id.tvRating);
        }
    }
}
