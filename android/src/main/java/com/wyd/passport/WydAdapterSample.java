package com.wyd.passport;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import wyd.adapter.WydExtenderBase;

public class WydAdapterSample extends WydExtenderBase {
    public WydAdapterSample(long cppObjAddr) {
        super(cppObjAddr);
    }

    public String getVersion() {
        return "1.0.0";
    }

    public void login(String paramJson) {
        Log.e("WydExtenderBase", paramJson);
        runOnGLThread(new Runnable() {
            public void run() {
                JSONObject payMessage = new JSONObject();
                try {
                    payMessage.put("return", "success");
                    payMessage.put("channelId", "1051");
                    payMessage.put("uid", "saksua");
                    payMessage.put("loginType", "app");
                    payMessage.put("sessionId", "12345");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                WydAdapterSample.this.callbackByMethodName(WydAdapterSample.this.m_cppObjectAddr, "login", payMessage.toString());
            }
        });
    }

    public String testStringReturn(String paramJson) {
        Log.i("WydExtenderBase", paramJson);
        return "This is a message sended to lua directlly";
    }
}
