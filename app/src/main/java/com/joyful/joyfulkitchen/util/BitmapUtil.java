package com.joyful.joyfulkitchen.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/4/6.
 */

public class BitmapUtil {
    /**
     * 根据一个网络连接(String)获取bitmap图像
     *
     * @param imageUri
     * @return
     * @throws
     */
    public static Bitmap getbitmap(String imageUri) {
        Log.v(TAG, "getbitmap:" + imageUri);
        // 显示网络上的图片
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

            Log.v(TAG, "image download finished." + imageUri);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            bitmap = null;
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "getbitmap bmp fail---");
            bitmap = null;
        }
        return bitmap;
    }
}
