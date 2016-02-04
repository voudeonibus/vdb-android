package com.voudeonibusapp.android.views.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.voudeonibusapp.android.aux.CallbackImg;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageUtils {

    public static void getImageBitmap(String url, final ImageView imageView) {

        class DownloadImagem extends AsyncTask<String, Integer, Long> {
            protected Long doInBackground(String... urls) {
                int count = urls.length;
                long totalSize = 0;
                try {
                    for (int i = 0; i < count; i++) {
                        Bitmap bm = null;
                        try {
                            URL aURL = new URL(urls[i]);
                            URLConnection conn = aURL.openConnection();
                            conn.connect();
                            InputStream is = conn.getInputStream();
                            BufferedInputStream bis = new BufferedInputStream(is);
                            bm = BitmapFactory.decodeStream(bis);
                            bis.close();
                            is.close();
                        } catch (IOException e) {
                            Crashlytics.logException(e);

                        }

                        imageView.setImageBitmap(bm);




                    }
                } catch (Exception e) {
                    Crashlytics.logException(e);
                }

                return totalSize;
            }

            protected void onProgressUpdate(Integer... progress) {

            }

            protected void onPostExecute(Long result) {

            }
        }

        new DownloadImagem().execute(url);

    }

    public static void getImageBitmap(String url, final CallbackImg callbackImg) {

        class DownloadImagem extends AsyncTask<String, Integer, Long> {
            protected Long doInBackground(String... urls) {
                int count = urls.length;
                long totalSize = 0;
                try {
                    for (int i = 0; i < count; i++) {
                        Bitmap bm = null;
                        try {
                            URL aURL = new URL(urls[i]);
                            URLConnection conn = aURL.openConnection();
                            conn.connect();
                            InputStream is = conn.getInputStream();
                            BufferedInputStream bis = new BufferedInputStream(is);
                            bm = BitmapFactory.decodeStream(bis);
                            bis.close();
                            is.close();
                        } catch (IOException e) {
                            Crashlytics.logException(e);

                        }

                        callbackImg.onEnd(bm);




                    }
                } catch (Exception e) {
                    Crashlytics.logException(e);
                }

                return totalSize;
            }

            protected void onProgressUpdate(Integer... progress) {

            }

            protected void onPostExecute(Long result) {

            }
        }

        new DownloadImagem().execute(url);

    }

    public static Bitmap blurBitmap(Bitmap bitmap, Context context){

        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript rs = RenderScript.create(context);

        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, android.support.v8.renderscript.Element.U8_4(rs));

        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        blurScript.setRadius(25.f);

        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        allOut.copyTo(outBitmap);

        bitmap.recycle();

        rs.destroy();


        return outBitmap;

    }



}
