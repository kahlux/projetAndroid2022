package com.example.projetandroid2022;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class WatchListTVFragment extends Fragment implements WatchListItemListener {

    private List<WatchListEntry> entries;
    private RecyclerView showRV;
    public WatchListTVFragment(List<WatchListEntry> entries) {
        this.entries = entries;
    }
    private DBHandler db;
    private WatchListEntryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View res = inflater.inflate(R.layout.fragment_watchlist_tv, container, false);
        showRV = res.findViewById(R.id.watchlist_shows_rv);
        adapter = new WatchListEntryAdapter(getContext(), entries, this);

        showRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        showRV.setAdapter(adapter);
        return res;
    }

    @Override
    public void onClickDelete(WatchListEntry entry) {
        db = new DBHandler(getActivity());

        db.deleteById(entry.getId());
        Toast.makeText(getActivity(),
                "La série " + entry.getResource().getName() + " vient d'être supprimée."
                , Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getActivity(), WatchListActivity.class));
        getActivity().finish();
    }

    @Override
    public void onRatingChanged(WatchListEntry entry, float v, boolean b) {
        db = new DBHandler(getActivity());

        if(b) {
            db.updateByRating(entry.getId(), v);
            Toast.makeText(getActivity(),
                    "La note de la série " + entry.getResource().getName()+ " est maintenant de " + v + "/5"
                    , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickShare(WatchListEntry entry) {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String body = "J'ai vu la série " + entry.getResource().getName() + ", je lui ai donné une note de " + entry.getRating() + "/5";
        String sub = "Your Subject";
        myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
        myIntent.putExtra(Intent.EXTRA_TEXT,body);
        startActivity(Intent.createChooser(myIntent, "Partagez avec"));
    }
}