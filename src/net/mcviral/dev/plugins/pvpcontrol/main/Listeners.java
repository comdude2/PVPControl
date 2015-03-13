package net.mcviral.dev.plugins.pvpcontrol.main;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.plugin.Plugin;

public class Listeners implements Listener{
	
	@SuppressWarnings("unused")
	private Plugin plugin = null;
	
	public Listeners(Plugin plugin){
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
		if (event.getEntity().getShooter() instanceof Player){
			Player atk = (Player) event.getEntity().getShooter();
			Player vic = null;
			for (LivingEntity e : event.getAffectedEntities()){
				if (e instanceof Player){
					vic = (Player) e;
					
				}
			}
		}
	}
	
	public boolean allowPVP(Player vic, Player atk){
		
		return false;
	}
	
}
