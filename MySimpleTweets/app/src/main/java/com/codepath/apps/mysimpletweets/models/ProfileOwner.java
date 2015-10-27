package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by msamant on 10/24/15.
 */
public class ProfileOwner implements Serializable {

private String userName;
private String screenName;
private String profileImageUrl;

    public String getUserName() {
        return userName;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

  public static ProfileOwner fromJSON(JSONObject jsonObject){
      ProfileOwner profileOwner = new ProfileOwner();
      try{
          profileOwner.userName= jsonObject.getString("name");
          profileOwner.screenName=jsonObject.getString("screen_name");
          profileOwner.profileImageUrl=jsonObject.getString("profile_image_url");
      }
      catch(JSONException e){
          e.printStackTrace();
      }
      return profileOwner;
  }
}
