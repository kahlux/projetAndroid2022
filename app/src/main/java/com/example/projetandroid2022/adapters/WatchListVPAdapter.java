package com.example.projetandroid2022.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projetandroid2022.WatchListFragment;
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
        List<WatchListEntry> res = new ArrayList<WatchListEntry>();
        switch(position) {
            case 0:
                for(WatchListEntry entry : entries) {
                    if(!entry.isShow()) res.add(entry);
                }
                break;
            case 1:
                for(WatchListEntry entry : entries) {
                    if(entry.isShow()) res.add(entry);
                }
                break;
        }
        return new WatchListFragment(res);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
