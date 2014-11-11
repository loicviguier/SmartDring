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
	private LayoutInflater inflater = null;
	private boolean[] switchState = new boolean[3];
	
	private final String[] optionName = {
			"Ambiant vol.", 
			"Movement", 
			"Phone rotation"};
	
	private final Integer[] optionIconID = {
			R.drawable.ic_noise, 
			R.drawable.ic_walking, 
			R.drawable.ic_reverse_phone};
    
    public ProfileSetupListAdapter(Context context, boolean[] switchState) {
		super();
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.switchState = switchState;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		//return null;
		return switchState[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
			convertView = inflater.inflate(R.layout.profile_setup_item, parent, false);
		
		ImageView iconSetupItem = (ImageView) convertView.findViewById(R.id.iconSetupItem);
		TextView textSetupItemp = (TextView) convertView.findViewById(R.id.textSetupItem);
		Switch switchSetupItem = (Switch) convertView.findViewById(R.id.switchSetupItem);
		
		iconSetupItem.setImageResource(optionIconID[position]);
		textSetupItemp.setText(optionName[position]);
		switchSetupItem.setChecked(switchState[position]);
		
		switchSetupItem.setTag(position);
		
		return convertView;
	}
	
}
