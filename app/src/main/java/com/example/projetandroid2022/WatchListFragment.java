package com.example.projetandroid2022;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetandroid2022.adapters.SearchAdapter;
import com.example.projetandroid2022.adapters.WatchListEntryAdapter;
import com.example.projetandroid2022.adapters.WatchListItemListener;
import com.example.projetandroid2022.db.DBHandler;
import com.example.projetandroid2022.entities.WatchListEntry;

import java.util.List;

public class WatchListFragment extends Fragment implements WatchListItemListener {

    private List<WatchListEntry> entries;
    private RecyclerView moviesRV, showsRV;
    public WatchListFragment(List<WatchListEntry> entries) {
        this.entries = entries;
    }
    private DBHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View res = inflater.inflate(R.layout.fragment_watchlist_movie, container, false);
        moviesRV = res.findViewById(R.id.watchlist_movies_rv);

        WatchListEntryAdapter moviesAdapter = new WatchListEntryAdapter(getActivity(), entries, this);
        moviesRV.setAdapter(moviesAdapter);
        moviesRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        return res;
    }

    @Override
    public void onClickDelete(WatchListEntry entry) {
        db = new DBHandler(getActivity());

        db.deleteById(entry.getId());

        Toast.makeText(getActivity(),
                "La ressource " + entry.getResource().getName() + "viens d'être supprimée."
                , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRatingChanged(WatchListEntry entry, float v, boolean b) {
        db = new DBHandler(getActivity());

        if(b) {
            db.updateByRating(entry.getId(), v);
        }

        Toast.makeText(getActivity(),
                "La note de " + entry.getResource().getName()+ " est maintenant de " + v + "/5"
                , Toast.LENGTH_SHORT).show();
    }
}