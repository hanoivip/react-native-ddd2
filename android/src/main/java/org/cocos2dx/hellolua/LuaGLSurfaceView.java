/****************************************************************************
 Copyright (c) 2010-2012 cocos2d-x.org

 http://www.cocos2d-x.org

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 ****************************************************************************/
package org.cocos2dx.hellolua;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.oh.ddd2.R;
import com.wyd.sdkMethod.SDKPort;
import com.wyd.sdkMethod.WYDActivityManager;

import org.cocos2dx.lib.Cocos2dxGLSurfaceView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import wyd.adapter.WydDelegateManager;
//import com.tencent.tersafe2.TP2Sdk;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//import com.wyd.baidulocation.WydBDLocationListener;
//import wyd.android.analytics.WZTalkingDataGA;
//import wyd.android.ui.WZTencentWeibo;
//import wyd.android.ui.WZSinaWeibo;


class LuaGLSurfaceView extends Cocos2dxGLSurfaceView {
    public static NodeList exitNil;

    public LuaGLSurfaceView(Context context) {
        super(context);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // exit program when key back is entered
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (SDKPort.getInstance().doExit()) {
                return true;
            }

            try {
                InputStream input = this.getResources().getAssets()
                        .open("quit.xml");
                Document doc = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder().parse(input);
                Element root = doc.getDocumentElement();
                String quit_dialog_title = root
                        .getAttribute("quit_dialog_title");
                String quit_dialog_msg = root.getAttribute("quit_dialog_msg");
                String quit_dialog_confirm = root
                        .getAttribute("quit_dialog_confirm");
                String quit_dialog_cancle = root
                        .getAttribute("quit_dialog_cancle");
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getContext());
                builder.setTitle(quit_dialog_title);
                builder.setMessage(quit_dialog_msg);
                OnConfirmListener confirmListener = new OnConfirmListener(
                        this.getContext());
                builder.setPositiveButton(quit_dialog_confirm, confirmListener);
                builder.setNegativeButton(quit_dialog_cancle,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
                input.close();
            } catch (Exception e) {
                // android.os.Process.killProcess(android.os.Process.myPid());
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getContext());
                builder.setTitle(R.string.quit_dialog_title);
                builder.setMessage(R.string.quit_dialog_msg);
                OnConfirmListener confirmListener = new OnConfirmListener(
                        this.getContext());
                builder.setPositiveButton(R.string.quit_dialog_confirm,
                        confirmListener);
                builder.setNegativeButton(R.string.quit_dialog_cancle,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    class OnConfirmListener implements DialogInterface.OnClickListener {
        Context context;

        OnConfirmListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
            WYDActivityManager.getInstance().onDestroy();
            WydDelegateManager.onDestroy();
            dialog.dismiss();
            Activity ac = (Activity) context;
            android.os.Process.killProcess(android.os.Process.myPid());
            ac.finish();
            System.exit(0);
        }

    }
}
