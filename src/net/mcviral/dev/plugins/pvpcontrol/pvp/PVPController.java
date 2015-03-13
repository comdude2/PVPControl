package net.mcviral.dev.plugins.pvpcontrol.pvp;

import java.util.LinkedList;

import net.mcviral.dev.plugins.pvpcontrol.main.Member;
import net.mcviral.dev.plugins.pvpcontrol.main.PVPControl;

public class PVPController {
	
	@SuppressWarnings("unused")
	private PVPControl plugin = null;
	private LinkedList <Member> members = new LinkedList <Member> ();
	
	public PVPController(PVPControl plugin){
		this.plugin = plugin;
	}

	public LinkedList <Member> getMembers() {
		return members;
	}

	public void setMembers(LinkedList <Member> members) {
		this.members = members;
	}
	
	
	
}
