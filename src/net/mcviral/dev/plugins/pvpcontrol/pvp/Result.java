package net.mcviral.dev.plugins.pvpcontrol.pvp;

public class Result {
	
	private boolean allowed = true;
	private String cause = null;
	
	public Result(boolean allowed, String cause){
		this.allowed = allowed;
		this.cause = cause;
	}
	
	public boolean isAllowed(){
		return allowed;
	}
	
	public String getCause(){
		return cause;
	}
	
}
