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
	private boolean loadedBefore = false;
	private Listeners listeners = null;
	
	public void onEnable(){
		this.saveDefaultConfig();
		listeners = new Listeners(this);
		if (!loadedBefore){
			this.getServer().getPluginManager().registerEvents(listeners, this);
		}
		startup();
		log.info(this.getDescription().getName() + " Enabled!");
	}
	
	public void onDisable(){
		shutdown();
		log.info(this.getDescription().getName() + " Disabled!");
	}
	
	public void startup(){
		gangcontroller.loadGangs();
	}
	
	public void shutdown(){
		gangcontroller.saveGangs();
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
