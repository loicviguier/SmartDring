package com.ihm.smartdring;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Switch;

import com.ihm.smartdring.listeners.AmbientVolumeDetectorService;
import com.ihm.smartdring.listeners.WalkDetectorService;

public class ProfilesListActivity extends Activity {
	private static final String TAG = "Profiles list";
	
	// SharedPreferences elements
	private final String tagApplicationIsOn = "com.ihm.smartdring.tagapplicationison";
	private final String tagFlipIsOn = "com.ihm.smartdring.tagflipison";
	private final String tagChosenProfile = "com.ihm.smartdring.tagchosenprofile";
	private SharedPreferences settings;
	private Editor editor;
	
	// Intents
	private Intent profileSetupActivity = null;
	private Intent walkDetectorService = null;
	private Intent ambientVolumeDetectorService = null;
	
	// Views elements
	private Switch profilesListSwitchActivate = null;
	private ListView profilesListView = null;
	private ArrayAdapter<String> adapter = null;
	
	private List<String> profilesListName;
	private ProfilesList profiles = null;
	private int selectedItemID = -1;
	
	
	@SuppressLint("InflateParams")
	private void showAlertDialogAddProfile() {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View profileAddAlertDialogView = inflater.inflate(R.layout.profiles_list_alert_dialog, null);
		AlertDialog.Builder profileAlertDialogBuilder = new AlertDialog.Builder(this);
		profileSetupActivity = new Intent(this, ProfileSetupActivity.class);
		
		profileAlertDialogBuilder.setView(profileAddAlertDialogView);
		profileAlertDialogBuilder.setTitle("Create a new profile");
		
		profileAlertDialogBuilder.setPositiveButton("Validate", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText profilesListAlertDialogEditText =
						(EditText) profileAddAlertDialogView.findViewById(R.id.editTextProfilesListAlertDialog);
				String profileName = profilesListAlertDialogEditText.getText().toString();
				
				if (! profileName.isEmpty()) {
					profiles.getProfiles().add(new Profile(profileName));
					profiles.saveProfilesList();
					profileSetupActivity.putExtra("itemID", -1);
					startActivityForResult(profileSetupActivity, 1);
				} else {
					showAlertDialogAddProfile();
				}
			}

		});

		profileAlertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}

		});
		
		profileAlertDialogBuilder.show();
	}
	
	
	@SuppressLint("InflateParams")
	private void showAlertDialogRenameProfile() {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View profileAddAlertDialogView = inflater.inflate(R.layout.profiles_list_alert_dialog, null);
		AlertDialog.Builder profileAlertDialogBuilder = new AlertDialog.Builder(this);
		
		profileAlertDialogBuilder.setView(profileAddAlertDialogView);
		profileAlertDialogBuilder.setTitle("Rename the profile");
		
		profileAlertDialogBuilder.setPositiveButton("Validate", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText profilesListAlertDialogEditText =
						(EditText) profileAddAlertDialogView.findViewById(R.id.editTextProfilesListAlertDialog);
				String newProfileName = profilesListAlertDialogEditText.getText().toString();
				
				if (! newProfileName.isEmpty()) {
					profiles.getProfiles().get(selectedItemID).setName(newProfileName);
					profiles.saveProfilesList();
					refreshProfilesList();
					selectedItemID = -1;
				} else {
					showAlertDialogRenameProfile();
				}
			}

		});

		profileAlertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				selectedItemID = -1;
				dialog.cancel();
			}

		});
		
		profileAlertDialogBuilder.show();
	}
	
	
	private void showPopupMenuProfilesItem(View view) {
		PopupMenu popupMenuProfileItem; 
		
		popupMenuProfileItem = new PopupMenu(this, view);
		popupMenuProfileItem.getMenuInflater().inflate(R.menu.popup_menu_profiles_list_item, 
				popupMenuProfileItem.getMenu());
		
		popupMenuProfileItem.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				switch(item.getItemId()) {
					case R.id.rename_item_popup_menu_profiles_list:
						showAlertDialogRenameProfile();
						return true;
					case R.id.modify_item_popup_menu_profiles_list: 
						profileSetupActivity.putExtra("itemID", selectedItemID);
						startActivity(profileSetupActivity);
						selectedItemID = -1;
						return true;
					case R.id.delete_item_popup_menu_profiles_list:
						profiles.getProfiles().remove(selectedItemID);
						profiles.saveProfilesList();
						refreshProfilesList();
						selectedItemID = -1;
						//TODO Update the chosen profile if he gets deleted
						return true;
					default:
						return true;
				}
				
			}
			
		});
		
		popupMenuProfileItem.show();
	}
	
	
	private void refreshProfilesList() {
		Log.d(TAG, "refreshing the list");
		profilesListName = new ArrayList<String>();
		for(int index = 0; index < profiles.getProfiles().size(); index++) {
			profilesListName.add(profiles.getProfiles().get(index).getName());
		}

		adapter = new ArrayAdapter<String>(ProfilesListActivity.this, android.R.layout.simple_list_item_single_choice, profilesListName);
		profilesListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
			case 1:
				refreshProfilesList();
				break;
			default:
				break;
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profiles_list_activity);
		
		this.settings = getPreferences(MODE_PRIVATE);
		this.editor = settings.edit();
		if (!settings.contains(tagApplicationIsOn))
			// Default value set on the first launch
			editor.putBoolean(tagApplicationIsOn, false);
		
		if (!settings.contains(tagChosenProfile))
			// Default value set on the first launch
			editor.putInt(tagChosenProfile, -1);
		
		this.profiles = new ProfilesList(this.getApplicationContext());
		this.profileSetupActivity = new Intent(this, ProfileSetupActivity.class);
		this.walkDetectorService = new Intent(this, WalkDetectorService.class);
		this.ambientVolumeDetectorService = new Intent(this, AmbientVolumeDetectorService.class);
		
		this.profilesListSwitchActivate = (Switch) findViewById(R.id.switchActivate);
		this.profilesListView = (ListView) findViewById(R.id.listViewProfiles);
		
		this.profiles.loadProfilesList();
		
		this.profilesListName = new ArrayList<String>();
		for(int index = 0; index < profiles.getProfiles().size(); index++) {
			this.profilesListName.add(profiles.getProfiles().get(index).getName());
		}
		
		this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, this.profilesListName);
		this.profilesListView.setAdapter(this.adapter);
		
		this.profilesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
				selectedItemID = position;
				showPopupMenuProfilesItem(view);
				return true;
			}
			
		});
		
		final int chosenProfile = settings.getInt(tagChosenProfile, -1);
		Log.d(TAG, "radio button " + chosenProfile + " to be set on startup");
		// Set the last user's choice of profile
		this.profilesListView.post(new Runnable() {
		    @Override
		    public void run() {
		    	profilesListView.setItemChecked(chosenProfile, true);
		    }
		});
		
		this.profilesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
				Log.d(TAG, "radio button " + position + " checked");
				// Update SharedPreferences
				editor.putInt(tagChosenProfile, position);
		        editor.commit();
				
				boolean activated = settings.getBoolean(tagApplicationIsOn, false);
				if (activated) {
					setProfile();
				}
			}
			
		});
		
		boolean activated = settings.getBoolean(tagApplicationIsOn, false);
		this.profilesListSwitchActivate.setChecked(activated);
		this.profilesListSwitchActivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// Update SharedPreferences
				editor.putBoolean(tagApplicationIsOn, isChecked);
		        editor.commit();
		        
				if (isChecked) {
					setProfile();
				}
				else {
					stopService(walkDetectorService);
					stopService(ambientVolumeDetectorService);
					editor.putBoolean(tagFlipIsOn, false);
					editor.commit();
				}

			}

		});
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		refreshProfilesList();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.profiles_list, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()) {
			case R.id.menu_add_profile:
				this.showAlertDialogAddProfile();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
	/**
	 * Boot services associated with a profile 
	 * if one is set (tagChosenProfile > -1)
	 */
	private void setProfile () {
		int profile = settings.getInt(tagChosenProfile, -1);
		
		if (profile != -1) {
			boolean ambientSound =
					profiles.getProfiles().get(profile).getAmbiantSound();
			boolean flipToSilent =
					profiles.getProfiles().get(profile).getFlipToSilence();
			boolean walkDetector = 
					profiles.getProfiles().get(profile).getWalkingAction();
			
			Log.d(TAG, "Ambient sound = " + ambientSound + ", flip = "
					+ flipToSilent + ", walk = " + walkDetector);
			
			if (ambientSound)
				startService(ambientVolumeDetectorService);
			if (walkDetector)
				startService(walkDetectorService);
			
			editor.putBoolean(tagFlipIsOn, flipToSilent);
				
		}
	}
}
