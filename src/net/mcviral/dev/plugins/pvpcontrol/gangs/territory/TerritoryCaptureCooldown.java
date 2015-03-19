package net.mcviral.dev.plugins.pvpcontrol.gangs.territory;

import java.util.UUID;

import org.bukkit.entity.Player;

import net.mcviral.dev.plugins.pvpcontrol.main.PVPControl;
import net.md_5.bungee.api.ChatColor;

public class TerritoryCaptureCooldown implements Runnable{
	
	private PVPControl plugin = null;
	private UUID uuid = null;
	private boolean cancel = false;
	private int id = -1;
	
	public TerritoryCaptureCooldown(PVPControl plugin, UUID uuid){
		this.plugin = plugin;
		this.uuid = uuid;
	}
	
	public void cancel(){
		cancel = true;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public void run(){
		if (!cancel){
			plugin.getTerritoryController().getCaptureBans().remove(uuid);
			Player p = plugin.getServer().getPlayer(uuid);
			if (p != null){
				p.sendMessage(ChatColor.YELLOW + "You are now able to capture territories again.");
			}else{
				//player went offline
			}
		}
	}
	
	public UUID getUUID(){
		return uuid;
	}
	
}
