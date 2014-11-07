package com.ihm.smartdring.listeners;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Detects the user's activity (service). For more information, see
 * http://developer.android.com/training/location/activity-recognition.html
 * Since the example does what we wanted it to do, the code is the same
 */
public class ActivityRecognitionIntentService extends IntentService {
	private static final String TAG = "ActivityRecognitionIntentService";
	
	public ActivityRecognitionIntentService() {
		super("ActivityRecognitionIntentService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "incoming intent");
		
		// If the incoming intent contains an update
        if (ActivityRecognitionResult.hasResult(intent)) {
        	Log.d(TAG, "Computing result");
            // Get the update
            ActivityRecognitionResult result =
                    ActivityRecognitionResult.extractResult(intent);
            // Get the most probable activity
            DetectedActivity mostProbableActivity =
                    result.getMostProbableActivity();
            /*
             * Get the probability that this activity is the
             * the user's actual activity
             */
            int confidence = mostProbableActivity.getConfidence();
            /*
             * Get an integer describing the type of activity
             */
            int activityType = mostProbableActivity.getType();
            Log.d(TAG, "Activity  : " + getNameFromType(activityType)
            		+ " = " + confidence + "%");
            /*
             * At this point, you have retrieved all the information
             * for the current update. You can display this
             * information to the user in a notification, or
             * send it to an Activity or Service in a broadcast
             * Intent.
             */
            if (confidence > 65) {
            	switch(activityType) {
                case DetectedActivity.ON_BICYCLE:
                case DetectedActivity.ON_FOOT:
                	// Increase volume
                	//TODO retrieve max authorized volume
                	
                	break;
                case DetectedActivity.STILL:
                case DetectedActivity.UNKNOWN:
                case DetectedActivity.TILTING:
                case DetectedActivity.IN_VEHICLE:
                	// Set the user's préférences back
                	//TODO Set the user's préférences back
                	
                	break;
            	}
            }
            
        }
    }
	
	/**
     * Map detected activity types to strings
     *@param activityType The detected activity type
     *@return A user-readable name for the type
     */
    private String getNameFromType(int activityType) {
        switch(activityType) {
            case DetectedActivity.IN_VEHICLE:
                return "in_vehicle";
            case DetectedActivity.ON_BICYCLE:
                return "on_bicycle";
            case DetectedActivity.ON_FOOT:
                return "on_foot";
            case DetectedActivity.STILL:
                return "still";
            case DetectedActivity.UNKNOWN:
                return "unknown";
            case DetectedActivity.TILTING:
                return "tilting";
        }
        return "unknown";
    }


}
