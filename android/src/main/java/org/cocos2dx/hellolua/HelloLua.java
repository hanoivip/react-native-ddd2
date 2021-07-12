package org.cocos2dx.hellolua;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.ColorMatrixColorFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//import com.testin.agent.TestinAgent;
import com.wyd.sdkMethod.SDKPort;
import com.wyd.sdkMethod.WYDActivityManager;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxGLSurfaceView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import wyd.adapter.WydDelegateManager;
import wyd.android.ui.WZLocation;
import wyd.android.ui.WZNotification;
import wyd.android.ui.WZWebView;
import wyd.android.utils.DeviceHelper;
import wyd.android.utils.DeviceInfo;
import wyd.android.utils.ExpansionFileHelper;
import wyd.android.utils.WZChannelUtils;
import wyd.android.utils.WZRunnableUtils;
import wyd.android.utils.WydCommonUtil;

public class HelloLua extends Cocos2dxActivity {
    protected LuaGLSurfaceView m_glSurfaceView;
    Context context = this;
    public ViewGroup.LayoutParams lp;
    public float scale = 1.0F;
    public int screen_height = 0;
    public int screen_width = 0;
    public static HelloLua cocosActivity;
    public View mView;
    private MediaPlayer player;
    private Handler mHandler;
    public DandandaoUtils utils = null;
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        List<String> mPermissionList = new ArrayList<String>();
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
        } else {//请求权限方法
            String[] permissions2 = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(this, permissions2, 1);
        }
        //if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
        //Log.e("GCloudVoice", "No phone permission");
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        //} else {
        //Log.e("GCloudVoice", "Hava phone permission");
        //GCloudVoiceEngine.getInstance().init(getApplicationContext(), this);
        //}
        //mtp初始化
        //TP2Sdk.initEx(19061, "f55e58cb08416694002d43394c1b3660");

//		int layoutId = getResources().getIdentifier("basic_view", "layout",getPackageName());
//		LayoutInflater inflater = getLayoutInflater();
//		View viewBasic = inflater.inflate(layoutId, null);

        //setContentView(R.layout.basic_view);
        //setContentView(viewBasic);
        //viewBasic.
        //mView = getWindow().getDecorView().findViewWithTag("basic_view");
        CocosActivity();
        //initActivity();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 应用运行时，保持屏幕高亮，不锁屏
        WYDActivityManager.getInstance(context).onCreate();
        WZWebView.setActivity(this);
        // LocationAndroid.setActivity(this);
        WZNotification.setActivity(this);
        WZLocation.setActivity(this);
        DeviceInfo.setActivity(this);
        WZChannelUtils.setActivity(this);
        DeviceHelper.setActivity(this);
        WZRunnableUtils.setActivity(this);
        ExpansionFileHelper.setActivity(this);
        try {
            WydCommonUtil.init(this, this, false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wyd.adapter.WydExtenderBase.setActivity(this);

        // DandandaoUtils utils = new DandandaoUtils(0);
        // int memory = Integer.parseInt(utils.getTotalMemory(""))/1024;
        // if (memory > 800)
        WydDelegateManager.onCreate();
        SDKPort.getInstance().setContext(context);
        // WydBDLocationListener.onCreate(savedInstanceState, this);
        //LocationAndroid.setActivity(this);
        String packName = this.getApplication().getPackageName();
		/*
        if (packName.startsWith("com.wyd.hero.dandandao")
                || packName.startsWith("com.tencent.tmgp")) {
            TestinAgent.init(this, "b08028d96891ebadfb66ab0fc6d5d10f",
                    "wyd_ddd2_quick");
        } else if (packName.startsWith("com.herogame.gplay")) {
            TestinAgent.init(this, "4f57a3ccd8b40ac92afc53b982faffaf",
                    "wyd_ddd2_hk");
        } else if (packName.equals("com.wyd.test")) {
            TestinAgent.init(this, "75fed03c35eea507219ab59767506483",
                    "wyd_ddd2_test");
        } else {
            TestinAgent.init(this, "02408f08aa79af066bfba15e1180feff",
                    "wyd_ddd2");
        }
        Log.i("HelloLua", "onCreate1");
		*/
		//Save intent params
		Intent intent = getIntent();
		String username = intent.getStringExtra("username");
		String token = intent.getStringExtra("token");
		SDKPort.getInstance().username = username;
		SDKPort.getInstance().token = token;
    }


    public void CocosActivity() {
        cocosActivity = this;
    }

    public static HelloLua getActivity() {
        return cocosActivity;
    }

    @SuppressWarnings("static-access")
    public void initActivity() {
        this.lp = m_glSurfaceView.getLayoutParams();
        //m_glSurfaceView.setZOrderOnTop(true);
        FrameLayout frameLayout = this.mFrameLayout;
        ViewGroup.LayoutParams framelayout_params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        frameLayout.setLayoutParams(framelayout_params);
        FrameLayout basicView = (FrameLayout) mView;
        Log.i("baiduMapModule", "initActivity " + basicView + " frameLayout " + frameLayout + " mView " + mView);
        basicView.addView(frameLayout);
    }

    private final static float[] BUTTON_PRESSED = new float[] {
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1.2f, 0 };

    private final static float[] BUTTON_RELEASED = new float[] {
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0 };

    private static final View.OnTouchListener touchListener = new View.OnTouchListener() {

        @SuppressWarnings("deprecation")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                v.getBackground().setColorFilter(new ColorMatrixColorFilter(BUTTON_PRESSED));
                //v.setBackground(v.getBackground());
            }else if(event.getAction() == MotionEvent.ACTION_UP) {
                v.getBackground().setColorFilter(new ColorMatrixColorFilter(BUTTON_RELEASED));
                //v.setBackground(v.getBackground());
            }
            return false;
        }
    };

    public static void setButtonStateChangeListener(View v) {
        //v.setOnTouchListener(touchListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        WydDelegateManager.onNewIntent(intent);
    }

    public void onStart() {
        super.onStart();
        Log.i("HelloLua", "onStart1");
        WydDelegateManager.onStart();

    }

    public void onRestart() {
        super.onRestart();
        WydDelegateManager.onRestart();
    }


    protected void isOpenCheckPlug() throws IOException, InterruptedException {

    }

    protected void getProccess() throws IOException, InterruptedException {
        // TODO Auto-generated method stub
        Process p = Runtime.getRuntime().exec("ps");
        String threadname = p.toString();
        // Log.e("Process", threadname);
        int result = p.waitFor();
        // Log.i("Proccess info result", ""+result);
        InputStream is = p.getInputStream();
        // Log.i("InputStream", ""+is.toString());

        p.getErrorStream();

        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        int n = 0;
        do {
            n = is.read(buffer);
            if (n > 0) {
                bout.write(buffer, 0, n);

            }
        } while (n > 0);
        String proccessInfo = new String(bout.toByteArray());

        // Log.i("Proccess info", new String(bout.toByteArray()));
        getStringByLine(proccessInfo);
    }

    private void getStringByLine(String proccessInfo) {
        // TODO Auto-generated method stub
        String s = proccessInfo;
        byte[] buff = s.getBytes();
        int f = buff.length;
        String[] sArray = s.split("\n");
        int length = sArray.length;
        for (int i = 1; i < sArray.length; i++) {
            // Log.e("message", sArray[i]);
            String[] nameArray = sArray[i].split(" ");

            // Log.e("name", nameArray[nameArray.length-1]);
            read(nameArray[nameArray.length - 1]);
        }
    }

    private void read(String data) {
        // TODO Auto-generated method stub
    }

    public void onStop() {
        super.onStop();
        WYDActivityManager.getInstance().onStop();
        WydDelegateManager.onStop();
    }

    public void onPause() {
        if(utils != null){
            utils.logoStop();
        }
        super.onPause();
        WYDActivityManager.getInstance().onPause();
        WydDelegateManager.onPause();

    }

    public void onResume() {
        if(utils != null){
            utils.logoOver();
        }
        super.onResume();
        WYDActivityManager.getInstance().onResume();
        if (DeviceInfo.name().equalsIgnoreCase("MI 2S"))
            ;
        {
            m_glSurfaceView.setVisibility(View.VISIBLE);
        }
        WydDelegateManager.onResume();
        m_glSurfaceView.setFocusable(true);
        m_glSurfaceView.setFocusableInTouchMode(true);
        m_glSurfaceView.requestFocus();

    }

    public void onDestroy() {
        // super.onResume();
        super.onDestroy();
        WYDActivityManager.getInstance().onDestroy();
        WydDelegateManager.onDestroy();

    }

    public Cocos2dxGLSurfaceView onCreateView() {
        // LuaGLSurfaceView glSurfaceView = new LuaGLSurfaceView(this);
        m_glSurfaceView = new LuaGLSurfaceView(this);
        this.hideSystemUI();
        if (Build.VERSION.SDK_INT < 9) {
            m_glSurfaceView.setEGLConfigChooser(5, 6, 5, 0, 8, 8);
        } else {
            m_glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 8);
        }
        wyd.adapter.WydExtenderBase.setGLSurfaceView(m_glSurfaceView);
        WZRunnableUtils.setGLSurfaceView(m_glSurfaceView);

        // ViewGroup.LayoutParams framelayout_params =
        // new ViewGroup.LayoutParams(50,
        // 50);
        // m_glSurfaceView.setLayoutParams(framelayout_params);

        return m_glSurfaceView;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // if (hasFocus) {
        this.hideSystemUI();
        // }
    }

    @SuppressLint("NewApi")
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        if (Build.VERSION.SDK_INT > 18) {
            m_glSurfaceView
                    .setSystemUiVisibility(Cocos2dxGLSurfaceView.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | Cocos2dxGLSurfaceView.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | Cocos2dxGLSurfaceView.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | Cocos2dxGLSurfaceView.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide
                            // nav
                            // bar
                            | Cocos2dxGLSurfaceView.SYSTEM_UI_FLAG_FULLSCREEN // hide
                            // status
                            // bar
                            | Cocos2dxGLSurfaceView.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        // 其实这里什么都不要做
        super.onConfigurationChanged(newConfig);
    }

    static {
        System.loadLibrary("GCloudVoice");
        System.loadLibrary("WydPlAdapter");
        System.loadLibrary("hellolua");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out
                .println("onActivityResult---onActivityResult---onActivityResult---onActivityResult");
        System.out.println("requestCode:" + requestCode + "---resultCode:"
                + resultCode + "--data:" + data);
        WydDelegateManager.onActivityResult(requestCode, resultCode, data);
        if (this.getApplication().getPackageName()
                .equals("com.wyd.hero.dandandao.wdj")) {
            if (data != null && requestCode == 2) {
                return;
            }
        }
        DeviceHelper.callImageCropperOnResult(requestCode, resultCode, data);
    }

}