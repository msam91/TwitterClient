package com.codepath.apps.mysimpletweets.Activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.Listeners.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.clients.TwitterClient;
import com.codepath.apps.mysimpletweets.models.ProfileOwner;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.Adapters.TweetsArrayAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends ActionBarActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setUpViews();
        client = TwitterApplication.getRestClient();
        populateTimeLine(1,false);
    }

    private void setUpViews() {
        lvTweets = (ListView) findViewById(R.id.lvtweets);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                populateTimeLine(page,false);
                return true;
            }
        });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                aTweets.clear();
                populateTimeLine(1, true);


            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void populateTimeLine(int sinceId, final boolean refresh) {
        client.getHomeTimeLine(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header header[], JSONArray response) {

                if(refresh){
                    swipeContainer.setRefreshing(false);
                }

                aTweets.addAll(Tweet.fromJSONArray(response));
                Log.i("ADAPTER", aTweets.toString());


            }

            @Override
            public void onFailure(int statusCode, Header header[], Throwable throwable, JSONObject response) {
                //Log.e("ERROR", response.toString());
            }
        }, sinceId);
    }

    public void onComposeAction(MenuItem mi) {
        // handle click here
        client.getOwnProfile(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header header[], JSONObject response) {
                ProfileOwner profile =  ProfileOwner.fromJSON(response);
                Intent i = new Intent(TimelineActivity.this, PostTweetActivity.class);
                i.putExtra("profile",profile);
                startActivityForResult(i, 200);

            }

            @Override
            public void onFailure(int statusCode, Header header[], Throwable throwable, JSONObject response) {
                //Log.e("ERROR", response.toString());
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 200 && requestCode == 200) {
            aTweets.clear();
            populateTimeLine(1,false);
        }
        else{

        }

    }
}

