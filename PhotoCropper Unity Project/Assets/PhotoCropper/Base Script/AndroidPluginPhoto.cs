using UnityEngine;
using System.Collections;

public class AndroidPluginPhoto : PluginBasee<AndroidPluginPhoto>
{

    protected override string GetJavaName()
    {
        return "com.photocropper.code.PhotoHelper";
    }


    //Load photo from the gallery
    public void LocalPhoto()
    {
        CallStaticMethod("LocalPhoto");
    }
}
