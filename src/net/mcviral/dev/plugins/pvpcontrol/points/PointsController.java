package net.mcviral.dev.plugins.pvpcontrol.points;

import java.util.LinkedList;

import net.mcviral.dev.plugins.pvpcontrol.main.PVPControl;

public class PointsController {
	
	private PVPControl plugin = null;
	private LinkedList <PointsPlayer> players = new LinkedList <PointsPlayer> ();
	
	public PointsController(PVPControl plugin){
		this.plugin = plugin;
	}
	
	public LinkedList <PointsPlayer> getPointsPlayers(){
		return players;
	}
	
	public void setPointsPlayers(LinkedList <PointsPlayer> players){
		this.players = players;
	}
	
}
