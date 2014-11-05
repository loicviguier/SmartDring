package com.ihm.smartdring.listeners;

import java.io.IOException;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
//For documentation on MediaRecorder, see
// http://developer.android.com/reference/android/media/MediaRecorder.html
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.IBinder;

/**
 * For information and documentation see
 * http://developer.android.com/reference/android/app/Service.html
 * Listen to the mic and adjusts the volume
 * @author Guillaume
 */
public class AmbientVolumeDetectorService extends Service {
	private NotificationManager mNM;
	private MediaRecorder myRecorder;
	private Handler myHandler;
	private Runnable myRunnable = new Runnable() {
		@Override
		public void run() {
			//TODO retrieve the value of activated
			boolean activated = false;
			long delay = 10000;
			
			// While we are active
			if(activated) {
				startRecorder();
				//TODO Evaluate ambient volume and adjusts
				myRecorder.stop();
				
				// Repetition of the task
				myHandler.postDelayed(myRunnable, delay);
			}
			else {
				// Get out of the loop properly
				myHandler.removeCallbacks(this);
				// and stops the service
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
		
		initializeRecorder();
        
        myHandler.post(myRunnable);
		
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
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
			//TODO
			
		}
        myRecorder.start();
	}
	
}
