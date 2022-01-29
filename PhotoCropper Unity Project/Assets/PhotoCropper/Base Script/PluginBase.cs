using UnityEngine;
using System.Collections;

public abstract class PluginBasee<T> where T : new()
{
    private static T instance;
    public static T Instance
    {
        get
        { 
            if (null == instance)
            {
                instance = new T();
                GameObject go = GameObject.Find("PluginHelper");
                if (go == null)
                {
                    go = new GameObject("PluginHelper");
                    GameObject.DontDestroyOnLoad(go);
                    go.AddComponent<PluginReceiver>();
                }
            }
            return instance;
        }
    }


#if UNITY_ANDROID
    private AndroidJavaClass m_jc_PluginHelper;
    public AndroidJavaClass Jc_PluginHelper
    {
        get 
        {
            if (m_jc_PluginHelper == null)
            {
                m_jc_PluginHelper = new AndroidJavaClass(GetJavaName());
            }
            return m_jc_PluginHelper;
        }
    }
#endif

    #region Encapsulate some method
    protected void CallStaticMethod(string methodName, params object[] args)
    {
#if UNITY_ANDROID
        Jc_PluginHelper.CallStatic(methodName, args);
#endif
    }

    protected T CallStaticMethod<T>(string methodName, params object[] args)
    {
#if UNITY_ANDROID
        return Jc_PluginHelper.CallStatic<T>(methodName, args);
#endif
        return default(T);
    }
    #endregion

    #region virtual method

    protected virtual string GetJavaName()
    {
        throw new System.NotImplementedException();
    }
    #endregion
}
