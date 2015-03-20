package net.mcviral.dev.plugins.pvpcontrol.gangs;

import java.util.LinkedList;
import java.util.UUID;

public class Gang {
	
	private String name = null;
	private String tag = null;
	private UUID leaderuuid = null;
	private LinkedList <UUID> members = new LinkedList <UUID> ();
	private boolean friendlyfire = true;
	private boolean joinable = false;
	
	public Gang(){
		
	}
	
	public Gang(String name, String tag, UUID leaderuuid, LinkedList <UUID> members, boolean friendlyfire, boolean joinable){
		this.name = name;
		this.tag = tag;
		this.leaderuuid = leaderuuid;
		this.members = members;
		this.friendlyfire = friendlyfire;
		this.joinable = joinable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public UUID getLeaderuuid() {
		return leaderuuid;
	}

	public void setLeaderuuid(UUID leaderuuid) {
		this.leaderuuid = leaderuuid;
	}

	public LinkedList <UUID> getMembers() {
		return members;
	}

	public void setMembers(LinkedList <UUID> members) {
		this.members = members;
	}

	public boolean allowsFriendlyfire() {
		return friendlyfire;
	}

	public void setFriendlyfire(boolean friendlyfire) {
		this.friendlyfire = friendlyfire;
	}
	
	public boolean isJoinable() {
		return joinable;
	}
	
	public void setJoinable(boolean joinable) {
		this.joinable = joinable;
	}
	
}
