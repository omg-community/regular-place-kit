package games.omg.listeners.damage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.projectiles.ProjectileSource;

import games.omg.utils.EntityUtils;
import games.omg.utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class DamageHandler implements Listener {

  final private static int MAX_DAMAGES = 20;
  final private static double IGNORE_TIME = 8000;

  final private static ChatColor CAUSE_COLOR = ChatColor.YELLOW;

  final private static HashMap<Entity, List<Damage>> damageList = new HashMap<>();

  //

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void recordDamageTaken(EntityDamageByEntityEvent event) {
    Entity damaged = event.getEntity();
    Entity damager = event.getDamager();
    EntityType damagerType = damager.getType();

    if (damager instanceof Projectile) {
      // The damaged entity was hit by a projectile.
      Projectile projectile = (Projectile) damager;
      ProjectileSource projectileSource = projectile.getShooter();

      if (!(projectileSource instanceof Entity)) {
        // There is no entity which shot the projectile.
        recordDamage(damaged, damager, null);
      } else {
        // There is an entity which shot the projectile.
        Entity shooter = (Entity) projectileSource;
        recordDamage(damaged, shooter, EntityUtils.getDisplayName(damagerType));
      }
    } else if (damager instanceof TNTPrimed) {
      // The damaged entity was hit by TNT.
      TNTPrimed tnt = (TNTPrimed) damager;
      Entity tntSource = tnt.getSource();

      if (tntSource == null) {
        // There is no entity which caused the TNT to be lit.
        recordDamage(damaged, damager, null);
      } else {
        // There is an entity which caused the TNT to be lit.
        recordDamage(damaged, tntSource, EntityUtils.getDisplayName(damagerType));
      }
    } else {
      // The damaged entity was most likely attacked by an entity directly with no external sources.
      recordDamage(damaged, damager, null);
    }
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onEntityDamagedByCause(EntityDamageEvent event) {
    // Stop if the damage was caused by another entity.
    if (event instanceof EntityDamageByEntityEvent) return;

    // Stop if the damage was caused by a custom damage event.
    Entity damagedEntity = event.getEntity();
    if (event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) return;

    // Record the damage.
    recordDamage(damagedEntity, null, Utils.getDisplayNameFromInternalName(event.getCause().name()));
  }

  //

  public static Death getDeath(Entity killed) {
    Entity killer = null;
    List<Entity> assists = new ArrayList<>();
    List<String> causes = new ArrayList<>();

    // If the entity has no damage history, return now.
    if (!damageList.containsKey(killed)) {
      return new Death(killed, killer, assists, causes);
    }

    for (Damage damage : damageList.get(killed)) {
      // If the damage is too old, skip it.
      if (System.currentTimeMillis() - damage.getTime() > IGNORE_TIME) {
        continue;
      }

      Entity damager = damage.getDamager();
      String cause = damage.getCause();

      // Do kill and assist logic if there's a damager.
      if (damager != null) {
        // If there is no killer yet, set the killer to the damager.
        if (killer == null) {
          killer = damager;
        }

        if (!killer.equals(damager)) {
          // This player is an assister.
          // Add them to the assists list if they're not already in it.
          if (!assists.contains(damager)) {
            assists.add(damager);
          }

          // Continue to the next damage - we don't want to add their cause to the causes list.
          continue;
        }
      }

      // Add the cause to the causes list.
      if (cause != null && !causes.contains(cause)) {
        causes.add(cause);
      }
    }

    return new Death(killed, killer, assists, causes);
  }

  public static void broadcastDeathMessage(Entity damagedEntity) {
    // Bukkit.broadcastMessage(getDeathMessage(damagedEntity));
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onEntityDeath(EntityDeathEvent event) {
    if (event.getEntity() instanceof Tameable) {
      AnimalTamer tamer = ((Tameable) event.getEntity()).getOwner();
      if (tamer != null) {
        Player owner = Bukkit.getPlayer(tamer.getUniqueId());
        if (owner != null && owner.isOnline()) {
          broadcastDeathMessage(event.getEntity());
          clearDamages(event.getEntity());
          event.getEntity().remove();
          return;
        }
      }
    }
    if (!(event.getEntity() instanceof Player)) {
      // broadcastDeathMessage(event.getEntity());
      clearDamages(event.getEntity());
    }
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    broadcastDeathMessage(event.getEntity());
    clearDamages(event.getEntity());
    event.setDeathMessage(null);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onQuit(PlayerQuitEvent event) {
    clearDamages(event.getPlayer());
  }

  public static void recordDamage(Entity entity, Damage cause) {
    if (!damageList.containsKey(entity))
      damageList.put(entity, new ArrayList<>());
    damageList.get(entity).add(0, cause);
    if (damageList.get(entity).size() > MAX_DAMAGES)
      damageList.get(entity).remove(MAX_DAMAGES); // TODO Is it great to keep calling .get() when you can use a
                                                  // reference variable?
  }

  public static void recordDamage(Entity damaged, Entity damager, String cause) {
    recordDamage(damaged, new Damage(damager, cause, System.currentTimeMillis()));
  }

  public static void clearDamages(Entity entity) {
    damageList.remove(entity);
  }
}
