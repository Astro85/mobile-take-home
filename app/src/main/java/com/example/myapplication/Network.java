package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.character.Character;
import com.example.myapplication.character.CharacterFragment;
import com.example.myapplication.episode.Episode;
import com.example.myapplication.episode.EpisodeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Network {
    private static String TAG = "Network";
    private static Network instance;
    private RequestQueue requestQueue;

    public static Network getInstance(Context context) {
        if (instance == null) {
            instance = new Network(context);
        }
        return instance;
    }

    private Network(Context context) {
        Cache cache = new DiskBasedCache(context.getCacheDir(), 20 * 1024 * 1024);
        com.android.volley.Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
    }

    /*            String url,
            Response.Listener<Bitmap> listener,
            int maxWidth,
            int maxHeight,
            ScaleType scaleType,
            Config decodeConfig,
            @Nullable Response.ErrorListener errorListener*/

    public void getImage(final String url, final ImageView imageView) {
        ImageRequest imageRequest = new ImageRequest(url, new Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, 300, 300, ImageView.ScaleType.MATRIX, Bitmap.Config.ARGB_4444, null);

        requestQueue.add(imageRequest);
    }

    public void getAllCharacters(Episode episode, final CharacterFragment.CharacterListener listener) {
        StringBuilder url = new StringBuilder("https://rickandmortyapi.com/api/character/");

        for(String charUrl : episode.charactersUrl) {
            String[] splitUrl = charUrl.split("/");
            url.append(splitUrl[splitUrl.length-1]).append(",");
        }


        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url.toString(), new Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    List<Character> characters = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        characters.add(new Character(response.getJSONObject(i)));
                    }

                    listener.onCharactersLoaded(characters);
                } catch (JSONException e) {
                    Log.e(TAG, "getAllCharacters onResponse: " + e.getMessage());
                    listener.onError(e);
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "getAllCharacters onErrorResponse: "+ error.getLocalizedMessage());
                listener.onError(error);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void getEpisodes(EpisodeFragment.EpisodeListener listener) {
        getMoreEpisodes(null, listener);
    }

    private void getMoreEpisodes(final String url, final EpisodeFragment.EpisodeListener listener){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url==null?"https://rickandmortyapi.com/api/episode/":url, null, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<Episode> episodes = new ArrayList<>();
                    JSONArray eppArray = response.getJSONArray("results");

                    for(int i=0; i<eppArray.length(); i++) {
                        episodes.add(new Episode(eppArray.getJSONObject(i)));
                    }

                    if (url == null) {
                        listener.onEpisodesLoaded(episodes);
                    } else {
                        listener.onMoreEpisodesLoaded(episodes);
                    }

                    String next = response.getJSONObject("info").getString("next");

                    if(next != null && !next.isEmpty()) {
                        getMoreEpisodes(next, listener);
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "getMoreEpisodes onResponse: "+ e.getMessage());
                    listener.onError(e);
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
                Log.e(TAG, "getMoreEpisodes onResponse: " + error.getMessage());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

}
