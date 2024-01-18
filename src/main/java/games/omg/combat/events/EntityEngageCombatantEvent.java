package games.omg.combat.events;

import org.bukkit.event.HandlerList;

import games.omg.combat.Combat;
import games.omg.combat.combatants.Combatant;

/**
 * Fires when an Entity disengages from combat with a Combatant.
 */
public class EntityEngageCombatantEvent extends CombatantEvent {
  private static final HandlerList handlers = new HandlerList();

  public EntityEngageCombatantEvent(Combat combat, Combatant combatant) {
    super(combat, combatant);
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}
