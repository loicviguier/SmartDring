package com.ihm.smartdring.listeners;

import java.io.IOException;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
//For documentation on MediaRecorder, see
// http://developer.android.com/reference/android/media/MediaRecorder.html
import android.util.Log;

/**
 * For information and documentation see
 * http://developer.android.com/reference/android/app/Service.html
 * Listen to the mic and adjusts the volume
 * @author Guillaume
 */
public class AmbientVolumeDetectorService extends Service {
	private final int MILLISECONDS_IN_A_SECOND = 1000;
	private final int DELAY_IN_SECONDS = 10;
	private final int DELAY =
			MILLISECONDS_IN_A_SECOND * DELAY_IN_SECONDS;
	
	private final String tagAmbientVolumeIsOn = "com.ihm.smartdring.tagambientvolumeison";
	private final String tagMaxAuthorizedVolume = "com.ihm.smartdring.tagmaxauthorizedvolume";
	
	private SharedPreferences settings;
	private static final String TAG = "AmbientVolumeDetector";
	private NotificationManager mNM;
	private MediaRecorder myRecorder;
	private Handler myHandler;
	private int maxAmplitude;
	private AudioManager myAudioManager;
	private int userRingerVolume;
	
	private Runnable myRunnable = new Runnable() {
		@Override
		public void run() {
			boolean activated = settings.getBoolean(tagAmbientVolumeIsOn, true);
			
			// While we are active
			if(activated) {
				maxAmplitude = myRecorder.getMaxAmplitude();
				
				int maxSystemVolume =
						myAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
				float maxAuthorizedVolume =
						settings.getInt(tagMaxAuthorizedVolume, 50);
				maxAuthorizedVolume = Math.round(
						maxSystemVolume * maxAuthorizedVolume / 100);
				int newVolume = Math.round((maxAmplitude / 32767) * maxAuthorizedVolume);
				
				newVolume = newVolume > (int) maxSystemVolume ?
						maxSystemVolume : newVolume;
				// Volume is at least = 1
				newVolume = newVolume == 0 ?
						1 : newVolume;
				
				
				Log.d(TAG, "[Service] : Tick, amplitude = "
						+ maxAmplitude + ", new volume = " + newVolume);
				
				myAudioManager.setStreamVolume(
						AudioManager.STREAM_RING, newVolume, 0);
				
				// Repetition of the task
				myHandler.postDelayed(myRunnable, DELAY);
			}
			else {
				// Stops the service
				stopSelf();
			}
			
	    }
	};

	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	

	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		myHandler = new Handler();
		myAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		userRingerVolume =
				myAudioManager.getStreamVolume(AudioManager.STREAM_RING);
		settings = PreferenceManager.getDefaultSharedPreferences(this);
		
		initializeRecorder();
		startRecorder();
        
        myHandler.post(myRunnable);
        
        Log.d(TAG, "[ServiceAmbientVolume] : Runnable lancé. Booléen = "
        		+ settings.getBoolean(tagAmbientVolumeIsOn, true));
        
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }
	
	
	@Override
    public void onDestroy() {
		myRecorder.stop();
		myHandler.removeCallbacks(myRunnable);
		myAudioManager.setStreamVolume(
				AudioManager.STREAM_RING, userRingerVolume, 0);
	}
	
	
	private void initializeRecorder() {
		// For documentation on MediaRecorder, see
		// http://developer.android.com/reference/android/media/MediaRecorder.html
		myRecorder = new MediaRecorder();
        myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        myRecorder.setOutputFile("/dev/null");
	}
	
	
	private void startRecorder() {
		try {
			myRecorder.prepare();
		} catch (IllegalStateException e) {
			// myRecorder has not been initialized properly
			initializeRecorder();
			e.printStackTrace();
			startRecorder();
		} catch (IOException e) {
			// Mic is not accessible
			showErrorNotification();
			stopSelf();
		}
        myRecorder.start();

        // Returns 0 for the first call
        myRecorder.getMaxAmplitude();
	}
	
	
	private void showErrorNotification() {
		int notifID = 1;

        // Set the icon, scrolling text and timestamp
        NotificationCompat.Builder mBuilder =
        		new NotificationCompat.Builder(this);
        
        mBuilder.setSmallIcon(com.ihm.smartdring.R.drawable.ic_launcher);
        mBuilder.setContentTitle("Erreur");
        mBuilder.setContentText("Le micro est inaccessible.");

        // Send the notification.
        mNM.notify(notifID, mBuilder.build());
	}
	
}
