using UnityEngine;
using System.Collections;

public class PhotoPluginManager 
{
    //Load the photo from the local gallery
    public static void LocalPhoto()
    {
#if UNITY_ANDROID
        AndroidPluginPhoto.Instance.LocalPhoto();
#endif
    }
}
