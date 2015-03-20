package net.mcviral.dev.plugins.pvpcontrol.gangs;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import net.mcviral.dev.plugins.pvpcontrol.main.PVPControl;
import net.mcviral.dev.plugins.pvpcontrol.util.FileManager;

public class GangController {
	
	private PVPControl plugin = null;
	private LinkedList <Gang> gangs = null;
	
	public GangController(PVPControl pvp){
		plugin = pvp;
		gangs = new LinkedList <Gang> ();
	}
	
	public void clearGangs(){
		gangs = null;
	}
	
	public void loadGangs(){
		plugin.log.info("Loading gangs...");
		File folder = new File(plugin.getDataFolder() + "/Gangs/");
		if (!folder.exists()){
			plugin.log.info("Gang directory created.");
			folder.mkdirs();
		}
		LinkedList <File> files = new LinkedList <File> ();
		List<File> tempFiles = Arrays.asList(folder.listFiles());
		if (tempFiles.size() > 0){
			for (File f : tempFiles){
				if (f.getName().endsWith(".yml")){
					plugin.log.info("Loaded Yaml: " + f.getName());
					files.add(f);
				}
			}
			FileManager fm = null;
			for (File f : files){
				fm = new FileManager(plugin, "Gangs/", f.getName().substring(0, f.getName().length() - 5));
				if (fm.exists()){
					plugin.log.info("Loading gang: " + f.getName().substring(0, f.getName().length() - 5));
					String name = fm.getYAML().getString("gang.name");
					String tag = fm.getYAML().getString("gang.tag");
					UUID leaderuuid = UUID.fromString(fm.getYAML().getString("gang.leader.uuid"));
					List <String> tempmembers = fm.getYAML().getStringList("gang.members");
					LinkedList <UUID> members = new LinkedList <UUID> ();
					UUID uuid = null;
					for (String s : tempmembers){
						uuid = UUID.fromString(s);
						if (uuid != null){
							members.add(uuid);
						}
					}
					boolean friendlyfire = fm.getYAML().getBoolean("gang.friendlyfire");
					boolean joinable = fm.getYAML().getBoolean("gang.joinable");
					if ((name != null) && (tag != null) && (leaderuuid != null) && (members != null)){
						gangs.add(new Gang(name, tag, leaderuuid, members, friendlyfire, joinable));
						plugin.log.info("Gang loaded.");
					}else{
						//Problem
						plugin.log.warning("Failed to load gang!");
						if (name != null){
							plugin.log.warning("Name: " + name);
						}else{
							plugin.log.warning("Name: null");
						}
						if (tag != null){
							plugin.log.warning("Tag: " + tag);
						}else{
							plugin.log.warning("Tag: null");
						}
						if (leaderuuid != null){
							plugin.log.warning("Leaderuuid: " + leaderuuid);
						}else{
							plugin.log.warning("Leaderuuid: null");
						}
						if (members != null){
							plugin.log.warning("Members: OK");
						}else{
							plugin.log.warning("Members: null");
						}
					}
				}else{
					plugin.log.severe("Couldn't load file: " + f.getName());
				}
			}
			plugin.log.info("Gang loading complete.");
		}else{
			plugin.log.info("No gangs to load.");
		}
		
	}
	
	public void saveGangs(){
		plugin.log.info("Saving gangs...");
		FileManager fm = null;
		if (gangs.size() > 0){
			for (Gang g : gangs){
				fm = new FileManager(plugin, "Gangs/", g.getName());
				boolean created = false;
				if (!fm.exists()){
					created = fm.createFile();
				}else{
					created = true;
				}
				if (created){
					fm.getYAML().set("gang.name", g.getName());
					fm.getYAML().set("gang.tag", g.getTag());
					fm.getYAML().set("gang.leaderuuid", g.getLeaderuuid());
					fm.getYAML().set("gang.members", g.getMembers());
					fm.getYAML().set("gang.friendlyfire", g.allowsFriendlyfire());
					fm.getYAML().set("gang.joinable", g.isJoinable());
					fm.saveYAML();
				}else{
					//Error
					plugin.log.severe("Failed to save gang: " + g.getName() + " as the file couldn't be created.");
				}
			}
			plugin.log.info("Gangs saved.");
		}else{
			plugin.log.info("No gangs to save.");
		}
	}
	
	public LinkedList <Gang> getGangs(){
		return gangs;
	}
	
	public boolean isInAGang(UUID uuid){
		for (Gang g : gangs){
			if (g.getMembers().contains(uuid)){
				return true;
			}
		}
		return false;
	}
	
	public Gang getGang(UUID uuid){
		for (Gang g : gangs){
			if (g.getMembers().contains(uuid)){
				return g;
			}
		}
		return null;
	}
	
}
