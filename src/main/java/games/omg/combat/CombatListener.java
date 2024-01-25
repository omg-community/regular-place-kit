package games.omg.combat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Manages combat between entities.
 */
public class CombatListener implements Listener {
  
  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    // check if either entity is in combat
    // we need to either start their combat
    // or update their combat in case this is a new engagement

    // relevant events should also be called
    // CombatEnteredEvent
    // EntityEngageCombatEvent

    // we need to start a timer for these two entities to disengage
    // which will be reset every time they damage each other (in this function)
    // the id will be the string of the two entity ids sorted alphabetically
    // so that we can easily find the timer for the two entities

    // Combatant.from(event.getEntity());
    // Combatant.from(event.getCause());
  }
}
