package com.ihm.smartdring;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class ProfileSetupListAdapter extends BaseAdapter {
	private ProfilesList profiles = null;
	private LayoutInflater inflater = null;
	private Context applicationContext = null;
	private int profileItemID = -1;
	
	private final String[] optionName = {
			"Ambiant vol.", 
			"Movement", 
			"Phone rotation"};
	
	private final Integer[] optionIconID = {
			R.drawable.ic_noise, 
			R.drawable.ic_walking, 
			R.drawable.ic_reverse_phone};
    
    public ProfileSetupListAdapter(Context context, int profileItemID) {
		super();
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.applicationContext = context;
		this.profileItemID = profileItemID;
		
		this.profiles = new ProfilesList(context);
		this.profiles.loadProfilesList();
		
		Profile setupProfile = profiles.getProfiles().get(profileItemID);
		Log.v("Names", setupProfile.getName());
		Log.v("Noise", setupProfile.getFlipToSilence()?"true":"false");
		Log.v("Walking", setupProfile.getWalkingAction()?"true":"false");
		Log.v("Flip", setupProfile.getFlipToSilence()?"true":"false");
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null)
			convertView = inflater.inflate(R.layout.profile_setup_item, parent, false);
		
		ImageView iconSetupItem = (ImageView) convertView.findViewById(R.id.iconSetupItem);
		TextView textSetupItemp = (TextView) convertView.findViewById(R.id.textSetupItem);
		Switch switchSetupItem = (Switch) convertView.findViewById(R.id.switchSetupItem);
		
		iconSetupItem.setImageResource(optionIconID[position]);
		textSetupItemp.setText(optionName[position]);
		
		ProfilesList profiles = new ProfilesList(applicationContext);
		profiles.loadProfilesList();
		
		switch(position) {
			case 0:
				switchSetupItem.setChecked(profiles.getProfiles().get(profileItemID).getAmbiantSound());
				switchSetupItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						//ProfilesList profiles = new ProfilesList(buttonView.getContext());
						//profiles.loadProfilesList();
						Log.v("Setup switch 1:", isChecked?"true":"false");
						//profiles.getProfiles().get(profileItemID).setAmbiantSound(isChecked);
						//profiles.saveProfilesList();
					}
					
				});
				break;
			
			case 1:
				switchSetupItem.setChecked(profiles.getProfiles().get(profileItemID).getWalkingAction());
				switchSetupItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						//ProfilesList profiles = new ProfilesList(applicationContext);
						//profiles.loadProfilesList();
						Log.v("Setup switch 2:", isChecked?"true":"false");
						//profiles.getProfiles().get(profileItemID).setWalkingAction(isChecked);
						//profiles.saveProfilesList();
					}
					
				});
				break;
			
			case 2:
				switchSetupItem.setChecked(profiles.getProfiles().get(profileItemID).getFlipToSilence());
				switchSetupItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						//ProfilesList profiles = new ProfilesList(applicationContext);
						//profiles.loadProfilesList();
						Log.v("Setup switch 3:", isChecked?"true":"false");
						//profiles.getProfiles().get(profileItemID).setFlipToSilence(isChecked);
						//profiles.saveProfilesList();
					}
					
				});
				break;
			
			default:
				break;
		}
		
		return convertView;
	}
	
}
