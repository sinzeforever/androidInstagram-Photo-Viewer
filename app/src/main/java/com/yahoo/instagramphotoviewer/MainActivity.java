package com.yahoo.instagramphotoviewer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends Activity {
    private Button toastButton;
    private InstagramPopularList instagramPopularList;
    private SimpleAdapter adapter;
    private ListView listView;
    private boolean isLoading;
    private Toast toast;
    static int listType = 1;

    final int RECENT_LIST = 1;
    final int FOLLOW_LIST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up toast
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        // get list view
        listView = (ListView) findViewById(R.id.listView);

        // set up list
        instagramPopularList = new InstagramPopularList();

        // get data by api
        getDataByApi();
    }

    private void getDataByApi () {
        String url = instagramPopularList.getApiUrl();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
            // on success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                setUpList(response);
            }

            // on fail

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("my", "fail api");
                super.onSuccess(statusCode, headers, response);
            }
        });
    }

    private void setUpList(JSONObject response) {
        Log.d("my", response.toString());
        instagramPopularList.parseApiResponse(response);
        // set up adapter
        Log.d("my", "the list " + instagramPopularList.getList().size());
        InstagramItemAdapter adapter = new InstagramItemAdapter(getBaseContext(), instagramPopularList.getList());
        listView.setAdapter(adapter);
    }

    private void showLoading() {
        isLoading = true;
        makeToast("Loading ..");
    }

    public void makeToast(String input) {
        toast.setText(input);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}