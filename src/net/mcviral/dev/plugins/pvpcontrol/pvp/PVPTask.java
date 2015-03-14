package net.mcviral.dev.plugins.pvpcontrol.pvp;

import net.mcviral.dev.plugins.pvpcontrol.main.Member;

public class PVPTask implements Runnable{
	
	private Member member = null;
	private boolean state;
	private boolean cancel = false;
	
	public PVPTask(Member member, boolean state) {
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
		}
		member.clearTask();
	}
	
}
