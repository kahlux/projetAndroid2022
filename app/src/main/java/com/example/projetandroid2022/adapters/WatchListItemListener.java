package com.example.projetandroid2022.adapters;

import com.example.projetandroid2022.entities.WatchListEntry;

public interface WatchListItemListener {

    void onClickDelete(WatchListEntry entry);
    void onRatingChanged(WatchListEntry watchListEntry, float v, boolean b);
    void onClickShare(WatchListEntry entry);
}
