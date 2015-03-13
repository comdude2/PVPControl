package net.mcviral.dev.plugins.pvpcontrol.gangs;

import java.util.LinkedList;
import java.util.UUID;

import net.mcviral.dev.plugins.pvpcontrol.main.PVPControl;

public class GangController {
	
	@SuppressWarnings("unused")
	private PVPControl plugin = null;
	private LinkedList <Gang> gangs = new LinkedList <Gang> ();
	
	public GangController(PVPControl pvp){
		plugin = pvp;
	}
	
	public void loadGangs(){
		
	}
	
	public void saveGangs(){
		
	}
	
	public LinkedList <Gang> getGangs(){
		return gangs;
	}
	
	public boolean isInAGang(UUID uuid){
		for (Gang g : gangs){
			if (g.getMembers().contains(uuid)){
				return true;
			}
		}
		return false;
	}
	
	public Gang getGang(UUID uuid){
		for (Gang g : gangs){
			if (g.getMembers().contains(uuid)){
				return g;
			}
		}
		return null;
	}
	
}
