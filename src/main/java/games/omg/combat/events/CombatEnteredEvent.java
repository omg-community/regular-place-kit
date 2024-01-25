package games.omg.combat.events;

import org.bukkit.event.HandlerList;

import games.omg.combat.Combat;

/**
 * Fires when an entity is now in combat.
 */
public class CombatEnteredEvent extends CombatEvent {
  private static final HandlerList handlers = new HandlerList();

  public CombatEnteredEvent(Combat combat) {
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
