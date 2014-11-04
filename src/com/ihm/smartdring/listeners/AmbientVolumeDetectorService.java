package com.ihm.smartdring.listeners;

import java.io.IOException;

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
	
	private MediaRecorder myRecorder;
	private Handler myHandler;
	private Runnable myRunnable = new Runnable() {
		@Override
		public void run() {
			boolean activated = false;
			long delay = 10000;
			
			// while we are active
			if(activated) {
				startRecorder();
				//TODO Evaluate ambient volume and adjusts
				myRecorder.stop();
				
				// Repetition of the task
				myHandler.postDelayed(myRunnable, delay);
			}
			else {
				myHandler.removeCallbacks(this);
			}
			
	    }
	};

	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
	// For documentation on MediaRecorder, see
	// http://developer.android.com/reference/android/media/MediaRecorder.html
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		myHandler = new Handler();
		myRecorder = new MediaRecorder();
        myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        myRecorder.setOutputFile("/dev/null");
        
        myHandler.post(myRunnable);
		
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }
	
	
	
	
	private void startRecorder(){
		try {
			myRecorder.prepare();
		} catch (IllegalStateException e) {
			// myRecorder has not been initiated properly
			//TODO
			e.printStackTrace();
		} catch (IOException e) {
			// Mic is not accessible
			//TODO
			e.printStackTrace();
		}
        myRecorder.start();
	}
	
}
