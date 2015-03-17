package net.mcviral.dev.plugins.pvpcontrol.gangs.territory;

import net.mcviral.dev.plugins.pvpcontrol.gangs.Gang;

public class TerritoryCaptureTimer implements Runnable{
	
	private TerritoryController tc = null;
	private boolean cancel = false;
	private int id = -1;
	private int mode = -1;
	private Territory territory = null;
	private Gang gang = null;
	private Gang stealers = null;
	
	public TerritoryCaptureTimer(TerritoryController tc, int mode, Territory territory){
		this.mode = mode;
		this.territory = territory;
		this.gang = null;
	}
	
	public TerritoryCaptureTimer(TerritoryController tc, int mode, Territory territory, Gang gang){
		this.mode = mode;
		this.territory = territory;
		this.gang = gang;
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
	
	public Territory getTerritory(){
		return territory;
	}

	@Override
	public void run() {
		if (!cancel){
			if (mode == TimerMode.CAPTURE){
				if (gang != null){
					//broadcast capture
					tc.broadcastCapture(territory, gang);
					territory.setGangInControl(gang);
				}else{
					//announce problem
					tc.announceError("ERROR: Gang is null");
				}
			}else if (mode == TimerMode.NEUTRALISE){
				tc.broadcastNeutralise(territory);
				territory.setGangInControl(null);
			}else if (mode == TimerMode.STEALING){
				if (gang != null){
					if (stealers != null){
						//broadcast
						tc.broadcastSteal(territory, gang, stealers);
						territory.setGangInControl(gang);
					}else{
						//announce problem
						tc.announceError("ERROR: Stealers is null");
					}
				}else{
					//announce problem
					tc.announceError("ERROR: Gang is null");
				}
			}else{
				//Er, wtf kind of mode did they use the timer for?
			}
		}
	}
	
	

}
