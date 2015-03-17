package net.mcviral.dev.plugins.pvpcontrol.gangs.territory;

import java.util.LinkedList;

import net.mcviral.dev.plugins.pvpcontrol.pvp.PVPController;

public class TerritoryController {
	
	@SuppressWarnings("unused")
	private PVPController plugin = null;
	private LinkedList <Territory> territories = new LinkedList <Territory> ();
	
	public TerritoryController(PVPController plugin){
		this.plugin = plugin;
	}

	public LinkedList <Territory> getTerritories() {
		return territories;
	}

	public void setTerritories(LinkedList <Territory> territories) {
		this.territories = territories;
	}
	
}
