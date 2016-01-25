package com.example.gdataswitcher;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.widget.Toast;

public class TimeService1 extends Service {

	int sec1=0;
	int sec2=0;
	int count=0;
	PowerManager pm;
	WakeLock myWakeLock;
	// run on another Thread to avoid crash
	private Handler mHandler = new Handler();
	private Handler mHandler1 = new Handler();
	// timer handling
	private Timer mTimer;
    
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if(count==0){
		// TODO Auto-generated method stub
		sec1=intent.getIntExtra("secInterval", 0);
		sec2=intent.getIntExtra("secHowLong", 0);
		
		pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
		myWakeLock=pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
		if(myWakeLock!=null){
			myWakeLock.acquire();
		//	Toast.makeText(getApplicationContext(), "WakeLock is acquired",Toast.LENGTH_SHORT).show();

		}
		
		
		// cancel if already existed
				if(mTimer != null) {
					mTimer.cancel();
				} else {
					// recreate new
					mTimer = new Timer();
				}
				// schedule task
				mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 1000, sec1);
				Toast.makeText(getApplicationContext(),"Automatic 2G/3G Enabling Activated",Toast.LENGTH_SHORT).show();
				count++;
		}else
		{
			Toast.makeText(getApplicationContext(), "Service is already running",Toast.LENGTH_SHORT).show();

		}
		return START_STICKY;
}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mTimer.cancel();
		if(myWakeLock!=null){
			myWakeLock.release();
			//Toast.makeText(getApplicationContext(), "WakeLock is OFF",Toast.LENGTH_SHORT).show();
			myWakeLock=null;
		}
		Toast.makeText(getApplicationContext(), "Automatic 2G/3G Enabling Deactivated",Toast.LENGTH_SHORT).show();

	}
	private void setMobileDataEnabled(Context context, boolean enabled) {
	    final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    Class conmanClass = null;
		try {
			conmanClass = Class.forName(conman.getClass().getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Field iConnectivityManagerField = null;
		try {
			iConnectivityManagerField = conmanClass.getDeclaredField("mService");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    iConnectivityManagerField.setAccessible(true);
	    Object iConnectivityManager = null;
		try {
			iConnectivityManager = iConnectivityManagerField.get(conman);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Class iConnectivityManagerClass = null;
		try {
			iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Method setMobileDataEnabledMethod = null;
		try {
			setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    setMobileDataEnabledMethod.setAccessible(true);

	    try {
			setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
	}

	class TimeDisplayTimerTask extends TimerTask {

		@Override
		public void run() {
			// run on another thread
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					// display toast
					setMobileDataEnabled(getApplicationContext(),true);
					Toast.makeText(getApplicationContext(), "Mobile Data Enabled", Toast.LENGTH_LONG).show();
				}

			});
			
			mHandler1.postDelayed(new Runnable() {
				@Override
				public void run() {
					// display toast
					setMobileDataEnabled(getApplicationContext(),false);
					Toast.makeText(getApplicationContext(), "Mobile Data Disabled", Toast.LENGTH_LONG).show();
				}

			},sec2);
		}

	}
}