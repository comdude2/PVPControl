package net.mcviral.dev.plugins.pvpcontrol.gangs;

import java.util.LinkedList;
import java.util.UUID;

public class Gang {
	
	private String name = null;
	private String tag = null;
	private UUID leaderuuid = null;
	private LinkedList <UUID> members = new LinkedList <UUID> ();
	private boolean friendlyfire = true;
	
	public Gang(){
		
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
	
}
