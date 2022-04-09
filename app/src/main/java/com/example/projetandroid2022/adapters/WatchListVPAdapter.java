package com.example.projetandroid2022.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projetandroid2022.WatchListMovieFragment;
import com.example.projetandroid2022.WatchListTVFragment;
import com.example.projetandroid2022.entities.WatchListEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        switch(position) {
            case 0:
                entries = entries.stream().filter(WatchListEntry::isShow).collect(Collectors.toList());
                return new WatchListMovieFragment(entries);
            case 1:
                entries = entries.stream().filter(x -> !x.isShow()).collect(Collectors.toList());
                return new WatchListTVFragment(entries);
        }
        return new WatchListMovieFragment(entries);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
