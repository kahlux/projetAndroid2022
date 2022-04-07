package com.example.projetandroid2022;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetandroid2022.entities.Actor;
import com.example.projetandroid2022.entities.Resource;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
    }
    private class RequestTask extends AsyncTask<Integer, Void, ArrayList<String>> {
        // Le corps de la tâche asynchrone (exécuté en tâche de fond)
//  lance la requète

        private ArrayList<String> requete(int nbQ) {
            ArrayList<String> response = new ArrayList<String>();
            String jsonRep ="";
            try {
                HttpURLConnection connection = null;
                URL url = new
                        URL("https://opentdb.com/api.php?amount="+nbQ+"&difficulty=easy");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String ligne = bufferedReader.readLine() ;
                while (ligne!= null){
                    jsonRep+=ligne;
                    ligne = bufferedReader.readLine();
                }
                JSONObject toDecode = new JSONObject(jsonRep);
                response = decodeJSON(toDecode);
            } catch (UnsupportedEncodingException e) {
                response.add("problème d'encodage");
            } catch (MalformedURLException e) {
                response.add("problème d'URL ");
            } catch (IOException e) {
                response.add("problème de connexion ");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
        private ArrayList<Actor> requeteAct(int id) {
            ArrayList<Actor> response = new ArrayList<Actor>();
            String jsonRep ="";
            try {
                HttpURLConnection connection = null;
                URL url = new
                        URL("https://opentdb.com/api.php?amount="+id+"&difficulty=easy");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String ligne = bufferedReader.readLine() ;
                while (ligne!= null){
                    jsonRep+=ligne;
                    ligne = bufferedReader.readLine();
                }
                JSONObject toDecode = new JSONObject(jsonRep);
                response = decodeJSONact(toDecode);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
        private ArrayList<Actor> decodeJSONact(JSONObject jso) throws Exception {
            JSONArray jact = jso.getJSONArray("results");
            ArrayList<Actor> cast = new ArrayList<Actor>();
            for(int i=0; i<5; i++) {
                if((String) ((JSONObject)jact.get(i)).get("backdrop_path")=="Acting"){
                    Actor acteur =new Actor();
                    acteur.setNom((String) ((JSONObject)jact.get(i)).get("name"));
                    acteur.setPersonnage((String) ((JSONObject)jact.get(i)).get("character"));
                    acteur.setUrl("https://image.tmdb.org/t/p/original"+(String) ((JSONObject)jact.get(i)).get("profile_path"));
                    cast.add(acteur);
                }
            }
            return cast;
        }
        private ArrayList<String> decodeJSON(JSONObject jso) throws Exception {
            JSONArray ja = jso.getJSONArray("results");
            JSONArray jact = jso.getJSONArray("results");
            ArrayList<String> response = new ArrayList<String>();
            ArrayList<Actor> cast = new ArrayList<>();
            Resource rep  = new Resource();
            for(int i=0; i<ja.length(); i++) {
                rep.setBackdropURL("https://image.tmdb.org/t/p/original"+(String) ((JSONObject)ja.get(i)).get("backdrop_path"));
                rep.setPosterURL("https://image.tmdb.org/t/p/original"+(String) ((JSONObject)ja.get(i)).get("poster_path"));
                rep.setSynopsis((String) ((JSONObject)ja.get(i)).get("overview"));
                rep.setDate((String) ((JSONObject)ja.get(i)).get("release_date"));
                rep.setActeurs(requeteAct((int) ((JSONObject)ja.get(i)).get("id")));
                rep.setName((String) ((JSONObject)ja.get(i)).get("title"));
                rep.setOrigine((String) ((JSONObject)ja.get(i)).get("original_language"));
                rep.setId((int) ((JSONObject)ja.get(i)).get("id"));
            }
            Log.d("rep",response.toString());
            return response;
        }

        @Override
        protected ArrayList<String> doInBackground(Integer... integers) {
            ArrayList<String> response = requete(integers[0]);
            return response;
        }

        protected void onPostExecute(ArrayList<String> result) {
            TextView t;
            EditText txt = new EditText(getApplicationContext());
            int i = 0;
            int temp=0;
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_activity);
            if (result.size()>1){
                while(i<result.size()){
                    t = new TextView(getApplicationContext());
                    t.setText(result.get(i));
                    t.setId(temp);
                    layout.addView(t);
                    i+=1;
                    temp++;
                    t = new EditText(getApplicationContext());
                    t.setId(temp);
                    layout.addView(t);
                    i+=1;
                    temp++;
                    t = new TextView(getApplicationContext());
                    t.setId(temp);
                    layout.addView(t);
                    temp++;
                }
            }else {
                t = new TextView(getApplicationContext());
                t.setText("il faut au moins une question");
                t.setId(0);
                layout.addView(t);
            }
        }
        // Méthode appelée lorsque la tâche de fond sera terminée
//  Affiche le résultat
    }
}