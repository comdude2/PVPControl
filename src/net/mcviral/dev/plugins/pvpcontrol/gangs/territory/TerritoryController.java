package net.mcviral.dev.plugins.pvpcontrol.gangs.territory;

import java.util.LinkedList;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.mcviral.dev.plugins.pvpcontrol.gangs.Gang;
import net.mcviral.dev.plugins.pvpcontrol.main.PVPControl;
import net.md_5.bungee.api.ChatColor;

public class TerritoryController {
	
	private PVPControl plugin = null;
	private LinkedList <Territory> territories = new LinkedList <Territory> ();
	private LinkedList <TerritoryCaptureTimer> timers = new LinkedList <TerritoryCaptureTimer> ();
	private LinkedList <TerritoryCaptureCooldown> cooldowns = new LinkedList <TerritoryCaptureCooldown> ();
	private LinkedList <UUID> capturebans = new LinkedList <UUID> ();
	
	public TerritoryController(PVPControl plugin){
		this.plugin = plugin;
	}
	
	public void captureTerritory(UUID uuid, Territory t, Gang g){
		TerritoryCaptureTimer tct = new TerritoryCaptureTimer(this, TimerMode.CAPTURE, t, g);
		timers.add(tct);
		int id = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, tct, 0L);
		tct.setId(id);
		TerritoryCaptureCooldown tcc = new TerritoryCaptureCooldown(plugin, uuid);
		cooldowns.add(tcc);
		id = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, tcc, 0L);
		tcc.setId(id);
	}
	
	public void neutraliseTerritory(){
		
	}
	
	public void stealTerritory(){
		
	}
	
	public LinkedList <Territory> getTerritories() {
		return territories;
	}
	
	public void setTerritories(LinkedList <Territory> territories) {
		this.territories = territories;
	}
	
	public void cancelTimers(){
		for (TerritoryCaptureTimer t : timers){
			plugin.getServer().getScheduler().cancelTask(t.getId());
		}
	}
	
	public void cancelCooldowns(){
		for (TerritoryCaptureCooldown t : cooldowns){
			plugin.getServer().getScheduler().cancelTask(t.getId());
		}
	}
	
	public void broadcastCapture(Territory territory, Gang gang){
		for (Player p : plugin.getServer().getOnlinePlayers()){
			if (gang.getMembers().contains(p.getUniqueId())){
				p.sendMessage(ChatColor.GREEN + gang.getTag() + ChatColor.GRAY + " have captured " + territory.getName());
			}else{
				p.sendMessage(ChatColor.RED + gang.getTag() + ChatColor.GRAY + " have captured " + territory.getName());
			}
		}
	}
	
	public void broadcastNeutralise(Territory territory){
		plugin.getServer().broadcastMessage(territory.getName() + " has gone back to being controlled by the" + ChatColor.AQUA + " GUARDS");
	}
	
	public void broadcastSteal(Territory territory, Gang gang, Gang stealers){
		for (Player p : plugin.getServer().getOnlinePlayers()){
			if (gang.getMembers().contains(p.getUniqueId())){
				p.sendMessage(ChatColor.RED + stealers.getName() + ChatColor.GRAY + " have stole " + territory.getName() + " from " + ChatColor.GREEN + gang.getTag());
			}else if (stealers.getMembers().contains(p.getUniqueId())){
				p.sendMessage(ChatColor.GREEN + stealers.getName() + ChatColor.GRAY + " have stole " + territory.getName() + " from " + ChatColor.RED + gang.getTag());
			}else{
				p.sendMessage(ChatColor.YELLOW + stealers.getName() + ChatColor.GRAY + " have stole " + territory.getName() + " from " + ChatColor.YELLOW + gang.getTag());
			}
		}
	}
	
	public void announceError(String error){
		plugin.log.severe(error);
	}
	
	public LinkedList <UUID> getCaptureBans(){
		return capturebans;
	}
	
}
