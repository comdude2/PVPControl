package net.mcviral.dev.plugins.pvpcontrol.points;

import java.util.UUID;

public class PointsPlayer {
	
	private UUID uuid = null;
	private int points = -1;
	
	public PointsPlayer(UUID uuid, int points){
		this.uuid = uuid;
		this.points = points;
	}
	
	public UUID getUUID(){
		return uuid;
	}
	
	public int getPoints(){
		return points;
	}
	
	public void setPoints(int points){
		this.points = points;
	}
	
}
