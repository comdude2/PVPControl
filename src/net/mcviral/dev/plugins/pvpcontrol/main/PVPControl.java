package net.mcviral.dev.plugins.pvpcontrol.main;

import java.util.List;

import net.mcviral.dev.plugins.pvpcontrol.gangs.GangController;
import net.mcviral.dev.plugins.pvpcontrol.points.PointsController;
import net.mcviral.dev.plugins.pvpcontrol.pvp.PVPController;
import net.mcviral.dev.plugins.pvpcontrol.pvp.PVPTask;
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
	public boolean debug = true;
	
	public void onEnable(){
		this.saveDefaultConfig();
		listeners = new Listeners(this);
		gangcontroller = new GangController(this);
		pvpcontroller = new PVPController(this);
		pointscontroller = new PointsController(this);
		if (!loadedBefore){
			this.getServer().getPluginManager().registerEvents(listeners, this);
		}
		this.reloadConfig();
		try{
			debug = this.getConfig().getBoolean("debug");
		}catch(Exception e){
			e.printStackTrace();
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
		this.getServer().getScheduler().cancelTasks(this);
	}
	
	public boolean isBlacklistedWorld(String world){
		this.reloadConfig();
		List <String> worlds = null;
		try{
			worlds = this.getConfig().getStringList("blacklisted");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		for (String w : worlds){
			if (w.equalsIgnoreCase(world)){
				return true;
			}
		}
		return false;
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
			return pvp(sender, cmd, label, args);
		}else if (cmd.getName().equalsIgnoreCase("points")){
			return points(sender, cmd, label, args);
		}else if (cmd.getName().equalsIgnoreCase("gang")){
			return gang(sender, cmd, label, args);
		}
		return false;
	}
	
	public void helpPVP(CommandSender sender){
		
	}
	
	public void helpPoints(CommandSender sender){
		
	}

	public void helpGang(CommandSender sender){
		
	}
	
	private boolean pvp(CommandSender sender, Command cmd, String label, String[] args){
		if (sender.hasPermission("pvp.use")){
			if (sender instanceof Player){
				Player p = (Player) sender;
				if (args.length > 0){
					if (args.length == 1){
						if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("on")){
							boolean state = false;
							String sstate = null;
							if (args[0].equalsIgnoreCase("off")){
								state = false;
								sstate = "off";
							}else if (args[0].equalsIgnoreCase("on")){
								state = true;
								sstate = "on";
							}else{
								//Error
								return true;
							}
							Member m = pvpcontroller.getMember(p.getUniqueId());
							if (m != null){
								if (m.getGlobalPVP() == state){
									//They already have pvp off, the dumb pricks
									sender.sendMessage(ChatColor.RED + "You already have pvp " + sstate);
									return true;
								}else{
									//Change the state
									if (m.hasATaskRunning()){
										//They're waiting for one.
										sender.sendMessage(ChatColor.RED + "You're already turning pvp " + sstate + ", you can't change your mind now.");
										return true;
									}else{
										PVPTask task = new PVPTask(this, m, state);
										m.setTask(task);
										this.getServer().getScheduler().scheduleSyncDelayedTask(this, task, 20 * 30L);
										sender.sendMessage(ChatColor.GREEN + "Your pvp will turn " + sstate + " in 30 seconds...");
										return true;
									}
								}
							}else{
								//Problem
								return false;
							}
						}else{
							helpPVP(sender);
							return false;
						}
					}else{
						helpPVP(sender);
						return false;
					}
				}else{
					helpPVP(sender);
					return false;
				}
			}else{
				sender.sendMessage(ChatColor.RED + "You need to be a player to perform this command.");
				return false;
			}
		}else{
			sender.sendMessage(ChatColor.RED + "You don't have permission to do this.");
			return false;
		}
	}
	
	private boolean points(CommandSender sender, Command cmd, String label, String[] args){
		if (sender.hasPermission("points.use")){
			if (args.length > 0){
				if (args.length == 1){
					
				}else{
					helpPVP(sender);
				}
			}else{
				helpPVP(sender);
			}
		}else{
			sender.sendMessage(ChatColor.RED + "You don't have permission to do this.");
		}
		return false;
	}
	
	private boolean gang(CommandSender sender, Command cmd, String label, String[] args){
		if (sender.hasPermission("gang.use")){
			if (args.length > 0){
				if (args.length == 1){
					
				}else{
					helpPVP(sender);
				}
			}else{
				helpPVP(sender);
			}
		}else{
			sender.sendMessage(ChatColor.RED + "You don't have permission to do this.");
		}
		return false;
	}
	
}
