package com.ihm.smartdring;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class ProfileSetupActivity extends Activity {
	private ListView profileSetupListView = null;
	private ProfilesList profiles = null;
	private Profile setupProfile = null;
	private int selectedItemID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup_activity);
        
        this.profileSetupListView = (ListView) findViewById(R.id.listViewSetupProfile);
        this.profiles = new ProfilesList(this.getApplicationContext());
        this.profiles.loadProfilesList();
        
        Bundle bunble  = this.getIntent().getExtras();
        int itemID = bunble.getInt("itemID", -1);
        
        if (itemID != -1) {
        	this.selectedItemID = itemID;
        } else {
        	this.selectedItemID = profiles.getProfiles().size() -1;
        }
        
        this.setupProfile = profiles.getProfiles().get(selectedItemID);
        this.setTitle(setupProfile.getName());
        
        ProfileSetupListAdapter adapter = new ProfileSetupListAdapter(this, selectedItemID);
        this.profileSetupListView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.profile_setup, menu);
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
    
}
