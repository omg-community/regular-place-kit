package games.omg.listeners.damage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
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
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.projectiles.ProjectileSource;

import games.omg.utils.EntityUtils;
import games.omg.utils.Utils;
import javafx.scene.image.Image;
import net.md_5.bungee.api.ChatColor;

public class DamageListener implements Listener {

  final private static int MAX_DAMAGES = 20;
  final private static double IGNORE_TIME = 8000;

  final private static ChatColor CAUSE_COLOR = ChatColor.YELLOW;

  final private static HashMap<Entity, List<DamageAction>> damageList = new HashMap<>();

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

  public static void damage(Entity damagedEntity, double damage, DamageAction cause) {
    if (!(damagedEntity instanceof Damageable)) return;
    recordDamage(damagedEntity, cause);
    ((Damageable) damagedEntity).damage(damage);
  }

  public static Entity getKiller(Entity damagedEntity) {
    if (!damageList.containsKey(damagedEntity)) return null;
    for (DamageAction cause : damageList.get(damagedEntity)) if (System.currentTimeMillis() - cause.getTick() <= IGNORE_TIME) {
      if (cause.getDamager() == null) continue;
      return cause.getDamager();
    }
    return null;
  }

  public static List<Entity> getAssists(Entity damagedEntity) {
    List<Entity> assists = new ArrayList<>();
    if (!damageList.containsKey(damagedEntity)) return assists;

    Entity killer = null;
    for (DamageAction cause : damageList.get(damagedEntity)) {
      if (System.currentTimeMillis() - cause.getTick() > IGNORE_TIME) continue;
      if (cause.getDamager() == null) continue;
      Entity damager = cause.getDamager();
      if (killer == null || killer.equals(damager)) {
        killer = damager;
        continue;
      }
      if (!assists.contains(damager)) assists.add(damager);
    }
    return assists;
  }

  public static List<String> getCauses(Entity damagedEntity, boolean fullList) {
    if (fullList) {
      List<String> causes = new ArrayList<>();
      if (!damageList.containsKey(damagedEntity)) return causes;
      for (DamageAction cause : damageList.get(damagedEntity)) {
        if (System.currentTimeMillis() - cause.getTick() > IGNORE_TIME) continue;
        String currentCause = cause.getCause();
        if (currentCause != null && !causes.contains(currentCause)) causes.add(currentCause);
      }
      return causes;
    } else {
      List<String> causes = new ArrayList<>();
      if (!damageList.containsKey(damagedEntity)) return causes;

      Entity killer = null;
      for (DamageAction cause : damageList.get(damagedEntity)) {
        if (System.currentTimeMillis() - cause.getTick() > IGNORE_TIME) continue;
        if (cause.getDamager() != null) {
          Entity damager = cause.getDamager();
          if (killer == null) {
            killer = damager;
          } else {
            if (!killer.equals(damager)) continue;
          }
        }
        String currentCause = cause.getCause();
        if (currentCause != null && !causes.contains(currentCause)) causes.add(currentCause);
      }
      return causes;
    }
  }

  public static String getDeathMessage(Entity damagedEntity) {
    Entity killer = getKiller(damagedEntity);
    List<Entity> assists = getAssists(damagedEntity);
    List<String> causes = getCauses(damagedEntity, false);

    String deadName;
    if (damagedEntity instanceof Player) {
      deadName = Utils.getName(damagedEntity);
    } else {
      deadName = Utils.getEntityTypeName(damagedEntity.getType());
      if (damagedEntity instanceof Tameable) {
        AnimalTamer tamer = ((Tameable) damagedEntity).getOwner();
        if (tamer != null) {
          Player owner = Bukkit.getPlayer(tamer.getUniqueId());
          if (owner != null && owner.isOnline()) {
            deadName = owner.getName() + "'s " + deadName;
          }
        }
      }
    }
    if (deadName == null) deadName = "?¿?¿?";
    ChatColor deadColor = ChatColor.RED; //TODO Not always red pls
    deadName = deadColor + deadName;

    String causeMod = null;
    if (!causes.isEmpty()) {
      for (String cause : causes) {
        if (causeMod == null) {
          causeMod=cause;
        } else {
          causeMod = causeMod + ", " + cause;
        }
      }
      causeMod = CAUSE_COLOR + causeMod;
    }

    if (killer == null) {
      if (causes.isEmpty()) {
        //No killer, no causes
        return ChatColor.GRAY + " » " + deadName + ChatColor.RESET + ChatColor.GRAY + " killed by " + CAUSE_COLOR + "?¿?¿?";
      } else {
        //No killer, causes
        return ChatColor.GRAY + " » " + deadName + ChatColor.RESET + ChatColor.GRAY + " killed by " + causeMod;
      }
    }

    String killerName;
    if (killer instanceof Player) {
      killerName = Utils.getName(killer);
    } else {
      killerName = Utils.getEntityTypeName(killer.getType());
    }
    if (killerName == null) killerName = "?¿?¿?";
    ChatColor killerColor = ChatColor.RED; //TODO Not always red pls
    killerName = killerColor + killerName;
    if (!assists.isEmpty()) killerName = killerName + " + " + assists.size();

    if (causes.isEmpty()) {
      //Killer, no causes
      return ChatColor.GRAY + " » " + deadName + ChatColor.RESET + ChatColor.GRAY + " killed by " + killerName;
    }
    //Killer, causes
    return ChatColor.GRAY + " » " + deadName + ChatColor.RESET + ChatColor.GRAY +" killed by " + killerName + ChatColor.RESET + ChatColor.GRAY + " with " + causeMod;
  }

  public static void broadcastDeathMessage(Entity damagedEntity) {
      Bukkit.broadcastMessage(getDeathMessage(damagedEntity));
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
      //broadcastDeathMessage(event.getEntity());
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

  public static void damage(Entity damagedEntity, double damage, Entity damager, String cause) {
    damage(damagedEntity,damage,new DamageAction(damager,cause,System.currentTimeMillis()));
  }

  public static void recordDamage(Entity entity, DamageAction cause) {
    if (!damageList.containsKey(entity)) damageList.put(entity,new ArrayList<>());
    damageList.get(entity).add(0,cause);
    if (damageList.get(entity).size()>MAX_DAMAGES) damageList.get(entity).remove(MAX_DAMAGES); //TODO Is it great to keep calling .get() when you can use a reference variable?
  }

  public static void recordDamage(Entity damaged, Entity damager, String cause) {
    recordDamage(damaged, new DamageAction(damager,cause, System.currentTimeMillis()));
  }

  public static void clearDamages(Entity entity) {
    damageList.remove(entity);
  }
}
