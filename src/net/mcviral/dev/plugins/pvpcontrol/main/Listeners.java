package net.mcviral.dev.plugins.pvpcontrol.main;

import net.mcviral.dev.plugins.pvpcontrol.gangs.Gang;
import net.mcviral.dev.plugins.pvpcontrol.points.PointsPlayer;
import net.mcviral.dev.plugins.pvpcontrol.pvp.Result;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener{
	
	private PVPControl plugin = null;
	
	public Listeners(PVPControl plugin){
		this.plugin = plugin;
	}
	
	//if (plugin.debug){
		//plugin.log.info("");
	//}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event){
		plugin.getPVPController().getMembers().add(new Member(event.getPlayer().getUniqueId()));
		if (plugin.getPointsController().isOnFile(event.getPlayer().getUniqueId())){
			plugin.getPointsController().loadPlayer(event.getPlayer().getUniqueId());
		}else{
			plugin.getPointsController().createPlayer(event.getPlayer().getUniqueId());
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event){
		for (Member m : plugin.getPVPController().getMembers()){
			if (m.getUUID().equals(event.getPlayer().getUniqueId())){
				plugin.getPVPController().getMembers().remove(m);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent event){
		if (!plugin.isBlacklistedWorld(event.getEntity().getWorld().getName())){
			if (event.getEntity().getKiller() instanceof Player){
				Player p = (Player) event.getEntity().getKiller();
				PointsPlayer pp = plugin.getPointsController().getPlayer(p.getUniqueId());
				pp.setPoints(pp.getPoints() + 1);
				p.sendMessage(ChatColor.GRAY + "You killed " + event.getEntity().getName());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event){
		if (!plugin.isBlacklistedWorld(event.getEntity().getWorld().getName())){
			Result r = null;
			if (plugin.debug){
				plugin.log.info("EntityDamageByEntityEvent called.");
			}
			boolean allow = true;
			Player vic = null;
			Player atk = null;
			if (event.getEntity() instanceof Player){
				vic = (Player) event.getEntity();
				cancelTaskDueToHit(vic);
				if (event.getDamager() instanceof Player){
					atk = (Player) event.getDamager();
					cancelTaskDueToHit(atk);
					if (atk != vic){
						r = allowPVP(vic, atk);
						allow = r.isAllowed();
						if (plugin.debug){
							plugin.log.info("Allow PVP: " + allow);
						}
					}
				}else if (event.getDamager() instanceof Projectile){
					Projectile p = (Projectile) event.getDamager();
					if (p.getShooter() instanceof Player){
						atk = (Player) p.getShooter();
						cancelTaskDueToHit(atk);
						r = allowPVP(vic, atk);
						allow = r.isAllowed();
						if (plugin.debug){
							plugin.log.info("Allow projectile PVP: " + allow);
						}
					}
				}
			}
			if (!allow){
				event.setCancelled(true);
				if (r != null){
					if (r.getCause() != null){
						if (r.getCause() == "ATK"){
							atk.sendMessage(ChatColor.RED + "You have PVP off, turn it on to engage other players in combat.");
						}else if (r.getCause() == "VIC"){
							atk.sendMessage(ChatColor.RED + "This player has PVP off, you aren't allowed to damage them.");
						}else if (r.getCause() == "GANG"){
							atk.sendMessage(ChatColor.RED + "This player is in your gang and friendly fire is disabled.");
						}else{
							//Wut, why's it not normal?
						}
					}else{
						//No cause, wut?
					}
				}else{
					//Wut
				}
				//atk.sendMessage(ChatColor.RED + "This player has PVP off, you aren't allowed to damage them.");
			}
		}
	}
	
	//CHECK BOTH FOR GANG AND MEMBER PVP OFF
	
	//@SuppressWarnings("unused")
	@EventHandler(priority = EventPriority.HIGH)
	public void onPotionSplash(PotionSplashEvent event){
		if (!plugin.isBlacklistedWorld(event.getEntity().getWorld().getName())){
			Result r = null;
			boolean allow = true;
			Player atk = null;
			if (event.getEntity().getShooter() instanceof Player){
				atk = (Player) event.getEntity().getShooter();
				cancelTaskDueToHit(atk);
				Player vic = null;
				for (LivingEntity e : event.getAffectedEntities()){
					if (e instanceof Player){
						vic = (Player) e;
						cancelTaskDueToHit(vic);
						r = allowPVP(vic, atk);
						allow = r.isAllowed();
					}
				}
			}
			if (!allow){
				event.setCancelled(true);
				atk.sendMessage(ChatColor.RED + "One or more of the players you hit has PVP off or is in your gang, you aren't allowed to damage them.");
			}
		}
	}
	
	public void cancelTaskDueToHit(Player p){
		Member m = plugin.getPVPController().getMember(p.getUniqueId());
		if (m == null){
			m = new Member(p.getUniqueId());
			return;
		}
		if (m.hasATaskRunning()){
			p.sendMessage(ChatColor.YELLOW + "Your PVP change has been canceled as you were hit.");
			m.cancelTask();
		}
	}
	
	public Result allowPVP(Player vic, Player atk){
		boolean allow = true;
		String cause = null;
		//Check global pvp then gang pvp
		if (plugin.debug){
			plugin.log.info("Atk: " + atk.getName() + " Vic: " + vic.getName());
		}
		if (plugin.getPVPController().isAMember(atk.getUniqueId())){
			if (plugin.debug){
				plugin.log.info("Attacker is registered as a member.");
			}
			Member matk = plugin.getPVPController().getMember(atk.getUniqueId());
			if (matk.getGlobalPVP()){
				if (plugin.debug){
					plugin.log.info("Attacker's pvp is off");
				}
				if (plugin.getPVPController().isAMember(vic.getUniqueId())){
					Member mvic = plugin.getPVPController().getMember(vic.getUniqueId());
					if (mvic.getGlobalPVP()){
						if (plugin.debug){
							plugin.log.info("Both players pvp is on");
						}
						allow = true;//erm, no
					}else{
						//PVP off
						if (cause == null){
							cause = "VIC";
						}
						allow = false;
					}
				}else{
					allow = true;//erm, no
				}
			}else{
				//PVP off
				if (cause == null){
					cause = "ATK";
				}
				allow = false;
			}
		}else{
			if (plugin.debug){
				plugin.log.info("Woah, why is " + atk.getName() + " not registered as a member?");
			}
		}
		//Check gang
		Gang gatk = plugin.getGangController().getGang(atk.getUniqueId());
		Gang gvic = plugin.getGangController().getGang(vic.getUniqueId());
		if ((gatk != null) && (gvic != null)){
			//They are both in gangs
			if (gatk == gvic){
				if (gatk.allowsFriendlyfire()){
					//Allowed
				}else{
					//PVP off
					if (cause == null){
						cause = "GANG";
					}
					allow = false;
				}
			}else{
				//allowed
			}
		}else{
			//who cares
		}
		return new Result(allow, cause);
	}
	
}
