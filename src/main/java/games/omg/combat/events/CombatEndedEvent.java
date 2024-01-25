package games.omg.combat.events;

import org.bukkit.event.HandlerList;

import games.omg.combat.Combat;

/**
 * Fires when an entity is no longer in combat with any other entities.
 */
public class CombatEndedEvent extends CombatEvent {
  private static final HandlerList handlers = new HandlerList();

  public CombatEndedEvent(Combat combat) {
    super(combat);
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}
