package com.joyful.joyfulkitchen.util;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * 用户显示提示用的
 */

public class ToastUtil {

    private static Toast mToast;

    /**
     * 打印消息并且用Toast显示消息
     *
     * @param activity
     * @param message
     * @param logLevel 填d, w, e分别代表debug, warn, error; 默认是debug
     */
    public static final void toastMessage(final Activity activity,
                                          final String message, String logLevel) {
        if ("w".equals(logLevel)) {
            Log.w(TAG, message);
        } else if ("e".equals(logLevel)) {
            Log.e(TAG, message);
        } else {
            Log.d(TAG, message);
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;
                }
                mToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    /**
     * 打印消息并且用Toast显示消息
     *
     * @param activity
     * @param message  填d, w, e分别代表debug, warn, error; 默认是debug
     */
    public static final void toastMessage(final Activity activity,
                                          final String message) {
        toastMessage(activity, message, null);
    }



}
