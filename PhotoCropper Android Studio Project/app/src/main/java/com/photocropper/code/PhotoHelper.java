package com.photocropper.code;

import android.app.Activity;
import android.content.Intent;

import com.unity3d.player.UnityPlayer;

public class PhotoHelper {

    public static void LocalPhoto()
    {
        final Activity currentActivity = (Activity)UnityPlayer.currentActivity;
        currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                Intent intent = new Intent(currentActivity,WebViewActivity.class);
                intent.putExtra("type", "GalleryPhoto");
                currentActivity.startActivity(intent);
            }
        });
    }
}
