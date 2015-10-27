package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by msamant on 10/24/15.
 */
public class User {

   private String name;
   private String screenName;
   private long uid;
   private String profileImgUrl;

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public long getUid() {
        return uid;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public static User fromJson(JSONObject jsonObject){

       User user = new User();
       try{

          user.name= jsonObject.getString("name");
          user.screenName= jsonObject.getString("screen_name");
          user.uid= jsonObject.getLong("id");
          user.profileImgUrl= jsonObject.getString("profile_image_url");
       }
       catch(JSONException e){
           e.printStackTrace();
       }
        return user;
   }
}
