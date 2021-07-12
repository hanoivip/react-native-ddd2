package com.wyd.passport;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.wyd.handler.PPHandler;
import com.wyd.sdkMethod.Delegate;
import org.json.JSONException;
import org.json.JSONObject;
import wyd.adapter.WydDelegateManager;

public abstract class WYDSDKPort {
    private boolean isLog = false;
    protected Object m_callBackObject = null;
    protected Context m_context = null;
    /* access modifiers changed from: private */
    public PPHandler m_handler = null;
    protected boolean payDeBug = false;

    public abstract boolean doExit();

    public abstract void enterPlatform(String str);

    public abstract void init(String str);

    public abstract void login(String str);

    public abstract void logout(String str);

    public abstract void others(String str);

    public abstract void startPurchase(String str);

    public WYDSDKPort() {
        WydDelegateManager.addDelegate(new Delegate());
    }

    public PPHandler getPPHandler() {
        if (this.m_handler == null) {
            SDKLog("not have a PPHanddler");
        }
        return this.m_handler;
    }

    public void setContext(Context context) {
        this.m_context = context;
        if (this.m_handler == null) {
            ((Activity) this.m_context).runOnUiThread(new Runnable() {
                public void run() {
                    WYDSDKPort.this.m_handler = new PPHandler();
                }
            });
        }
        do {
        } while (this.m_handler == null);
    }

    public Context getContext() {
        return this.m_context;
    }

    public void setcallBackObj(Object callBackObject) {
        this.m_callBackObject = callBackObject;
    }

    public Object getcallBackObj() {
        return this.m_callBackObject;
    }

    /* access modifiers changed from: protected */
    public String getJsonString(JSONObject obj, String key) {
        if (obj.has(key)) {
            try {
                return obj.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        SDKToast("无法得到JsonString，返回“”，key为：" + key);
        return "";
    }

    /* access modifiers changed from: protected */
    public Integer getJsonInt(JSONObject obj, String key) {
        if (obj.has(key)) {
            try {
                return Integer.valueOf(obj.getInt(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        SDKToast("无法得到JsonInt，返回1，key为：" + key);
        return Integer.valueOf(1);
    }

    /* access modifiers changed from: protected */
    public void setLog(boolean bool) {
        this.isLog = bool;
    }

    /* access modifiers changed from: protected */
    public void SDKToast(String message) {
        if (this.isLog) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    /* access modifiers changed from: protected */
    public void SDKLog(String message) {
        if (this.isLog) {
            Log.v("SDKLOG", message);
        }
    }
}
