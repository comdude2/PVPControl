package net.mcviral.dev.plugins.pvpcontrol.util;

import java.io.File;
import java.io.IOException;
//import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class FileManager {

	private Plugin plugin;
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	private String path = null;;
	private String filename = null;
	
	public FileManager(Plugin mplugin, String mpath, String file){
		plugin = mplugin;
		path = mpath;
		filename = file;
		customConfigFile = new File(plugin.getDataFolder() + "/" + path, filename + ".yml");
	}
	
	//@SuppressWarnings("deprecation")
	public void reloadYAML(){
		customConfigFile = new File(plugin.getDataFolder() + "/" + path, filename + ".yml");
		if (!customConfigFile.exists()){
			try {
				customConfigFile.mkdirs();
				customConfigFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
		// Look for defaults in the jar
		//InputStream defConfigStream = plugin.getResource(path + ".yml");
		//if (defConfigStream != null) {
			//YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			//customConfig.setDefaults(defConfig);
		//}
	}
	
	public FileConfiguration getYAML() {
	    if (customConfig == null) {
	        reloadYAML();
	    }
	    return customConfig;
	}
	
	public void saveYAML() {
	    if (customConfig == null || customConfigFile == null) {
	        return;
	    }
	    try {
	        getYAML().save(customConfigFile);
	    } catch (IOException ex) {
	        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile.getAbsolutePath() + customConfigFile.getName(), ex);
	    }
	}
	
	public boolean exists(){
		if (customConfigFile.exists()){
			return true;
		}else{
			return false;
		}
	}
	
	public void delete(){
		customConfigFile.delete();
	}
	
}
