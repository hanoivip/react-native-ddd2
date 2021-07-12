package com.wyd.sdkMethod;

import android.content.Context;

public class WYDActivityManager {
    private static WYDActivityManager instance = null;
    public Context context = null;

    public static WYDActivityManager getInstance(Context context2) {
        if (instance == null) {
            instance = new WYDActivityManager(context2);
        }
        return instance;
    }

    public static WYDActivityManager getInstance() {
        if (instance != null) {
            return instance;
        }
        System.out.println("not a context");
        return null;
    }

    private WYDActivityManager(Context _context) {
        this.context = _context;
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onCreate() {
    }

    public void onFinish() {
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public void onDestroy() {

    }

    public void onRestart() {
    }
}
