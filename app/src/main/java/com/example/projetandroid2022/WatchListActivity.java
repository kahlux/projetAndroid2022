package com.example.projetandroid2022;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projetandroid2022.adapters.WatchListVPAdapter;
import com.example.projetandroid2022.db.DBHandler;
import com.example.projetandroid2022.entities.Actor;
import com.example.projetandroid2022.entities.Resource;
import com.example.projetandroid2022.entities.WatchListEntry;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WatchListActivity extends AppCompatActivity {
    private WatchListVPAdapter vpAdapter;
    private TabLayout tabLayout;
    ViewPager2 viewPager;
    private DBHandler db;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        viewPager = findViewById(R.id.watchlist_vp);
        tabLayout = findViewById(R.id.watchlist_tabs);
        db = new DBHandler(this);
        List<WatchListEntry> entries = db.listAllEntries();
        if(entries != null) {
            for(WatchListEntry entry : entries) {
                RequestTask rq = new RequestTask();
                Resource resource = new Resource();
                resource.setId(entry.getResourceId());
                resource.setShow(entry.isShow());
                try {
                    resource = rq.execute(resource).get();
                    entry.setResource(resource);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        vpAdapter = new WatchListVPAdapter(this, entries);
        viewPager.setAdapter(vpAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();

    }

    public class RequestTask extends AsyncTask<Resource, Void, Resource> {
        private List<Actor> getActorsFromResourceId(int resourceId, boolean isShow) {
            ArrayList<Actor> response = null;
            try {
                HttpURLConnection connection = null;
                URL url = null;
                if(isShow) {
                    url = new URL("https://api.themoviedb.org/3/tv/"
                            + resourceId + "/credits?api_key=8c32f074e2ca41b85ea9e1903ac5730f&language=fr-FR");
                } else {
                    url = new URL("https://api.themoviedb.org/3/movie/"
                            + resourceId + "/credits?api_key=8c32f074e2ca41b85ea9e1903ac5730f&language=fr-FR");
                }
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String ligne = bufferedReader.readLine();
                String jsonText = "";
                while (ligne != null) {
                    jsonText += ligne;
                    ligne = bufferedReader.readLine();
                }
                JSONObject toDecode = new JSONObject(jsonText);
                response = decodeActorsJSON(toDecode);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        private ArrayList<Actor> decodeActorsJSON(JSONObject jso) {
            ArrayList<Actor> response = new ArrayList<>();
            try {
                JSONArray cast = jso.getJSONArray("cast");
                for(int i = 0; i <cast.length(); i++) {
                    Actor actor = new Actor();
                    JSONObject actorJSON = cast.getJSONObject(i);
                    try { actor.setNom(actorJSON.getString("name")); }
                    catch(JSONException e) { actor.setNom("Pas plus d'infos"); }
                    try { actor.setPersonnage(actorJSON.getString("character")); }
                    catch(JSONException e) { actor.setPersonnage("Pas plus d'informations"); }
                    try { actor.setUrl("https://image.tmdb.org/t/p/original"+actorJSON.getString("profile_path")); }
                    catch(JSONException e) { actor.setUrl(null); }
                    response.add(actor);
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }

            return response;
        }

        private Resource decodeResourcesJSON(JSONObject jso, boolean isShow) {
            Resource resource = new Resource();
            boolean isValid = true;
            if(isShow) {
                try { resource.setDate(jso.getString("first_air_date")); }
                catch(JSONException e) { resource.setDate("Date inconnue"); }
                try { resource.setName(jso.getString("name")); }
                catch(JSONException e) { resource.setName("Sans nom"); }
            } else {
                try { resource.setDate(jso.getString("release_date")); }
                catch(JSONException e) { resource.setDate("Date inconnue"); }
                try { resource.setName(jso.getString("title")); }
                catch(JSONException e) { resource.setName("titre"); }
            }
            try { resource.setPosterURL("https://image.tmdb.org/t/p/original"+jso.getString("poster_path")); }
            catch(JSONException e) { resource.setPosterURL(null); }
            try { resource.setBackdropURL("https://image.tmdb.org/t/p/original"+jso.getString("backdrop_path")); }
            catch(JSONException e) { resource.setBackdropURL(null); }
            try { resource.setSynopsis(jso.getString("overview")); }
            catch(JSONException e) { resource.setSynopsis("Pas de description retrouvées"); }
            ArrayList<Actor> actors = null;
            try { actors = (ArrayList<Actor>) getActorsFromResourceId(jso.getInt("id"), isShow); }
            catch (JSONException e) { e.printStackTrace(); }
            if(actors != null) {
                resource.setActeurs(actors);
            }
            try { resource.setId(jso.getInt("id")); }
            catch(JSONException e) { isValid = false; }

            if(!isValid) resource = new Resource();
            return resource;
        }

        @Override
        protected Resource doInBackground(Resource... resources) {
            Resource response = null;
            String str = "";
            JSONObject json = null;
            Resource resource = resources[0];
            if(resource.isShow()) {
                str = "https://api.themoviedb.org/3/tv/"+resource.getId()+"?api_key=8c32f074e2ca41b85ea9e1903ac5730f&language=fr-FR";
            } else {
                str = "https://api.themoviedb.org/3/movie/" + resource.getId() + "?api_key=8c32f074e2ca41b85ea9e1903ac5730f&language=fr-FR";
            }
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }
                json = new JSONObject(stringBuffer.toString());
            }
            catch(Exception ex) {
                Log.d("RequestTask", "doInBackground: ", ex);
                return null;
            }
            finally {
                if(bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return decodeResourcesJSON(json, resource.isShow());
        }
    }
}
