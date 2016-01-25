package com.example.gdataswitcher;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.widget.Toast;

public class TimeService extends Service {
	WifiManager wifi;
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
		// TODO Auto-generated method stub
		if(count==0){
		
		sec1=intent.getIntExtra("secInterval", 0);
		sec2=intent.getIntExtra("secHowLong", 0);
	
		
		pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
		myWakeLock=pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
		if(myWakeLock!=null){
		myWakeLock.acquire();
		//Toast.makeText(getApplicationContext(), "WakeLock is acquired",Toast.LENGTH_SHORT).show();

		}
		 wifi=(WifiManager)getSystemService(Context.WIFI_SERVICE);
			if(mTimer != null) {
				mTimer.cancel();
			} else {
				// recreate new
				mTimer = new Timer();
			}
			// schedule task
			mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 1000, sec1);
			Toast.makeText(getApplicationContext(), "Automatic Wifi Enabling is Activated",Toast.LENGTH_SHORT).show();
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
			//Toast.makeText(getApplicationContext(), "WakeLock is Released",Toast.LENGTH_SHORT).show();
			myWakeLock=null;
		}
		Toast.makeText(getApplicationContext(), "Automatic Wifi Enabling Deactivated",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCreate() {
		// cancel if already existed
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
					if(wifi.isWifiEnabled()==false){
						wifi.setWifiEnabled(true);
		Toast.makeText(getApplicationContext(), "Wifi Turned ON",Toast.LENGTH_SHORT).show();
					}
				}

			});
			
			mHandler1.postDelayed(new Runnable() {
				@Override
				public void run() {
					// display toast
					if(wifi.isWifiEnabled()==true){
						wifi.setWifiEnabled(false);
		    Toast.makeText(getApplicationContext(), "Wifi Turned OFF",Toast.LENGTH_SHORT).show();
					}
				}

			},sec2);
		}

	}
}