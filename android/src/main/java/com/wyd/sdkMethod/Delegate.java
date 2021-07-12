package com.wyd.sdkMethod;

import android.content.Intent;
import android.util.Log;
import wyd.adapter.WydDelegateBase;

public class Delegate implements WydDelegateBase {
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

    public void onNewIntent(Intent intent) {
        Log.v("SDKLOG", "quick onNewIntent");
    }

    public void onRestart() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        SDKPort.getInstance().onActivityResult(requestCode, resultCode, data);
    }
}
