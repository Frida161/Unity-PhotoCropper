using UnityEngine;
using System.Collections;

public class PluginReceiver : MonoBehaviour {

    static PluginReceiver m_instance;

    public static PluginReceiver Instance
    {
        get { return PluginReceiver.m_instance; }
    }

    void Awake()
    {
        m_instance = this;
    }

    //Image cropped from Android finish
    public void LoadPhoto(string imgPath)
    {
        MessageManager.SendMessage(MessageName.PhotoLoaded, imgPath);
    }
}
