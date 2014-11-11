package com.ihm.smartdring;

import com.ihm.smartdring.listeners.AmbientVolumeDetectorService;
import com.ihm.smartdring.listeners.WalkDetectorService;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SeekBar;


public class ProfileSetupActivity extends Activity {
	// SharedPreferences elements
	private final String tagApplicationIsOn = "com.ihm.smartdring.tagapplicationison";
	private final String tagChosenProfile = "com.ihm.smartdring.tagchosenprofile";
	private final String tagFlipIsOn = "com.ihm.smartdring.tagflipison";
	private final String tagAmbientVolumeIsOn = "com.ihm.smartdring.tagambientvolumeison";
	private final String tagMaxAuthorizedVolume = "com.ihm.smartdring.tagmaxauthorizedvolume";
	private SharedPreferences settings;
	private Editor editor;
	
	// Views
	private ListView profileSetupListView = null;
	private SeekBar profileSetupSeekBar = null;
	private ProfileSetupListAdapter adapter = null;
	
	// Intents
	private Intent walkDetectorService = null;
	private Intent ambientVolumeDetectorService = null;
	
	// Data
	private ProfilesList profiles = null;
	private int selectedItemID = -1;
	private boolean[] switchSetupItemState = new boolean[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // SharedPreferences elements
        this.settings = PreferenceManager.getDefaultSharedPreferences(this);
        this.editor = settings.edit();
        
        // Intents
        this.walkDetectorService = new Intent(this, WalkDetectorService.class);
		this.ambientVolumeDetectorService = new Intent(this, AmbientVolumeDetectorService.class);
        
        setContentView(R.layout.profile_setup_activity);
        this.profileSetupListView = (ListView) findViewById(R.id.listViewSetupProfile);
        this.profileSetupSeekBar = (SeekBar) findViewById(R.id.seekBarVolume);
        
        this.profiles = new ProfilesList();
        this.profiles.loadProfilesList();
        
        Bundle bunble  = this.getIntent().getExtras();
        int itemID = bunble.getInt("itemID", -1);
        
        if (itemID != -1) {
        	this.selectedItemID = itemID;
        } else {
        	this.selectedItemID = profiles.getProfiles().size() -1;
        }
        
        Profile setupProfile = profiles.getProfiles().get(selectedItemID);
		
		this.setTitle(setupProfile.getName());
		
		this.profileSetupSeekBar.setMax(100);
		this.profileSetupSeekBar.setProgress(setupProfile.getVolume());
		
        boolean[] selectedItemState = {
        		setupProfile.getAmbiantSound(), 
        		setupProfile.getWalkingAction(), 
        		setupProfile.getFlipToSilence()};
        
        this.switchSetupItemState = selectedItemState;
        
        this.adapter = new ProfileSetupListAdapter(this, selectedItemState);
        this.profileSetupListView.setAdapter(adapter);
        
        this.profileSetupSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				editor.putInt(tagMaxAuthorizedVolume, 
						profiles.getProfiles().get(selectedItemID).getVolume());
				editor.commit();
				profiles.saveProfilesList();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				profiles.getProfiles().get(selectedItemID).setVolume(progress);
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.profile_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//    	switch(item.getItemId()) {
//			case R.id.menu_discard_profile:
//				this.setupProfile = profiles.getProfiles().remove(selectedItemID);
//				profiles.saveProfilesList();
//				Intent returnIntent = new Intent();
//				setResult(1, returnIntent);
//				finish();
//				return true;
//			default:
//				return super.onOptionsItemSelected(item);
//    	}
    	return super.onOptionsItemSelected(item);
    }
    
    public void switchSetupItemClick(View v) {
    	int position = (Integer) v.getTag();
    	
    	this.switchSetupItemState[position] = (! this.switchSetupItemState[position]);
    	
    	switch (position) {
    		case 0:
    			this.profiles.getProfiles().get(selectedItemID).setAmbiantSound(this.switchSetupItemState[position]);
    			ambientSound(this.switchSetupItemState[position]);
    			break;
    		case 1:
    			this.profiles.getProfiles().get(selectedItemID).setWalkingAction(this.switchSetupItemState[position]);
    			walkingAction(this.switchSetupItemState[position]);
    			break;
    		case 2:
    			this.profiles.getProfiles().get(selectedItemID).setFlipToSilence(this.switchSetupItemState[position]);
    			flipToSilence(this.switchSetupItemState[position]);
    			break;
    		default:
    			break;
    	}
    	
    	this.profiles.saveProfilesList();
		adapter.notifyDataSetChanged();
    }
    
    private void ambientSound(boolean activate) {
    	if(settings.getBoolean(tagApplicationIsOn, false) &&
    			settings.getInt(tagChosenProfile, -1) == selectedItemID)
	    	if (activate) {
	    		stopService(ambientVolumeDetectorService);
	    		startService(ambientVolumeDetectorService);
	    		editor.putBoolean(tagAmbientVolumeIsOn, true);
				editor.commit();
	    	}
	    	else {
	    		stopService(ambientVolumeDetectorService);
	    		editor.putBoolean(tagAmbientVolumeIsOn, false);
				editor.commit();
	    	}
    }
    
    private void walkingAction(boolean activate) {
    	if(settings.getBoolean(tagApplicationIsOn, false) &&
    			settings.getInt(tagChosenProfile, -1) == selectedItemID)
	    	if (activate) {
	    		stopService(walkDetectorService);
	    		startService(walkDetectorService);
	    	}
	    	else {
	    		stopService(walkDetectorService);
	    	}
    }
    
    private void flipToSilence(boolean activate) {
    	if(settings.getBoolean(tagApplicationIsOn, false) &&
    			settings.getInt(tagChosenProfile, -1) == selectedItemID) {
	    	editor.putBoolean(tagFlipIsOn, activate);
			editor.commit();
    	}
    	else
    		Log.d("ProfileSetupActivity", "application is off or we're off-profile");
    }
    
}
