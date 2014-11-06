package com.ihm.smartdring;

import com.ihm.smartdring.listeners.TimerService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;


public class ProfileSetupActivity extends Activity {
	public final static String TIME_SET = "com.ihm.smartdring.TIME_SET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup_activity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ring_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * Action � mener lorsque l'utilisateur 
     * active ou d�sactive l'interrupteur
     * SMART MODE
     * @param view d�crivant le contexte
     */
    public void onToggleSmartModeClicked(View view){
    	// From http://developer.android.com/guide/topics/ui/controls/togglebutton.html
    	boolean on = ((Switch) view).isChecked();
        
        if (on) {
            // Enable function
        	
        	
        } else {
            // Disable function (if already started)
        	
        }
    }
    
    public void onToggleTimerModeClicked(View view){
    	// From http://developer.android.com/guide/topics/ui/controls/togglebutton.html
    	boolean on = ((Switch) view).isChecked();
        
    	
        if (on) {
            // Enable function
        	
        	Intent timer = new Intent(this, TimerService.class);
        	// Get the time set by the user
        	long timeSet = 5000;
            timer.putExtra(TIME_SET, timeSet);
            // Launch service
        	startService(timer);
        	
        } else {
            // Disable function (if already started)
        	
        }
    }
}
