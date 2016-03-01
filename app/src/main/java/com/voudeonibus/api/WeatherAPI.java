package com.voudeonibus.api;


import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.voudeonibus.aux.Callback;
import com.voudeonibus.controller.WeatherController;
import com.voudeonibus.models.aux.Weather;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherAPI {


    private static final String PROTOCOL = "http://";
    private static final String BASE_URL = "api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "openweathermapapigoeshere";

    private static AsyncHttpClient client = new AsyncHttpClient();

    private static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return PROTOCOL + BASE_URL + relativeUrl;
    }

    public static void update(final Callback callback) {

        RequestParams requestParams = new RequestParams();
        requestParams.put("q", "Jaragua+do+Sul+SC+Brasil");
        requestParams.put("units", "metric");
        requestParams.put("APPID", API_KEY);

        get("", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                try {
                    Log.d("Ops", response.getJSONObject("main").toString());
                } catch (JSONException e) {
                    Crashlytics.logException(e);
                }

                Weather weather = new Weather();

                try {
                    weather.setTemp(response.getJSONObject("main").getDouble("temp"));
                    weather.setDescription(response.getJSONArray("weather").getJSONObject(0).getString("description"));

                    WeatherController.update(weather);
                } catch (JSONException e) {
                    Crashlytics.logException(e);
                }

                if (callback != null) {
                    callback.exec(statusCode, response);
                }
            }
        });
    }

    public static void update() {
        update(null);
    }

}
