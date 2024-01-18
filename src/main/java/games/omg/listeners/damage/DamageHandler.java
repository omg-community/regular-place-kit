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

  /**
   * The maximum number of {@link Damage Damages} to store for each entity.
   */
  final private static int MAX_DAMAGES = 20;

  /**
   * The maximum amount of time until a {@link Damage} becomes invalid.
   */
  final private static double IGNORE_TIME = 8000;

  final private static HashMap<Entity, List<Damage>> damageList = new HashMap<>();

  /**
   * An EventHandler method which records damage taken by any entity.
   * This event and function is only called when the damage is caused by another
   * entity.
   * 
   * Projectiles and TNT have special handling.
   * 
   * @param event The EntityDamageByEntityEvent
   */
  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onEntityDamagedByCause(EntityDamageEvent event) {
    // Stop if the damage was caused by a custom damage event.
    if (event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
      return;
    }

    Entity damaged = event.getEntity();
    Entity damager = null;

    // Since EntityDamageEvent does not have a getDamager() method,
    // if this is an EntityDamageByEntityEvent, we need to extract the damager.
    if (event instanceof EntityDamageByEntityEvent) {
      damager = ((EntityDamageByEntityEvent) event).getDamager();
    }

    if (damager != null) {
      // The damaged entity was damaged by another entity.
      String damagerTypeName = EntityUtils.getDisplayName(damager.getType());

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
          recordDamage(damaged, shooter, damagerTypeName);
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
          recordDamage(damaged, tntSource, damagerTypeName);
        }
      } else {
        // The damaged entity was most likely attacked by an entity directly with no
        // external sources.
        recordDamage(damaged, damager, null);
      }
    } else {
      // The damaged entity was damaged by something other than an entity.
      // Simply record the damage with no damager.
      recordDamage(damaged, null, Utils.getDisplayNameFromInternalName(event.getCause().name()));
    }
  }

  //

  /**
   * Gets a Death object for an entity based on their damage history.
   * 
   * This should be called when a user dies, but can be called at any time
   * if it may be necessary to retrieve the latest attacker information.
   * 
   * @param killed The entity which was killed
   * @return The Death object for the entity
   */
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

          // Continue to the next damage - we don't want to add their cause to the causes
          // list.
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

  /**
   * Broadcasts a death message for an entity.
   * 
   * @param damagedEntity The entity which died
   * 
   * @deprecated This method probably shouldn't be used. Implement the message sender in the Death object.
   */
  public static void broadcastDeathMessage(Entity damagedEntity) {
    // Bukkit.broadcastMessage(getDeathMessage(damagedEntity));
  }

  // TODO: move damage history clearing into a separate method
  // simply use this method to broadcast the death message

  /**
   * Listens for when Entities die to broadcast a death message.
   * 
   * This method also clears the damage history for the entity.
   * 
   * There are some special cases for Tameable entities to locally
   * broadcast the death message to the owner.
   * 
   * @param event The EntityDeathEvent
   */
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

  // TODO: why reduplicate code?
  /**
   * Listens for when Players die to broadcast a death message.
   * 
   * This method also clears the damage history for the entity.
   * 
   * @param event The PlayerDeathEvent
   */
  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    broadcastDeathMessage(event.getEntity());
    clearDamages(event.getEntity());
    event.setDeathMessage(null);
  }

  /**
   * Listens for when Players quit to clear their damage history.
   * 
   * @param event The PlayerQuitEvent
   */
  @EventHandler(priority = EventPriority.MONITOR)
  public void onQuit(PlayerQuitEvent event) {
    clearDamages(event.getPlayer());
  }

  // TODO: move this stuff into Damage. Don't allow this to be called directly
  /**
   * Records a damage for an entity.
   * 
   * @param entity The entity which was damaged
   * @param cause The cause of the damage
   * 
   * @deprecated Don't call this directly.
   */
  public static void recordDamage(Entity entity, Damage cause) {
    if (!damageList.containsKey(entity))
      damageList.put(entity, new ArrayList<>());
    damageList.get(entity).add(0, cause);
    if (damageList.get(entity).size() > MAX_DAMAGES)
      damageList.get(entity).remove(MAX_DAMAGES); // TODO Is it great to keep calling .get() when you can use a
                                                  // reference variable?
  }

  /**
   * Records a damage for an entity.
   * 
   * @param damaged The entity which was damaged
   * @param damager The entity which caused the damage
   * @param cause The cause of the damage
   * 
   * @deprecated Don't call this directly.
   */
  public static void recordDamage(Entity damaged, Entity damager, String cause) {
    recordDamage(damaged, new Damage(damager, cause, System.currentTimeMillis()));
  }

  /**
   * Clears the damage history for an entity.
   * 
   * @param entity The entity to clear the damage history for
   * 
   * @deprecated Don't call this directly.
   */
  public static void clearDamages(Entity entity) {
    damageList.remove(entity);
  }
}
