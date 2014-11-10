package com.ihm.smartdring;

import java.io.Serializable;

public class Profile implements Serializable {
	
	// =============================
	// Attributes
	// =============================
	
	private static final long serialVersionUID = 1L;
	private String name;
	private int volume;
	private boolean ambiantSound;
	private boolean walkingAction;
	private boolean flipToSilence;
	
	// =============================
	// Constructor
	// =============================
	
	/**
	 * Create a new object Profile
	 * @param name
	 */
	public Profile(String name) {
		super();
		this.name = name;
		this.volume = 50;
		this.ambiantSound = false;
		this.walkingAction = false;
		this.flipToSilence = false;
	}
	
	// =============================
	// Getter and Setter
	// =============================
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the volume
	 */
	public int getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(int volume) {
		this.volume = volume;
	}

	/**
	 * @return the ambiantSound
	 */
	public boolean getAmbiantSound() {
		return ambiantSound;
	}

	/**
	 * @param ambiantSound the ambiantSound to set
	 */
	public void setAmbiantSound(boolean ambiantSound) {
		this.ambiantSound = ambiantSound;
	}

	/**
	 * @return the walkingAction
	 */
	public boolean getWalkingAction() {
		return walkingAction;
	}

	/**
	 * @param walkingAction the walkingAction to set
	 */
	public void setWalkingAction(boolean walkingAction) {
		this.walkingAction = walkingAction;
	}

	/**
	 * @return the flipToSilence
	 */
	public boolean getFlipToSilence() {
		return flipToSilence;
	}

	/**
	 * @param flipToSilence the flipToSilence to set
	 */
	public void setFlipToSilence(boolean flipToSilence) {
		this.flipToSilence = flipToSilence;
	}
	
}
