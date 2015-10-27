package com.codepath.apps.mysimpletweets.Utils;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Locale;
import android.text.format.DateUtils;

/**
 * Created by msamant on 10/24/15.
 */
public class FormatDate {

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();

;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatString(relativeDate);
    }

    public static String formatString(String relativeDate){
        String formattedTime[] = relativeDate.split(" ");
        return formattedTime[0]+formattedTime[1].charAt(0);

    }
}
