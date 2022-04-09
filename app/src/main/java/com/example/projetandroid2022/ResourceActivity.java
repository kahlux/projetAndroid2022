package com.example.projetandroid2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetandroid2022.adapters.ActorAdapter;
import com.example.projetandroid2022.entities.Resource;

public class ResourceActivity extends AppCompatActivity {
    private RecyclerView acteursRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Intent i = getIntent();
        Resource s =(Resource) i.getSerializableExtra("resource");
        acteursRV = findViewById(R.id.actors_part);
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