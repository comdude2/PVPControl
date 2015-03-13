package net.mcviral.dev.plugins.pvpcontrol.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.bukkit.plugin.Plugin;

public class ErrorReporter {
	
	private Plugin plugin = null;
	private String reportPath = null;
	private Log log = null;
	
	public ErrorReporter(Plugin plugin, Log log, String reportPath){
		this.plugin = plugin;
		this.log = log;
		this.setReportPath(reportPath);
	}
	
	public void reportError(String title, String description, Exception e){
		Date d = new Date();
		File f = new File(reportPath);
		if (f.exists()){
			f = new File(plugin.getDataFolder() + "/" + reportPath + d.getTime() + ".yml");
			String error = exceptionToString(e);
			if (!f.exists()){
				createYaml(plugin.getDataFolder() + "/" + reportPath + d.getTime());
			}
			FileManager fm = new FileManager(plugin, reportPath, d.getTime() + "");
			int i = 1;
			try{
				log.warning("ErrorReporter: New Error from plugin - " + plugin.getDescription().getName());
				log.warning(title);
				log.warning(description);
				log.warning(error);
				fm.getYAML().set("count", i+1);
				fm.getYAML().set(i + ".title", title);
				fm.getYAML().set(i + ".description", description);
				fm.getYAML().set(i + ".message", e.getMessage());
				fm.getYAML().set(i + ".error", error);
				fm.saveYAML();
			}catch(Exception ex){
				log.severe("----------------------------- ErrorReporter ERROR -----------------------------");
				e.printStackTrace();
			}
		}else{
			log.warning("Error report path not found, failed to log error!");
		}
	}
	
	private String exceptionToString(Exception ex){
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}
	
	public void createYaml(String name){
		try{ 
			File file = new File(name + ".yml");
			file.createNewFile(); 
		}catch(IOException e){ 
			e.printStackTrace(); 
		}
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
	
}
