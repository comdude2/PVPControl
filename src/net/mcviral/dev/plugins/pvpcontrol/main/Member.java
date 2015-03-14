package net.mcviral.dev.plugins.pvpcontrol.main;

import java.util.UUID;

import net.mcviral.dev.plugins.pvpcontrol.pvp.PVPTask;

public class Member {
	
	private UUID uuid = null;
	private boolean globalpvp = true;
	private PVPTask task = null;
	
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
	
	public void setTask(PVPTask task){
		this.task = task;
	}
	
	public boolean hasATaskRunningTask(){
		if (task != null){
			return true;
		}else{
			return false;
		}
	}
	
	public void clearTask(){
		task = null;
	}
	
}
