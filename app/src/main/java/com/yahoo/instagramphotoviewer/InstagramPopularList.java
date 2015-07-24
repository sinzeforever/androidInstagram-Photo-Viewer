package com.yahoo.instagramphotoviewer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sinze on 7/24/15.
 */
public class InstagramPopularList {
    JSONObject jsonObject;
    List<InstagramItem> list;
    final String CLIENT_ID = "2b3254322bf346aa97baa41621e259bd";
    final String apiUrl = "https://api.instagram.com/v1/media/popular?client_id=";

    public InstagramPopularList() {
        list = new ArrayList<InstagramItem>();
    }

    public List<InstagramItem> getList() {
        return this.list;
    }

    public String getApiUrl() {
        return apiUrl + CLIENT_ID;
    }

    public void parseApiResponse(JSONObject jsonObject) {
        try {
            JSONArray dataArray = jsonObject.getJSONArray("data");

            for(int i = 0; i < dataArray.length(); i++) {
                list.add(new InstagramItem(dataArray.getJSONObject(i)));
            }

        } catch (Exception e) {
            Log.d("my", "Fail to parse api response at the beginning");
        }
    }
}
