package com.wyd.sdkMethod;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.wyd.passport.AsynPassport;
import com.wyd.passport.WYDSDKPort;
//import com.wyd.sdkMethod2.DddFKClassA1;
//import com.wyd.util.Common;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class SDKPort extends WYDSDKPort {
    public static SDKPort instance = null;
    //private CallbackManager callbackManager = null;
	
	public String username;
	public String token;

    public void setContext(Context context) {
        super.setContext(context);
    }

    public static SDKPort getInstance() {
        if (instance == null) {
            instance = new SDKPort();
        }
        return instance;
    }

    public void init(String message) {
        Log.e("hanoivip", "init sdk...");
        //DddFKClassA1.initDddFKClass();
        String str = "10831";
        String str2 = "10001";
        try {
            JSONObject info = new JSONObject(message);
            JSONObject initInfo = info.getJSONObject("SDKInitConfig");
            String appKey = getJsonString(initInfo, "AppKey");
            String appId = getJsonString(initInfo, "AppId");
            if (info.has("SDKOtherConfig")) {
                info.getJSONObject("SDKOtherConfig");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //this.callbackManager = Factory.create();
    }

    public void login(String loginType) {
        Log.e("hanoivip", "login with type " + loginType);
        new Thread() {
            public void run() {
                try {
                    JSONObject payMessage = new JSONObject();
                    try {
                        payMessage.put("return", "success");
                        //payMessage.put("channelId", "1051");
                        payMessage.put("uid", SDKPort.getInstance().username);
                        payMessage.put("loginType", "app");
                        payMessage.put("sessionId", SDKPort.getInstance().token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ((AsynPassport) SDKPort.this.getcallBackObj()).callBack(0, payMessage.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void enterPlatform(String message) {
        Log.e("hanoivip", "enter platform " + message);
    }

    public void startPurchase(String message) {
        Log.e("hanoivip", "start in-app-purchase");
    }

    public void changeAccount() {
    }

    private void submitExtendData(JSONObject message) {
    }

    public void others(String arg0) {
        Log.v("SDKLOG", "this is others:" + arg0);
        String str = "";
        try {
            JSONObject message = new JSONObject(arg0);
            if (message != null && message.has("funType")) {
                String type = message.getString("funType");
                if (type.equals("bindFacebook")) {
                    bindFacebook();
                } else if (type.equals("shareFacebook")) {
                    shareFacebook(message);
                } else if (type.equals("inviteFacebook")) {
                    inviteFacebook(message);
                } else if (type.equals("highFloatButton")) {
                } else {
                    if (type.equals("finshLevel_beimei")) {
                        finshLevel(message);
                    } else if (type.equals("finshPay_beimei")) {
                        finshPay(message);
                    } else if (type.equals("getAdId")) {
                        postAdid();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void postAdid() {

    }

    private void finshLevel(JSONObject message) {

    }

    private void finshPay(JSONObject message) {

    }

    private void bindFacebook() {
    }

    private void shareFacebook(JSONObject msg) {

    }

    private void inviteFacebook(JSONObject msg) {

    }

    /*
    public void xiaohao(String token, String itemId, String purchaseData, String payType) {
        Log.v("SDKLOG", "xiaohao");
        final String str = token;
        final String str2 = itemId;
        final String str3 = purchaseData;
        final String str4 = payType;
        new Thread() {
            public void run() {
                try {
                    int response = SDKPort.this.mService.consumePurchase(3, SDKPort.this.getContext().getPackageName(), str);
                    Log.v("SDKLOG", "result:" + response);
                    JSONObject payMessage = new JSONObject();
                    if (response == 0) {
                        try {
                            payMessage.put("return", "gp_success");
                            payMessage.put("productId", str2);
                            payMessage.put("payToken", str);
                            payMessage.put("payMessage", str3);
                            payMessage.put("payType", str4);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            payMessage.put("return", "fail");
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                    ((AsynPassport) SDKPort.this.getcallBackObj()).callBack(11, payMessage.toString());
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        }.start();
    }*/

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void logout(String message) {
        JSONObject payMessage = new JSONObject();
        try {
            payMessage.put("return", "success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ((AsynPassport) getcallBackObj()).callBack(1, payMessage.toString());
    }

    public boolean doExit() {
        return false;
    }

}
