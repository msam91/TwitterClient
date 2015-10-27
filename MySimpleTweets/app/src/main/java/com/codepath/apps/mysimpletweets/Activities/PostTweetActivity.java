package com.codepath.apps.mysimpletweets.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.clients.TwitterClient;
import com.codepath.apps.mysimpletweets.models.ProfileOwner;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class PostTweetActivity extends ActionBarActivity {

    private TwitterClient client;
    private EditText etTweet;
    private ImageView ivOwnerProfileImage;
    private TextView tvOwnerUserName;
    private TextView tvOwnerScreenName;
    private TextView tvCharCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_tweet);
        client = TwitterApplication.getRestClient();
        setUpView();
        ProfileOwner profileOwner = (ProfileOwner) getIntent().getSerializableExtra("profile");
        Picasso.with(getApplicationContext()).load(profileOwner.getProfileImageUrl()).fit().placeholder(R.drawable.abc_spinner_mtrl_am_alpha).into(ivOwnerProfileImage);
        tvOwnerUserName.setText(profileOwner.getUserName());
        tvOwnerScreenName.setText("@"+profileOwner.getScreenName());


        final TextWatcher mTextEditorWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                tvCharCount.setText(String.valueOf(String.valueOf(140 - s.length())));
            }
            public void afterTextChanged(Editable s) {

            }
        };
        etTweet.addTextChangedListener(mTextEditorWatcher);
    }


    private void setUpView(){
        etTweet = (EditText)findViewById(R.id.etTweet);
        ivOwnerProfileImage=(ImageView)findViewById(R.id.ivProfileOwnerImage);
        tvOwnerUserName =(TextView)findViewById(R.id.tvProfileUserName);
        tvOwnerScreenName = (TextView)findViewById(R.id.tvProfileScreenName);
        tvCharCount = (TextView)findViewById(R.id.tvCharCount);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_tweet, menu);
        return true;
    }

    public void onPostTweet(View view){
        String status = etTweet.getText().toString();
        client.postTweet(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header header[],JSONObject response){
                setResult(200);
                finish();
            }
            @Override
            public void onFailure(int statusCode, Header header[],Throwable throwable,JSONObject response){
                //Log.e("ERROR", response.toString());
            }
        },status);

    }

    public void onCancel(View view){
        setResult(400);
        finish();
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
