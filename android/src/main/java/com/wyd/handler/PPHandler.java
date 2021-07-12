package com.wyd.handler;

import android.os.Handler;
import android.os.Message;
import com.wyd.sdkMethod.SDKPort;

public class PPHandler extends Handler {
    public void handleMessage(Message msg) {
        String info = msg.obj.toString();
        switch (msg.what) {
            case 0:
                SDKPort.getInstance().login(info);
                return;
            case 1:
                SDKPort.getInstance().logout(info);
                return;
            case 9:
                SDKPort.getInstance().enterPlatform(info);
                return;
            case 11:
                SDKPort.getInstance().startPurchase(info);
                return;
            case 12:
                SDKPort.getInstance().others(info);
                return;
            case 99:
                SDKPort.getInstance().init(info);
                return;
            default:
                return;
        }
    }
}
