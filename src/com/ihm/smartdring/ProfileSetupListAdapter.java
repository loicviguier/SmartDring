package com.ihm.smartdring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class ProfileSetupListAdapter extends BaseAdapter {
	private Boolean[] optionActivate;
	private LayoutInflater inflater;
	
	private final String[] optionName = {
			"Ambiant vol.", 
			"Movement", 
			"Phone rotation"};
	
	private final Integer[] optionIconID = {
			R.drawable.ic_noise, 
			R.drawable.ic_walking, 
			R.drawable.ic_reverse_phone};
    
    public ProfileSetupListAdapter(Context context, Boolean[] optionActivate) {
		super();
		this.optionActivate = optionActivate;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return optionActivate[position];
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
		switchSetupItem.setActivated(optionActivate[position]);
		
		return convertView;
	}
	
}
