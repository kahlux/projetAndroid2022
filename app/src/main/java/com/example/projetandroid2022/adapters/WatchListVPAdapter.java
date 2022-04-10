package com.example.projetandroid2022.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projetandroid2022.WatchListMovieFragment;
import com.example.projetandroid2022.WatchListTVFragment;
import com.example.projetandroid2022.entities.WatchListEntry;

import java.util.ArrayList;
import java.util.List;

public class WatchListVPAdapter extends FragmentStateAdapter {

    List<WatchListEntry> entries;
    public WatchListVPAdapter(@NonNull FragmentActivity fragmentActivity, List<WatchListEntry> entries) {
        super(fragmentActivity);
        if(entries != null) this.entries = entries;
        else this.entries = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("VPAdapter", "createFragment: " + position);
        switch(position) {
            case 0:
                List<WatchListEntry> movies = new ArrayList<WatchListEntry>();
                for(WatchListEntry entry : entries) {
                    if(!entry.isShow()) movies.add(entry);
                }
                return new WatchListMovieFragment(movies);
            case 1:
                Log.d("VPAdapter", "createFragment: " + entries.size());
                List<WatchListEntry> shows = new ArrayList<WatchListEntry>();
                for(WatchListEntry entry : entries) {
                    if(entry.isShow()) shows.add(entry);
                }
                return new WatchListTVFragment(shows);
            default:
                return new WatchListMovieFragment(null);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
