package net.mcviral.dev.plugins.pvpcontrol.gangs.territory;

import net.mcviral.dev.plugins.pvpcontrol.gangs.Gang;

public class Territory {
	
	private String name = null;
	private Gang gangincontrol = null;
	
	public Territory(String name, Gang gangincontrol){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gang getGangInControl() {
		return gangincontrol;
	}

	public void setGangInControl(Gang gangincontrol) {
		this.gangincontrol = gangincontrol;
	}
	
	
	
}
