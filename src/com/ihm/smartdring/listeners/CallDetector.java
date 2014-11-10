package com.ihm.smartdring.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallDetector extends BroadcastReceiver {
	private static final String TAG = "CallDetector";
	
	private final String tagApplicationIsOn = "com.ihm.smartdring.tagapplicationison";
	private final String tagFlipIsOn = "com.ihm.smartdring.tagflipison";
	private SharedPreferences settings;

	/**
	 * Actions to do when receiving a call
	 * Reacts to CALL_STATE_RINGING
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		
		this.settings = PreferenceManager.getDefaultSharedPreferences(context);
		
		boolean flipIsOn = settings.getBoolean(tagFlipIsOn, true);
		boolean activated = settings.getBoolean(tagApplicationIsOn, true);
		
		Intent service = new Intent(context, FlipService.class);
		Bundle extras = intent.getExtras();
		String phoneState = extras.getString(TelephonyManager.EXTRA_STATE);

		if(flipIsOn && activated)		
			if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
				Log.d(TAG, "Call detected, phone state : " + phoneState);
				context.startService(service);
			}
			else {
				Log.d(TAG, "Call is over or cancelled, phone state : " + phoneState);
				context.stopService(service);
			}
	}

}
