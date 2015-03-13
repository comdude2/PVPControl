package net.mcviral.dev.plugins.pvpcontrol.util;

import org.bukkit.Bukkit;

public class Log {

	private String name = null;
	
	public Log(String mname){
		name = mname;
	}
	
	public void info(String message){
		Bukkit.getLogger().info("[" + name + "] " + message);
	}
	
	public void warning(String message){
		Bukkit.getLogger().warning("[" + name + "] " + message);
	}
	
	public void severe(String message){
		Bukkit.getLogger().severe("[" + name + "] " + message);
	}
}
