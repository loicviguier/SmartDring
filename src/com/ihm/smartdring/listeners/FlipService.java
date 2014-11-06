package com.ihm.smartdring.listeners;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

public class FlipService extends Service implements SensorEventListener {
	private static final String TAG = "FlipService";

	private int userSetting;
	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor myMagnetic;
    private AudioManager myAudioManager;
    private float[] accelerometerMatrix = new float[3];
	private float[] magneticsMatrix = new float[3];
	private float[] mOrientation = new float[3];
	private float[] mRotationM = new float[9];
    private float pitch;
    private float roll;
    private boolean compassInitialized;
    private boolean accelerometerInitialized;

    
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "started");
		
		myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		myMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	    mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    
		compassInitialized = false;
		accelerometerInitialized = false;
		userSetting = myAudioManager.getRingerMode();
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this, myMagnetic, SensorManager.SENSOR_DELAY_NORMAL);
		
		// We want this service to continue running until it is explicitly
        // stopped, so return sticky.
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		mSensorManager.unregisterListener(this);
		
    	myAudioManager.setRingerMode(userSetting);
		
		Log.d(TAG, "destroyed");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			System.arraycopy(event.values, 0, accelerometerMatrix, 0, 3);
			accelerometerInitialized = true;
		}
	    else {
	    	System.arraycopy(event.values, 0, magneticsMatrix, 0, 3);
	    	compassInitialized = true;
	    }
		
		if (accelerometerInitialized && compassInitialized) {
			
			if(!SensorManager.getRotationMatrix(mRotationM, null,
					accelerometerMatrix, magneticsMatrix)) {
				Log.e(TAG, "failure of rotation matrix");
				return;
			}
			
			SensorManager.getOrientation(mRotationM, mOrientation);
			pitch = Math.round(Math.toDegrees(mOrientation[1]));
			roll = Math.round(Math.toDegrees(mOrientation[2]));
			
			Log.i(TAG, "pitch : " + pitch + ", roll : " + roll);
			
			if (Math.abs(pitch) < 20 && Math.abs(roll)> 160) {
				Log.i(TAG, "TURNING RING SILENT, SHUTTING DOWN SERVICE");
				myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				stopSelf();
			}
	    }
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
}
