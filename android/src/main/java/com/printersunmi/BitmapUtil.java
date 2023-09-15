package com.printersunmi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BitmapUtil {
  Bitmap bitmap;
  public Bitmap convertToBitMap(final String url) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        URL imageUrl = null;
        try {
          imageUrl = new URL(url);
        } catch (MalformedURLException e) {
          e.printStackTrace();
        }
        try {
          assert imageUrl != null;
          HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
          conn.setDoInput(true);
          conn.connect();
          InputStream is = conn.getInputStream();
          bitmap = BitmapFactory.decodeStream(is);
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
    return bitmap;
  }
}
