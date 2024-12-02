package com.example.startingpoint;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FactAdapter extends RecyclerView.Adapter<FactAdapter.FactViewHolder> {
    private List<Fact> factList;
    private Context context;

    public FactAdapter(List<Fact> factList, Context context) {
        this.factList = factList;
        this.context = context;
    }

    @NonNull
    @Override
    public FactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fact_item, parent, false);
        return new FactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FactViewHolder holder, int position) {
        Fact fact = factList.get(position);
        holder.factTitle.setText(fact.getTitle());
        holder.factSummary.setText(fact.getSummary());

        if (fact.getImageUrl() != null) {
            Glide.with(context)
                    .load(fact.getImageUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.factImage);
        } else {
            holder.factImage.setImageResource(R.drawable.placeholder_image);
        }

        holder.wikiButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(fact.getUrl()));
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return factList.size();
    }

    public static class FactViewHolder extends RecyclerView.ViewHolder {
        TextView factTitle, factSummary;
        ImageView factImage;
        Button wikiButton;

        public FactViewHolder(View itemView) {
            super(itemView);
            factTitle = itemView.findViewById(R.id.factTitle);
            factSummary = itemView.findViewById(R.id.factSummary);
            factImage = itemView.findViewById(R.id.factImage);
            wikiButton = itemView.findViewById(R.id.wikiButton);
        }
    }
}
