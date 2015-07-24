package com.yahoo.instagramphotoviewer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sinze on 7/24/15.
 */
public class InstagramItem {
    public String type;
    public String link;
    public String createTime;
    public String caption;
    public String imageUrl;
    public String user;
    public int imageHeight;
    public ArrayList<InstagramComment> commentList;
    public int commentNumber;
    public static int commentLimit = 2;

    public InstagramItem(JSONObject rawObject) {
        parseRawJson(rawObject);
    }

    private void parseRawJson(JSONObject rawObject) {
        try {
            type = rawObject.getString("type");
            createTime = rawObject.getString("created_time");
            link  = rawObject.getString("link");
            caption = rawObject.getJSONObject("caption").getString("text");
            user = rawObject.getJSONObject("user").getString("username");
            imageUrl = rawObject.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            imageHeight = rawObject.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
            // get comment
            commentList = new ArrayList<InstagramComment>();
            commentNumber = rawObject.getJSONObject("comments").getInt("count");
            JSONArray jsonArray = rawObject.getJSONObject("comments").getJSONArray("data");
            for(int i = 0 ; i < commentLimit; i++) {
                commentList.add(new InstagramComment(jsonArray.getJSONObject(i)));
                Log.d("my", "comments num " + i);
            }

        } catch (Exception e) {
            Log.d("my", "parse Json error in instagram item");
        }
    }
}

class InstagramComment {
    public String createTime;
    public String text;
    public String user;

    public InstagramComment(JSONObject rawObject) {
        try {
            createTime = rawObject.getString("created_time");
            text = rawObject.getString("text");
            user = rawObject.getJSONObject("from").getString("username");

        } catch (Exception e) {
            Log.d("my", "parse Json error in instagram comment");
        }
    }
}