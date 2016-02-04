package com.voudeonibusapp.android.api;

import com.voudeonibusapp.android.aux.Callback;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class LineAPI extends Base {

    public static String NAME_API = "line";

    public static void all(final Callback callback) {
        get("line", "/all", null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                callback.exec(statusCode, response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                callback.exec(statusCode, response);
            }
            
        });
    }

}
