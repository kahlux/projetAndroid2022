package com.example.projetandroid2022;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.projetandroid2022.entities.WatchListEntry;

import java.util.List;

public class WatchListMovieFragment extends Fragment {

    private List<WatchListEntry> entries;

    public WatchListMovieFragment(List<WatchListEntry> entries) {
        this.entries = entries;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View res = inflater.inflate(R.layout.fragment_watchlist_movie, container, false);

        return res;
    }
}