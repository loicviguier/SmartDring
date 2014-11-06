package com.ihm.smartdring.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallDetector extends BroadcastReceiver {
	private static final String TAG = "CallDetector";

	/**
	 * Actions to do when receiving a call
	 * Reacts to CALL_STATE_RINGING
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent service = new Intent(context, FlipService.class);
		Bundle extras = intent.getExtras();
		String phoneState = extras.getString(TelephonyManager.EXTRA_STATE);

		//TODO Retrieve activation state of the service
		
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
