package com.photocropper.code;

// Credit:https://github.com/yasirkula/UnityNativeGallery
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NativeGalleryUtils
{
    private static String secondaryStoragePath = null;
    private static int isXiaomiOrMIUI = 0; // 1: true, -1: false

    public static boolean IsXiaomiOrMIUI()
    {
        if( isXiaomiOrMIUI > 0 )
            return true;
        else if( isXiaomiOrMIUI < 0 )
            return false;

        if( "xiaomi".equalsIgnoreCase( android.os.Build.MANUFACTURER ) )
        {
            isXiaomiOrMIUI = 1;
            return true;
        }

        // Check if device is using MIUI
        // Credit: https://gist.github.com/Muyangmin/e8ec1002c930d8df3df46b306d03315d
        String line;
        BufferedReader inputStream = null;
        try
        {
            java.lang.Process process = Runtime.getRuntime().exec( "getprop ro.miui.ui.version.name" );
            inputStream = new BufferedReader( new InputStreamReader( process.getInputStream() ), 1024 );
            line = inputStream.readLine();

            if( line != null && line.length() > 0 )
            {
                isXiaomiOrMIUI = 1;
                return true;
            }
            else
            {
                isXiaomiOrMIUI = -1;
                return false;
            }
        }
        catch( Exception e )
        {
            isXiaomiOrMIUI = -1;
            return false;
        }
        finally
        {
            if( inputStream != null )
            {
                try
                {
                    inputStream.close();
                }
                catch( Exception e )
                {
                }
            }
        }
    }


}