package net.mcviral.dev.plugins.pvpcontrol.main;

import net.mcviral.dev.plugins.pvpcontrol.gangs.Gang;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;

public class Listeners implements Listener{
	
	private PVPControl plugin = null;
	
	public Listeners(PVPControl plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event){
		boolean allow = true;
		Player vic = (Player) event.getEntity();
		Player atk = null;
		if (event.getEntity() instanceof Player){
			vic = (Player) event.getEntity();
			if (event.getDamager() instanceof Player){
				atk = (Player) event.getDamager();
				if (atk != vic){
					allow = allowPVP(vic, atk);
				}
			}else if (event.getDamager() instanceof Projectile){
				Projectile p = (Projectile) event.getDamager();
				if (p.getShooter() instanceof Player){
					atk = (Player) p.getShooter();
					allow = allowPVP(vic, atk);
				}
			}
		}
		if (!allow){
			event.setCancelled(true);
			atk.sendMessage(ChatColor.RED + "This player has PVP off, you aren't allowed to damage them.");
		}
	}
	
	//CHECK BOTH FOR GANG AND MEMBER PVP OFF
	
	@SuppressWarnings("unused")
	@EventHandler(priority = EventPriority.HIGH)
	public void onPotionSplash(PotionSplashEvent event){
		boolean allow = true;
		if (event.getEntity().getShooter() instanceof Player){
			Player atk = (Player) event.getEntity().getShooter();
			Player vic = null;
			for (LivingEntity e : event.getAffectedEntities()){
				if (e instanceof Player){
					vic = (Player) e;
					allow = allowPVP(vic, atk);
				}
			}
		}
	}
	
	public boolean allowPVP(Player vic, Player atk){
		//Check global pvp then gang pvp
		if (plugin.getPVPController().isAMember(atk.getUniqueId())){
			Member matk = plugin.getPVPController().getMember(atk.getUniqueId());
			if (matk.getGlobalPVP()){
				if (plugin.getPVPController().isAMember(vic.getUniqueId())){
					Member mvic = plugin.getPVPController().getMember(vic.getUniqueId());
					if (mvic.getGlobalPVP()){
						return true;
					}//PVP off
				}else{
					return true;
				}
			}//PVP off
		}
		//Check gang
		Gang gatk = plugin.getGangController().getGang(atk.getUniqueId());
		Gang gvic = plugin.getGangController().getGang(vic.getUniqueId());
		if ((gatk != null) && (gvic != null)){
			//They are both in gangs
			if (gatk == gvic){
				if (gatk.allowsFriendlyfire()){
					//Allowed
					return true;
				}else{
					//PVP off
				}
			}else{
				return true;
			}
		}else{
			return true;
		}
		return false;
	}
	
}
