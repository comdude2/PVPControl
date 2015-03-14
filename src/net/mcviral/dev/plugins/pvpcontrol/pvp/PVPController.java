package net.mcviral.dev.plugins.pvpcontrol.pvp;

import java.util.LinkedList;
import java.util.UUID;

import net.mcviral.dev.plugins.pvpcontrol.main.Member;
import net.mcviral.dev.plugins.pvpcontrol.main.PVPControl;

public class PVPController {
	
	@SuppressWarnings("unused")
	private PVPControl plugin = null;
	private LinkedList <Member> members = null;
	
	public PVPController(PVPControl plugin){
		this.plugin = plugin;
		members = new LinkedList <Member> ();
	}

	public LinkedList <Member> getMembers() {
		return members;
	}

	public void setMembers(LinkedList <Member> members) {
		this.members = members;
	}
	
	public boolean isAMember(UUID uuid){
		for (Member m : members){
			if (m.getUUID().equals(uuid)){
				return true;
			}
		}
		return false;
	}
	
	public Member getMember(UUID uuid){
		for (Member m : members){
			if (m.getUUID().equals(uuid)){
				return m;
			}
		}
		return null;
	}
	
}
