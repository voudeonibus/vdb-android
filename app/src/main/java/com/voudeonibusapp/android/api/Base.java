package com.voudeonibusapp.android.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public abstract class Base {

    private static final String PROTOCOL = "https://";
    private static final String BASE_URL = "apiurlgoesinhere.com";


    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String api, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(api, url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String api, String relativeUrl) {
        return PROTOCOL + api + "." + BASE_URL + relativeUrl;
    }

}
