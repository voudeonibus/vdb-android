package com.voudeonibusapp.android.aux;

import org.json.JSONArray;
import org.json.JSONObject;

public interface Callback {
    public void exec(int statusCode, JSONObject response);
    public void exec(int statusCode, JSONArray response);
}
