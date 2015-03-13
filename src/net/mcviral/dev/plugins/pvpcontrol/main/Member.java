package net.mcviral.dev.plugins.pvpcontrol.main;

import java.util.UUID;

public class Member {
	
	private UUID uuid = null;
	private boolean globalpvp = true;
	
	public Member(UUID uuid){
		this.uuid = uuid;
	}
	
	public UUID getUUID(){
		return uuid;
	}
	
	public boolean getGlobalPVP(){
		return globalpvp;
	}
	
	public void setGlobalPVP(boolean state){
		globalpvp = state;
	}
	
}
