package org.cocos2dx.hellolua;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import wyd.adapter.WydExtenderBase;

//import com.tencent.tersafe2.TP2Sdk;


public class DandandaoUtils extends WydExtenderBase{
	private MediaPlayer player;
	private SurfaceView sv;
	private View viewBasic;
	public DandandaoUtils(long paramLong) {
		super(paramLong);
	}

	public String getFreeSpase(String paramString) {
		for (File localFile = new File(paramString);; localFile = localFile
				.getParentFile())
			if ((localFile.exists())
					|| (localFile.getAbsolutePath().equals("/")))
				return Long.toString(localFile.getFreeSpace());
	}

	// 实时获取CPU当前频率（单位KHZ）
	public String getCurCpuFreq(String paramString) {
		Log.v("sss", "wwwwww");
		String result = "N/A";
		try {
			FileReader fr = new FileReader(
					"/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			result = text.trim();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 获取CPU平台
	public String getCPUPlatform(String paramString) {
		String board_platform = "";
		Process p = null;
		try {
			p = new ProcessBuilder("/system/bin/getprop", "ro.board.platform")
					.redirectErrorStream(true).start();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line = "";
			while ((line = br.readLine()) != null) {
				board_platform = line;
			}
			p.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return board_platform;
	}

	// 播放logo
	public void playLogo(final String paramString) {
		Log.v("SDKLOG", "play logo:"+paramString);
		HelloLua.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				playLogo2(paramString);					
			}
		});
		

	}

	public void playLogo2(String path) {	
		
		viewBasic = ((ViewGroup) HelloLua.getActivity().findViewById(android.R.id.content)).getChildAt(0);
		sv = new MySurfaceView(HelloLua.getActivity());
		sv.setZOrderOnTop(true);
		((MySurfaceView) sv).setUtils(this);
		HelloLua.getActivity().utils = this;
		((ViewGroup) viewBasic).addView(sv);
		player = new MediaPlayer();
		AssetFileDescriptor afd;
		path=path.substring(7,path.length());
		Log.v("SDKLOG","the path:"+path);
		try {
			afd = HelloLua.getActivity().getAssets().openFd(path);//"wydLogo.mp4"
			Log.v("SDKLOG", "play Movie8:");
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			player.prepareAsync();
			// player.start();
			player.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					player.setDisplay(sv.getHolder());
					mp.start();
					Log.v("SDKLOG", "this is do star");
				}
			});
			player.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer arg0) {
					Log.v("SDKLOG", "this is do completion");
					logoOver();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void logoStop(){
		if(player != null){
			player.stop();
		}
	}
	
	public void logoOver(){
		Log.v("SDKLOG", "logoOver");
		if(player==null){return;}
		Log.v("SDKLOG", "logoOver");
		player.release();
		player = null;
		((ViewGroup) viewBasic).removeView(sv);
		HelloLua.getActivity().runOnGLThread(new Runnable() {
			@Override
			public void run() {
				callbackByMethodName(DandandaoUtils.this.m_cppObjectAddr, "playLogo", "");
				// TODO Auto-generated method stub
				// callbackByMethodName(AsynPassport.this.m_cppObjectAddr, name,
				// params);
			}
		});
	}
	
	// 登入MTP的
	public void loginMTP(String paramString) {
		String roleId = "";
		String serverId = "";
		JSONObject info = null;
		try {
			info = new JSONObject(paramString);
			if (info.has("serverId")) {
				serverId = info.getString("serverId");
			}
			if (info.has("roleId")) {
				roleId = info.getString("roleId");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// 用户登录游戏时调用
		// TP2Sdk.onUserLogin(1, Integer.valueOf(serverId), "all_openId",
		// roleId);
	}

	// 获取内存
	public String getTotalMemory(String paramString) {
		String str1 = "/proc/meminfo";// 系统内存信息文件
		String str2;
		String[] arrayOfString;
		long initial_memory = 0;

		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

			arrayOfString = str2.split("\\s+");
			for (String num : arrayOfString) {
				Log.i(str2, num + "\t");
			}

			initial_memory = Integer.valueOf(arrayOfString[1]).intValue();// 获得系统总内存，单位是KB，乘以1024转换为Byte
			localBufferedReader.close();

		} catch (IOException e) {
		}
		return Long.toString(initial_memory);

	}

	// 获取build信息
	public void setDebugState(String paramString) {
		saveLog();
	}

	// 获取build信息
	public void getTotalInfo() {
		String str1 = "/system/build.prop";
		String str2 = "";
		Log.e("jk", "getTotalInfo");
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			while ((str2 = localBufferedReader.readLine()) != null) {
				Log.e("jk", "" + str2);
			}
		} catch (IOException e) {
		}
	}

	public String getVersion() {
		return "1.0.0";
	}

	// 保存日志
	private void saveLog() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			String filePath = "/sdcard/ddd_log/";
			String fileName = "dandandao_log_android.txt";
			createFile(filePath, fileName);

			try {
				Runtime.getRuntime().exec("logcat -c");
				ArrayList<String> commandLine = new ArrayList<String>();
				commandLine.add("logcat");
				commandLine.add("-b");
				commandLine.add("main");
				commandLine.add("-b");
				commandLine.add("system");
				commandLine.add("-b");
				commandLine.add("radio");
				// commandLine.add( "-b");
				// commandLine.add( "events");
				commandLine.add("-v");
				commandLine.add("time");
				commandLine.add("-f");
				commandLine.add(filePath + fileName);
				Process process = Runtime.getRuntime().exec(
						commandLine.toArray(new String[commandLine.size()]));

				Log.d("saveLog", "LogStart ");
			} catch (IOException e) {
			}
		}
	}

	// 创建文件
	public File createFile(String filePath, String fileName) {
		// 生成文件夹之后，再生成文件，不然会出错

		String strFilePath = filePath + fileName;
		File file = new File(strFilePath);
		try {
			if (!file.exists()) {
				Log.d("saveLog", "Create the file 1:" + strFilePath);
				makeFilePath(filePath, fileName);
				file.getParentFile().mkdirs();
				file.createNewFile();
			} else if (file.exists()) {
				Log.d("saveLog", "Create the file 2:" + strFilePath);
				file.delete();
				file.createNewFile();
			}
		} catch (Exception e) {
			Log.e("saveLog", "Error on write File:" + e);
		}
		return file;
	}

	// 生成文件
	public File makeFilePath(String filePath, String fileName) {
		File file = null;
		makeRootDirectory(filePath);
		try {
			file = new File(filePath + fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	// 生成文件夹
	public static void makeRootDirectory(String filePath) {
		File file = null;
		try {
			file = new File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {
			Log.i("saveLog:", e + "");
		}
	}
}