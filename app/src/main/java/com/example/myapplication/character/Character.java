package com.example.myapplication.character;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;

public class Character implements Comparable {

    public String name;
    public String status;
    public String species;
    public String gender;
    public String image_url;
    public Instant created;

    public Character(JSONObject json)  throws JSONException {
        this.name = json.getString("name");
        this.status = json.getString("status");
        this.species = json.getString("species");
        this.gender = json.getString("gender");
        this.image_url = json.getString("image");
        this.created = Instant.parse(json.getString("created"));
    }


    @Override
    public int compareTo(Object o) {
        if(o instanceof Character) {
            Character c = (Character) o;
            if (this.status.equalsIgnoreCase(c.status)) {
                return -this.created.compareTo(c.created);
            } else if (this.status.equalsIgnoreCase("alive")) {
                return 1;
            } else if(this.status.equalsIgnoreCase("dead")) {
                return -1;
            } else {
                if (c.status.equalsIgnoreCase("alive")) {
                    return -1;
                } else if (c.status.equalsIgnoreCase("dead")) {
                    return 11;
                }
            }
        }
        return 0;
    }
}
