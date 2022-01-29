package com.photocropper.code;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

public class PhotoHelper {

    public static void TakePhoto()
    {
        final Activity currentActivity = (Activity)UnityPlayer.currentActivity;
        currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                Intent intent = new Intent(currentActivity,WebViewActivity.class);
                intent.putExtra("type", "takePhoto");
                currentActivity.startActivity(intent);
            }
        });
    }

    public static void LocalPhoto()
    {
        final Activity currentActivity = (Activity)UnityPlayer.currentActivity;
        currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                Intent intent = new Intent(currentActivity,WebViewActivity.class);
                intent.putExtra("type", "LocalPhoto");
                currentActivity.startActivity(intent);
            }
        });
    }
}
