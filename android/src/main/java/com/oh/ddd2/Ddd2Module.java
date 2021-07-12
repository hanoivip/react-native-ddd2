package com.oh.ddd2;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import android.content.Intent;
import org.cocos2dx.hellolua.HelloLua;

public class Ddd2Module extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public Ddd2Module(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "Ddd2";
    }

    @ReactMethod
	public void enterGameWithName(String token, String username, Callback callback) {
		ReactApplicationContext context = getReactApplicationContext();
		Intent intent = new Intent(context, HelloLua.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("token", token);
		intent.putExtra("username", username);
		context.startActivity(intent);
	}
	
	@ReactMethod
	public void enterGame(String token, int uid, Callback callback) {
		ReactApplicationContext context = getReactApplicationContext();
		Intent intent = new Intent(context, HelloLua.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("token", token);
		intent.putExtra("uid", uid);
		context.startActivity(intent);
	}
}
