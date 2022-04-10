package com.example.projetandroid2022;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetandroid2022.adapters.ActorAdapter;
import com.example.projetandroid2022.db.DBHandler;
import com.example.projetandroid2022.entities.Resource;
import com.example.projetandroid2022.entities.WatchListEntry;
import com.squareup.picasso.Picasso;

import java.sql.Date;

public class ResourceActivity extends AppCompatActivity {
    private RecyclerView acteursRV;
    private RatingBar ratingBar;
    private Button watchlistButton;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Intent i = getIntent();
        Resource s =(Resource) i.getSerializableExtra("resource");
        acteursRV = findViewById(R.id.actors_part);
        ratingBar = findViewById(R.id.rating_bar_resource);
        watchlistButton = findViewById(R.id.add_watchlist_button);
        db = new DBHandler(this);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingBar.setRating(v);
            }
        });

        watchlistButton.setOnClickListener(v -> {
            WatchListEntry entry = new WatchListEntry();
            entry.setRating(ratingBar.getRating());
            entry.setShow(s.isShow());
            entry.setResourceId(s.getId());

            db.insertEntry(entry);
        });

        if(s.getActeurs() != null) {
            ActorAdapter showsAdapter = new ActorAdapter(this, s.getActeurs());
            acteursRV.setAdapter(showsAdapter);
            acteursRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        }
        if(s.getBackdropURL()!=null){
            Picasso.get().load(s.getBackdropURL()).into((ImageView) findViewById(R.id.backdrop_img));
        }else{
            ((ImageView) findViewById(R.id.backdrop_img)).setImageResource(R.drawable.home_cover);
        }
        if(s.getPosterURL()!=null){
            Picasso.get().load(s.getPosterURL()).into((ImageView) findViewById(R.id.poster_img));
        }else{
            ((ImageView) findViewById(R.id.backdrop_img)).setImageResource(R.drawable.home_cover);
        }
        ((TextView)findViewById(R.id.synopsis_text)).setText(s.getSynopsis());
        ((TextView)findViewById(R.id.movie_title)).setText(s.getName());
        ((TextView)findViewById(R.id.movie_director)).setText(s.getDate());

    }
}