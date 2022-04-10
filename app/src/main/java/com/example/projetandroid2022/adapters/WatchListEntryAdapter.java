package com.example.projetandroid2022.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetandroid2022.R;
import com.example.projetandroid2022.entities.Resource;
import com.example.projetandroid2022.entities.WatchListEntry;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WatchListEntryAdapter extends RecyclerView.Adapter<WatchListEntryAdapter.MyViewHolder> {

    Context context;
    List<WatchListEntry> mData;
    WatchListItemListener resourceItemClickListener;
    WatchListItemListener watchListItemListener;

    public WatchListEntryAdapter(Context context, List<WatchListEntry> mData, WatchListItemListener listener) {
        this.context = context;
        this.mData = mData;
        watchListItemListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.watchlist_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchListEntryAdapter.MyViewHolder myViewHolder, int i) {
        WatchListEntry entry = mData.get(i);
        Resource resource = entry.getResource();
        if(resource != null) {
            myViewHolder.titleTV.setText(resource.getName());
            myViewHolder.dateOfViewTV.setText(entry.getDateOfViewing().toString());
            if(resource.getPosterURL() != null) {
                Picasso.get().load(resource.getPosterURL()).into(myViewHolder.posterIV);
            } else {
                myViewHolder.posterIV.setImageResource(R.drawable.default_poster);
            }
            myViewHolder.ratingBar.setRating(entry.getRating());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        protected final TextView titleTV;
        private final TextView dateOfViewTV;
        private final ImageView posterIV;
        private final RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.watchlist_movie_title);
            dateOfViewTV = itemView.findViewById(R.id.watchlist_date);
            posterIV = itemView.findViewById(R.id.watchlist_poster);
            ratingBar = itemView.findViewById(R.id.rating_bar_watchlist);
            Button deleteButton = itemView.findViewById(R.id.watchlist_delete_button);
            Button shareButton = itemView.findViewById(R.id.watchlist_share_button);
            //on va faire comme pour l'accueil (watchList Clickée -> on va sur la page de la ressource :
            /* itemView.setOnClickListener(v ->
                    resourceItemClickListener.onResourceClick(mData.get(MyViewHolder.this.getAdapterPosition()).getResource())); */
            ratingBar.setOnRatingBarChangeListener((ratingBar, val, isUser) ->
                    watchListItemListener.onRatingChanged(mData.get(getAdapterPosition()), val, isUser));
            deleteButton.setOnClickListener(v ->
                    watchListItemListener.onClickDelete(mData.get(getAdapterPosition())));
            shareButton.setOnClickListener(v ->
                    watchListItemListener.onClickShare(mData.get(getAdapterPosition())));
        }
    }
}
