package com.andrewtorr.skywriter.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.Date;

/**
 * Created by Andrew on 7/23/2015.
 *
 */
@ParseClassName("Script")
public class Script extends ParseObject {
    private ParseUser user;
    private String title;
    private String text;
    private String both_lowercase;
    private JSONArray lineList;
    private Date date;

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public String getText() {
        return getString("text");
    }

    public void setText(String text) {
        put("text", text);
    }

    public String getBoth_lowercase() {
        return getString("both_lowercase");
    }

    public void setBoth_lowercase(String both_lowercase) {
        put("both_lowercase", both_lowercase);
    }

    public Date getDate() {
        return getDate("date");
    }

    public void setDate(Date date) {
        put("date", date);
    }

    public static ParseQuery<Script> getQuery() {
        return ParseQuery.getQuery(Script.class);
    }

}
