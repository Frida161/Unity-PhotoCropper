package com.photocropper.code;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import com.unity3d.player.UnityPlayer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

public class WebViewActivity extends Activity
{
    public static final int PHOTOZOOM = 1;
    public static final int PHOTORESULT = 2;

    public static final String IMAGE_UNSPECIFIED = "image/*";
    public final static String FILE_NAME = "image.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String type = this.getIntent().getStringExtra("type");


        if(type.equals("GalleryPhoto"))
        {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
            startActivityForResult(intent, PHOTOZOOM);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;

        if (requestCode == PHOTOZOOM) {
            startPhotoZoom(data.getData());
        }

        if (requestCode == PHOTORESULT) {
           // Toast.makeText(this, "saving...", Toast.LENGTH_LONG).show();
            if (NativeGalleryUtils.IsXiaomiOrMIUI()) {
                UnityPlayer.UnitySendMessage("PluginHelper", "LoadPhoto", FILE_NAME);
                this.finish();
            } else {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    try {
                        SaveBitmap(photo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            this.finish();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);

        if (NativeGalleryUtils.IsXiaomiOrMIUI()) {
            final Activity currentActivity = (Activity)UnityPlayer.currentActivity;
            String pkName = currentActivity.getPackageName();
            String path = "/mnt/sdcard/Android/data/"+pkName+"/files";
            Uri uritempFile = Uri.parse("file://" + "/" +path+ "/"+FILE_NAME);
            //save it to the gallery
            //Uri uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");

            intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
           // Toast.makeText(this, uritempFile.toString()+" crop", Toast.LENGTH_LONG).show();

            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        }

        intent.putExtra("crop", "true");
        if (Build.MANUFACTURER.contains("HUAWEI")) {
            //The hardware manufacturer is Huawei, the default is a circular clipping box. This setting makes it can not be circular
            intent.putExtra("aspectX", 9999);
            intent.putExtra("aspectY", 9998);
        }else{
            //Other phones generally default to square
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }

        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);


        if (NativeGalleryUtils.IsXiaomiOrMIUI()){
            intent.putExtra("return-data", false);//设置为不返回数据
        }
        else{
            intent.putExtra("return-data", true);
        }
        startActivityForResult(intent, PHOTORESULT);
    }

    public void SaveBitmap(Bitmap bitmap) throws IOException {
        if (!NativeGalleryUtils.IsXiaomiOrMIUI()) {
            FileOutputStream fOut = null;
            final Activity currentActivity = (Activity) UnityPlayer.currentActivity;
            String pkName = currentActivity.getPackageName();
            String path = "/mnt/sdcard/Android/data/" + pkName + "/files";
            try {
                File destDir = new File(path);
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }

                fOut = new FileOutputStream(path + "/" + FILE_NAME);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        UnityPlayer.UnitySendMessage("PluginHelper", "LoadPhoto", FILE_NAME);
        this.finish();
    }



}
