package net.mcviral.dev.plugins.pvpcontrol.main;

import net.mcviral.dev.plugins.pvpcontrol.gangs.GangController;
import net.mcviral.dev.plugins.pvpcontrol.points.PointsController;
import net.mcviral.dev.plugins.pvpcontrol.pvp.PVPController;
import net.mcviral.dev.plugins.pvpcontrol.util.Log;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
		for (Player p : this.getServer().getOnlinePlayers()){
			pvpcontroller.getMembers().add(new Member(p.getUniqueId()));
		}
	}
	
	public void shutdown(){
		gangcontroller.saveGangs();
		gangcontroller.clearGangs();
		pvpcontroller.setMembers(null);
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
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("pvp")){
			pvp(sender, cmd, label, args);
		}else if (cmd.getName().equalsIgnoreCase("points")){
			points(sender, cmd, label, args);
		}else if (cmd.getName().equalsIgnoreCase("gang")){
			gang(sender, cmd, label, args);
		}
		return false;
	}
	
	public void help(CommandSender sender){
		
	}
	
	private boolean pvp(CommandSender sender, Command cmd, String label, String[] args){
		if (sender.hasPermission("pvp.use")){
			
		}else{
			sender.sendMessage(ChatColor.RED + "You don't have permission to do this.");
		}
		return false;
	}
	
	private boolean points(CommandSender sender, Command cmd, String label, String[] args){
		return false;
	}
	
	private boolean gang(CommandSender sender, Command cmd, String label, String[] args){
		return false;
	}
	
}
