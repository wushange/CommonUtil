package com.connxun.demoprogrect.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 整个应用程序Applicaiton
 *
 * @author wushange
 * @Description
 * @ClassName MApplication
 * @date 2015-11-3 下午12:40:18
 */
public class MApplication extends Application {

    private static MApplication INSTANCE;
    /**
     * 对外提供整个应用生命周期的Context
     **/
    private static Context context;
    /**
     * 整个应用全局可访问数据集合
     **/
    private static Map<String, Object> gloableData = new HashMap<String, Object>();
    /***
     * 寄存整个应用Activity
     **/
    private final Stack<WeakReference<Activity>> activitys = new Stack<WeakReference<Activity>>();
    private String TOKEN = null;
    private String TAG = "MApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }


    /**
     * 对外提供Application Context
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }

    public MApplication() {
        super();
    }

    /******************************************************* Application数据操作API（开始） ********************************************************/

    /**
     * 往Application放置数据（最大不允许超过5个）
     *
     * @param strKey   存放属性Key
     * @param strValue 数据对象
     */
    public static void assignData(String strKey, Object strValue) {
        if (gloableData.size() > 5) {
            throw new RuntimeException("超过允许最大数");
        }
        gloableData.put(strKey, strValue);
    }

    /**
     * 从Applcaiton中取数据
     *
     * @param strKey 存放数据Key
     * @return 对应Key的数据对象
     */
    public static Object gainData(String strKey) {
        return gloableData.get(strKey);
    }

    /*
     * 从Application中移除数据
     */
    public static void removeData(String key) {
        if (gloableData.containsKey(key))
            gloableData.remove(key);
    }

    /******************************************************* Application数据操作API（结束） ********************************************************/

    /******************************************* Application中存放的Activity操作（压栈/出栈）API（开始） *****************************************/

    /**
     * 将Activity压入Application栈
     *
     * @param task 将要压入栈的Activity对象
     */
    public void pushTask(WeakReference<Activity> task) {
        activitys.push(task);
    }

    /**
     * 将传入的Activity对象从栈中移除
     *
     * @param task
     */
    public void removeTask(WeakReference<Activity> task) {
        activitys.remove(task);
    }

    /**
     * 根据指定位置从栈中移除Activity
     *
     * @param taskIndex Activity栈索引
     */
    public void removeTask(int taskIndex) {
        if (activitys.size() > taskIndex)
            activitys.remove(taskIndex);
    }

    /**
     * 将栈中Activity移除至栈顶
     */
    public void removeToTop() {
        int end = activitys.size();
        int start = 1;
        for (int i = end - 1; i >= start; i--) {
            if (!activitys.get(i).get().isFinishing()) {
                activitys.get(i).get().finish();
            }
        }
    }

    /**
     * 移除全部（用于整个应用退出）
     */
    public void removeAll() {
        // finish所有的Activity
        for (WeakReference<Activity> task : activitys) {
            if (!task.get().isFinishing()) {
                task.get().finish();
            }
        }
    }

    /******************************************* Application中存放的Activity操作（压栈/出栈）API（结束） *****************************************/
}
