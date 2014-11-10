package com.ihm.smartdring;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
        this.selectedItemID = bunble.getInt("itemID", -1);
        
        if(this.selectedItemID != -1) {
        	this.setupProfile = profiles.getProfiles().get(this.selectedItemID);
        } else {
        	int lastElement = profiles.getProfiles().size() -1;
        	this.setupProfile = profiles.getProfiles().get(lastElement);
        }
        
        this.setTitle(setupProfile.getName());
        
        boolean[] exemple = {
        		setupProfile.getAmbiantSound(),
        		setupProfile.getWalkingAction(), 
        		setupProfile.getAmbiantSound()};
        
        ProfileSetupListAdapter adapter = new ProfileSetupListAdapter(this, exemple);
        this.profileSetupListView.setAdapter(adapter);
        
        this.profileSetupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ring_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
    	switch(item.getItemId()) {
			case R.id.menu_discard_profile:
				if(this.selectedItemID != -1) {
		        	this.setupProfile = profiles.getProfiles().remove(this.selectedItemID);
		        } else {
		        	int lastElement = profiles.getProfiles().size() - 1;
		        	this.setupProfile = profiles.getProfiles().remove(lastElement);
		        }
				profiles.saveProfilesList();
				
				//ProfilesListActivity parent = (ProfilesListActivity) this.getParent();
				//parent.refreshProfilesList();
				//Intent returnIntent = new Intent();
				//setResult(RESULT_OK, returnIntent);
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
    	}
    }
    
    
    
}
