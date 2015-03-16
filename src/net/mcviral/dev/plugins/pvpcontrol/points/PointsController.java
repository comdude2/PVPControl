package net.mcviral.dev.plugins.pvpcontrol.points;

import java.io.File;
import java.util.LinkedList;
import java.util.UUID;

import net.mcviral.dev.plugins.pvpcontrol.main.PVPControl;
import net.mcviral.dev.plugins.pvpcontrol.util.FileManager;

public class PointsController {
	
	private PVPControl plugin = null;
	private LinkedList <PointsPlayer> players = new LinkedList <PointsPlayer> ();
	
	public PointsController(PVPControl plugin){
		this.plugin = plugin;
		File folder = new File(plugin.getDataFolder() + "/Points/");
		if (!folder.exists()){
			plugin.log.info("Points directory created.");
			folder.mkdirs();
		}
	}
	
	public LinkedList <PointsPlayer> getPointsPlayers(){
		return players;
	}
	
	public void setPointsPlayers(LinkedList <PointsPlayer> players){
		this.players = players;
	}
	
	public boolean isOnFile(UUID uuid){
		File folder = new File(plugin.getDataFolder() + "/Points/" + uuid.toString() + ".yml");
		if (!folder.exists()){
			return false;
		}
		return true;
	}
	
	public boolean createPlayer(UUID uuid){
		boolean success = false;
		FileManager fm = new FileManager(plugin, "Points/", uuid.toString());
		if (!fm.exists()){
			success = fm.createFile();
		}
		return success;
	}
	
	public boolean loadPlayer(UUID uuid){
		FileManager fm = new FileManager(plugin, "Points/", uuid.toString());
		if (fm.exists()){
			int points = fm.getYAML().getInt("points");
			if (points >= 0){
				players.add(new PointsPlayer(uuid, points));
			}else{
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
	
	public boolean savePlayer(UUID uuid){
		FileManager fm = new FileManager(plugin, "Points/", uuid.toString());
		if (!fm.exists()){
			if (!createPlayer(uuid)){
				return false;
			}
		}
		PointsPlayer p = getPlayer(uuid);
		if (p != null){
			fm.getYAML().set("points", p.getPoints());
			fm.saveYAML();
			return true;
		}else{
			return false;
		}
	}
	
	public void savePlayers(){
		FileManager fm = null;
		for (PointsPlayer p : players){
			fm = new FileManager(plugin, "Points/", p.getUUID().toString());
			if (!fm.exists()){
				if (!createPlayer(p.getUUID())){
					return;
				}
			}
			fm.getYAML().set("points", p.getPoints());
			fm.saveYAML();
		}
	}
	
	public PointsPlayer getPlayer(UUID uuid){
		for (PointsPlayer p : players){
			if (p.getUUID().equals(uuid)){
				return p;
			}
		}
		return null;
	}
	
}
