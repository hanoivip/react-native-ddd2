package com.wyd.passport;

import android.os.Message;
import android.util.Log;

import com.wyd.sdkMethod.SDKPort;
import wyd.thirdparty.IWydPassport;

public class AsynPassport extends IWydPassport {
    private Message message;

    public AsynPassport(long cppObjAddr) {
        super(cppObjAddr);
        Log.e("hanoivip", String.valueOf(cppObjAddr));
        SDKPort.getInstance().setcallBackObj(this);
    }

    public void appVersionUpdate(String arg0) {
    }

    public void enterPlatform(String arg0) {
    }

    public String getLoginUin() {
        return null;
    }

    public void initSDK(String params) {
        pushMessage(params, 99);
    }

    public void isLogin(String params) {
    }

    public void login(String params) {
        pushMessage(params, 0);
    }

    public void logout(String params) {
        pushMessage(params, 1);
    }

    public void others(String params) {
        pushMessage(params, 12);
    }

    public void setLoginUin(String params) {
    }

    public void startPurchase(String params) {
        pushMessage(params, 11);
    }

    public void accountOthers(String params) {
        pushMessage(params, 12);
    }

    public void purchaseOthers(String params) {
        pushMessage(params, 12);
    }

    public String getVersion() {
        return null;
    }

    public void pushMessage(String params, int type) {
        this.message = Message.obtain();
        this.message.obj = params;
        this.message.what = type;
        SDKPort.getInstance().getPPHandler().sendMessage(this.message);
    }

    public void callBack(int type, final String params) {
        Log.e("hanoivip", "Call back " + String.valueOf(type) + " params " + params);
        String funName = "";
        switch (type) {
            case 0:
                funName = "login";
                break;
            case 1:
                funName = "logout";
                break;
            case 9:
                funName = "platform";
                break;
            case 11:
                funName = "startPurchase";
                break;
            case 12:
                funName = "others";
                break;
            case 99:
                funName = "init";
                break;
        }
        final String name = funName;
        runOnGLThread(new Runnable() {
            public void run() {
                AsynPassport.this.callbackByMethodName(AsynPassport.this.m_cppObjectAddr, name, params);
            }
        });
    }
}
