package com.ihm.smartdring.listeners;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.ActivityRecognitionClient;


/**
 * Detects the user's activity (fragment). For more information, see
 * http://developer.android.com/training/location/activity-recognition.html
 * Since the example does what we wanted it to do, the code is the same
 */
public class WalkDetectorService extends Service 
			implements GooglePlayServicesClient.ConnectionCallbacks,
			GooglePlayServicesClient.OnConnectionFailedListener {
	private static final String TAG = "WalkDetector";

	// Constants that define the activity detection interval
	public static final int MILLISECONDS_PER_SECOND = 1000;
	public static final int DETECTION_INTERVAL_SECONDS = 20;
	public static final int DETECTION_INTERVAL_MILLISECONDS =
	        MILLISECONDS_PER_SECOND * DETECTION_INTERVAL_SECONDS;

    public enum REQUEST_TYPE {START, STOP}
	
	private Context mContext;
	private static ActivityRecognitionClient mActivityRecognitionClient;
	private static PendingIntent mActivityRecognitionPendingIntent;
	// Flag that indicates if a request is underway.
    private boolean mInProgress;
    private REQUEST_TYPE mRequestType;
	
    
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "Detector service started");
		
		mContext = getBaseContext();
		mActivityRecognitionClient =
                new ActivityRecognitionClient(mContext, this, this);
		
		Intent intentBis = new Intent(
                mContext, ActivityRecognitionIntentService.class);
		
		mActivityRecognitionPendingIntent =
                PendingIntent.getService(mContext, 0, intentBis,
                PendingIntent.FLAG_UPDATE_CURRENT);
		
		startUpdates();
		mInProgress = false;
		return START_STICKY;
	}

	
	@Override
	public void onDestroy() {
		stopUpdates();
		Log.d(TAG, "Service stopping");
	}


	@Override
	public void onConnected(Bundle arg0) {
		
		switch (mRequestType) {
        	case START :
				/*
		         * Request activity recognition updates using the preset
		         * detection interval and PendingIntent. This call is
		         * synchronous.
		         */
		        mActivityRecognitionClient.requestActivityUpdates(
		                DETECTION_INTERVAL_MILLISECONDS,
		                mActivityRecognitionPendingIntent);
		        break;
        	case STOP : 
        		mActivityRecognitionClient.removeActivityUpdates(
                        mActivityRecognitionPendingIntent);
        		break;
		}
        /*
         * Since the preceding call is synchronous, turn off the
         * in progress flag and disconnect the client
         */
        mInProgress = false;
        mActivityRecognitionClient.disconnect();
		
	}

	
	@Override
	public void onDisconnected() {
		// Turn off the request flag
        mInProgress = false;
        // Delete the client
        mActivityRecognitionClient = null;
	}
	
	
	// Implementation of OnConnectionFailedListener.onConnectionFailed
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Turn off the request flag
        mInProgress = false;

        // Inform the user
        
    }
    
    
    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.
                        isGooglePlayServicesAvailable(this);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // Continue
            return true;
        // Google Play services was not available for some reason
        } else {
            // Error case
            return false;
        }
    }
    
    
    public void startUpdates() {
    	// Set the request type to START
        mRequestType = REQUEST_TYPE.START;
    	
        // Check for Google Play services
        if (!servicesConnected()) {
            return;
        }
        // If a request is not already underway
        if (!mInProgress) {
            // Indicate that a request is in progress
            mInProgress = true;
            // Request a connection to Location Services
            mActivityRecognitionClient.connect();
        } else {
            /*
             * A request is already underway. You can handle
             * this situation by disconnecting the client,
             * re-setting the flag, and then re-trying the
             * request.
             */
        	Log.e(TAG, "Start : A request is already underway.");
        }
    }
    
    
    /**
     * Turn off activity recognition updates
     *
     */
    public void stopUpdates() {
        // Set the request type to STOP
        mRequestType = REQUEST_TYPE.STOP;
        /*
         * Test for Google Play services after setting the request type.
         * If Google Play services isn't present, the request can be
         * restarted.
         */
        if (!servicesConnected()) {
            return;
        }
        // If a request is not already underway
        if (!mInProgress) {
            // Indicate that a request is in progress
            mInProgress = true;
            // Request a connection to Location Services
            mActivityRecognitionClient.connect();
        //
        } else {
            /*
             * A request is already underway. You can handle
             * this situation by disconnecting the client,
             * re-setting the flag, and then re-trying the
             * request.
             */
        	Log.e(TAG, "Stop : A request is already underway.");
        }
    }

    
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
