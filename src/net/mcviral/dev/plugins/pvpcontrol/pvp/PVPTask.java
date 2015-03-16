package net.mcviral.dev.plugins.pvpcontrol.pvp;

import org.bukkit.entity.Player;

import net.mcviral.dev.plugins.pvpcontrol.main.Member;
import net.mcviral.dev.plugins.pvpcontrol.main.PVPControl;
import net.md_5.bungee.api.ChatColor;

public class PVPTask implements Runnable{
	
	private Member member = null;
	private boolean state;
	private boolean cancel = false;
	
	private PVPControl plugin = null;
	
	public PVPTask(PVPControl plugin, Member member, boolean state) {
		this.plugin = plugin;
		this.member = member;
		this.state = state;
	}
	
	public Member getUUID(){
		return member;
	}
	
	public void cancel(){
		cancel = true;
	}
	
	@Override
	public void run(){
		if (!cancel){
			member.setGlobalPVP(state);
			Player p = plugin.getServer().getPlayer(member.getUUID());
			if (p != null){
				String sstate = null;
				if (state){
					sstate = "on";
				}else{
					sstate = "off";
				}
				p.sendMessage(ChatColor.GREEN + "Your PVP is now " + sstate);
			}else{
				//player is offline
			}
		}
		member.clearTask();
	}
	
}
