package com.example.projetandroid2022;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetandroid2022.db.DBHandler;

public class WatchListActivity extends AppCompatActivity {
    RecyclerView watchListRV;
    private DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        db = new DBHandler(this);


    }
}
