package com.ihm.smartdring;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;

public class TimerService extends Service {

	public void onStart(Intent intent, int startId){
		
		// See http://developer.android.com/reference/android/media/AudioManager.html
		// for AudioManager info
		final AudioManager soundProfile = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		final int userSetting = soundProfile.getRingerMode();
		
		// Silent mode engaged
		soundProfile.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		
		// Until the end of the timer : 
		TimerTask task = new TimerTask(){
			public void run(){
				soundProfile.setRingerMode(userSetting);
			}
		};
		Timer t = new Timer("RingSettingsTimer", false);
		
		t.schedule(task, System.currentTimeMillis(), 5000);
		
		stopSelf();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		onStart(intent, 0);
        return null;
	}

}
