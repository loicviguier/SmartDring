package com.ihm.smartdring;

import java.util.Timer;
import java.util.TimerTask;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

public class TimerService extends IntentService {
	public final static String TIME_SET = "com.ihm.smartdring.TIME_SET";

	public TimerService() {
		super("RingSettingsTimersIntentService");
	}

	public void onStart(Intent intent, int startId){
		
		// Retrieve user's time set
		long userSetting = intent.getLongExtra(TIME_SET, 0);
		Log.i("info", "userSetting : " + userSetting);
		
		// See http://developer.android.com/reference/android/media/AudioManager.html
		// for AudioManager info
		AudioManager soundProfile = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		// Silent mode engaged
		soundProfile.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		
		// Until the end of the timer : 
		TimerTask task = new TimerTask(){
			public void run(){
				Log.i("info", "End of TimerService");
				AudioManager soundProfile = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				soundProfile.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			}
		};
		Timer t = new Timer("RingSettingsTimer", false);
		
		t.schedule(task, userSetting);
		
		stopSelf();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		onStart(intent, 0);
        return null;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		onStart(intent, 0);
		
	}

}
