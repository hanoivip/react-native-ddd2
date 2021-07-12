package org.cocos2dx.hellolua;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.core.view.MotionEventCompat;

class MySurfaceView extends SurfaceView {
    private DandandaoUtils utils = null;
    public MySurfaceView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public void setUtils(DandandaoUtils _uitls){
        utils = _uitls;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.v("SDKLOG","keyCode:"+keyCode);
        boolean isKeyCodeSupported = keyCode != KeyEvent.KEYCODE_BACK && keyCode != KeyEvent.KEYCODE_VOLUME_UP && keyCode != KeyEvent.KEYCODE_VOLUME_DOWN ;
        if(isKeyCodeSupported){
            Log.v("SDKLOG","keyCode222:"+keyCode);
            if(utils != null){
                utils.logoOver();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        final int action = MotionEventCompat.getActionMasked(ev);
        Log.v("SDKLOG","onTouchEvent:"+action);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                utils.logoOver();
                break;
        }
        return true;
    }
}
