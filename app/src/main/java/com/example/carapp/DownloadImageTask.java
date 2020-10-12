package com.example.carapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import java.io.InputStream;
import java.util.Objects;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    @Override
    public Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap carIcon = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            carIcon = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", Objects.requireNonNull(e.getMessage()));
            e.printStackTrace();
        }
        return carIcon;
    }
}
