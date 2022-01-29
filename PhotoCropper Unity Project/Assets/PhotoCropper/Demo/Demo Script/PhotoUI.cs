using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System.IO;
public class PhotoUI : MonoBehaviour {
    public Image img;
 
    void Start() 
    {
        //Register photo events
        MessageManager.RegisterMessage(MessageName.PhotoLoaded, OnPlotonLoad);
	}

    void OnDestroy()
    {
        //Remove photo events
        MessageManager.RemoveMessage(MessageName.PhotoLoaded, OnPlotonLoad);
    }

    public void LocalPhoto()
    {
        PhotoPluginManager.LocalPhoto();
    }

    
    void OnPlotonLoad(object obj)
    {
        PlayerPrefs.SetString("profile_photo_path", obj.ToString());
        StartCoroutine(ReloadImg(obj.ToString()));
    }

    //Load photo
    IEnumerator ReloadImg(string imgPath)
    {
        string path = "file:///" + Application.persistentDataPath + "/" + imgPath;
        WWW www = new WWW(path);
        yield return www;
        Debug.Log(www.error);
        Texture2D texture = (Texture2D)www.texture;
        img.sprite = Sprite.Create(texture, new Rect(0, 0, texture.width, texture.height), new Vector2(0.5f, 0.5f));
    }


}
