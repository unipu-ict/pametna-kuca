package com.unipu.marko.pametnakuca;

/**
 * Created by Marko on 25.6.2017..
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSON {
    public static String Datum;
    public static String Temperatura;
    public static String Pritisak;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_ID = "Datum";
    public static final String KEY_NAME = "Temperatura";
    public static final String KEY_EMAIL = "Pritisak";

    private JSONArray users = null;

    private String json;

    public ParseJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            JSONObject jo = users.getJSONObject(0);
            Datum = jo.getString(KEY_ID);
            Temperatura = jo.getString(KEY_NAME);
            Pritisak = jo.getString(KEY_EMAIL);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
