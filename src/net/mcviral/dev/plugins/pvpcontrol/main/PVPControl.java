package net.mcviral.dev.plugins.pvpcontrol.main;

import net.mcviral.dev.plugins.pvpcontrol.gangs.GangController;
import net.mcviral.dev.plugins.pvpcontrol.points.PointsController;
import net.mcviral.dev.plugins.pvpcontrol.pvp.PVPController;
import net.mcviral.dev.plugins.pvpcontrol.util.Log;

import org.bukkit.plugin.java.JavaPlugin;

public class PVPControl extends JavaPlugin{
	
	public Log log = new Log(this.getDescription().getName());
	private GangController gangcontroller = null;
	private PVPController pvpcontroller = null;
	private PointsController pointscontroller = null;
	
	public void onEnable(){
		
	}
	
	public void onDisable(){
		
	}
	
	public void startup(){
		
	}
	
	public void shutdown(){
		
	}
	
	public GangController getGangController(){
		return gangcontroller;
	}
	
	public PVPController getPVPController(){
		return pvpcontroller;
	}
	
	public PointsController getPointsController(){
		return pointscontroller;
	}
	
}
