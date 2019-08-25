package com.example.myapplication.episode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Episode {

    public String name;
    public String airDate;
    public String episode;
    public List<String> charactersUrl;

    public Episode(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString("name");
        airDate = jsonObject.getString("air_date");
        episode = jsonObject.getString("episode");
        charactersUrl = new ArrayList<>();

        JSONArray charUrlArray = jsonObject.getJSONArray("characters");

        for(int i=0; i<charUrlArray.length(); i++){
            charactersUrl.add(charUrlArray.getString(i));
        }
    }


}
