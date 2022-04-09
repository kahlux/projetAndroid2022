package com.example.projetandroid2022;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.projetandroid2022.adapters.SearchAdapter;
import com.example.projetandroid2022.adapters.ResourceItemClickListener;
import com.example.projetandroid2022.entities.Actor;
import com.example.projetandroid2022.entities.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity implements ResourceItemClickListener {
    private RecyclerView repRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ArrayList<String> so = getIntent().getStringArrayListExtra("param");
        //initialisation des View utilisées :
        repRV = findViewById(R.id.repRV);
        //initialisation de la data :
        RequestTask repTask = new RequestTask();
        ArrayList<Resource> repTab = null;
        try {
            repTab = repTask.execute(so).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if(repTab != null) {
            SearchAdapter moviesAdapter = new SearchAdapter(this, repTab, this);
            repRV.setAdapter(moviesAdapter);
            repRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        } else {
            //TODO: erreur de connexions, générer quelque chose...
        }
    }

    @Override
    public void onResourceClick(Resource resource) {
        Intent i = new Intent(this,ResourceActivity.class);
        i.putExtra("resource",  resource);
        startActivity(i);
    }

    //classe pour générer les ressources de la page d'accueil
    public class RequestTask extends AsyncTask<ArrayList<String>, Void, ArrayList<Resource>> {

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

        private ArrayList<Resource> decodeResourcesJSON(JSONObject jso, boolean isShow) {
            ArrayList<Resource> response = new ArrayList<>();
            try {
                JSONArray results = jso.getJSONArray("results");
                for(int i = 0; i < results.length(); i++) {
                    Resource resource = new Resource();
                    JSONObject resourceJSON = results.getJSONObject(i);
                    boolean isValid = true;
                    if(isShow) {
                        try { resource.setDate(resourceJSON.getString("first_air_date")); }
                        catch(JSONException e) { resource.setDate("Date inconnue"); }
                        try { resource.setName(resourceJSON.getString("name")); }
                        catch(JSONException e) { resource.setName("Sans nom"); }
                    } else {
                        try { resource.setDate(resourceJSON.getString("release_date")); }
                        catch(JSONException e) { resource.setDate("Date inconnue"); }
                        try { resource.setName(resourceJSON.getString("title")); }
                        catch(JSONException e) { resource.setName("titre"); }
                    }
                    try { resource.setPosterURL("https://image.tmdb.org/t/p/original"+resourceJSON.getString("poster_path")); }
                    catch(JSONException e) { resource.setPosterURL(null); }
                    try { resource.setBackdropURL("https://image.tmdb.org/t/p/original"+resourceJSON.getString("backdrop_path")); }
                    catch(JSONException e) { resource.setBackdropURL(null); }
                    try { resource.setSynopsis(resourceJSON.getString("overview")); }
                    catch(JSONException e) { resource.setSynopsis("Pas de description retrouvées"); }
                    ArrayList<Actor> actors = (ArrayList<Actor>) getActorsFromResourceId(resourceJSON.getInt("id"), isShow);
                    if(actors != null) {
                        resource.setActeurs(actors);
                    }
                    try { resource.setId(resourceJSON.getInt("id")); }
                    catch(JSONException e) { isValid = false; }
                    if(isValid) response.add(resource);
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected ArrayList<Resource> doInBackground(ArrayList<String>... strings) {
            ArrayList<Resource> response = null;
            String str = "";
            JSONObject json = null;
            if(strings[0].get(1).equals("film")) {
                str = "https://api.themoviedb.org/3/search/movie?api_key=8c32f074e2ca41b85ea9e1903ac5730f&language=fr-FR&query=" +
                        strings[0].get(0) + "&page=1&include_adult=false";
            } else {
                str = "https://api.themoviedb.org/3/search/tv?api_key=8c32f074e2ca41b85ea9e1903ac5730f&language=fr-FR&query=" +
                        strings[0].get(0) + "&page=1&include_adult=false";
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
            if(strings[0].get(1).equals("film")){
                return decodeResourcesJSON(json, false);
            }else{
                return decodeResourcesJSON(json, true);
            }
        }
    }
}