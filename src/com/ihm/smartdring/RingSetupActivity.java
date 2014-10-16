package com.ihm.smartdring;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;


public class RingSetupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ring_setup_activity);
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
     * Action à mener lorsque l'utilisateur 
     * active ou désactive l'interrupteur
     * SMART MODE
     * @param view décrivant le contexte
     */
    public void onToggleSmartModeClicked(View view){
    	// From http://developer.android.com/guide/topics/ui/controls/togglebutton.html
    	boolean on = ((ToggleButton) view).isChecked();
        
        if (on) {
            // Enable function
        	
        	
        } else {
            // Disable function (if already started)
        	
        }
    }
    
    public void onToggleTimerModeClicked(View view){
    	// From http://developer.android.com/guide/topics/ui/controls/togglebutton.html
    	boolean on = ((ToggleButton) view).isChecked();
        
        if (on) {
            // Enable function
        	
        	// Activité : voir Service, TimerTask, AudioManager
        	//TODO
        	
        } else {
            // Disable function (if already started)
        	
        }
    }
}
