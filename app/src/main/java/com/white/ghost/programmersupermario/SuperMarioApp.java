package com.white.ghost.programmersupermario;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Function:Application
 * Author Name: Chris
 * Date: 2019/4/30 9:49
 */
public class SuperMarioApp extends Application {

    private static SuperMarioApp sSuperMarioApp;
    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        sSuperMarioApp = this;
        // 用于监测内存泄漏，一旦有则显示在log里，debug时会多安装一个"Leaks"的app
        //在leakCanary SDK1.5以前，Android 6.0以上系统可能会报
        // FATAL EXCEPTION: IntentService[HeapAnalyzerService]
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        mRefWatcher = LeakCanary.install(this);
    }

    public static SuperMarioApp getInstance() {
        return sSuperMarioApp;
    }

    /**
     * 获取leakCanary监测对象
     * @return leakCanary监测对象
     */
    public static RefWatcher getRefWatcher() {
        return getInstance().mRefWatcher;
    }
}
