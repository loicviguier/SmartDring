package com.ihm.smartdring;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Switch;


public class ProfileSetupActivity extends Activity {
	private ListView profileSetupListView = null;
	private ProfileSetupListAdapter adapter = null;
	
	private ProfilesList profiles = null;
	private int selectedItemID = -1;
	private boolean[] switchSetupItemState = new boolean[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup_activity);
        
        this.profileSetupListView = (ListView) findViewById(R.id.listViewSetupProfile);
        
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
		
        boolean[] selectedItemState = {
        		setupProfile.getAmbiantSound(), 
        		setupProfile.getWalkingAction(), 
        		setupProfile.getFlipToSilence()};
        
        this.switchSetupItemState = selectedItemState;
        
        this.adapter = new ProfileSetupListAdapter(this, selectedItemState);
        this.profileSetupListView.setAdapter(adapter);
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
    			break;
    		case 1:
    			this.profiles.getProfiles().get(selectedItemID).setWalkingAction(this.switchSetupItemState[position]);
    			break;
    		case 2:
    			this.profiles.getProfiles().get(selectedItemID).setFlipToSilence(this.switchSetupItemState[position]);
    			break;
    		default:
    			break;
    	}
    	
    	this.profiles.saveProfilesList();
		adapter.notifyDataSetChanged();
    }
    
}
