package games.omg.combat.events;

import org.bukkit.event.entity.EntityEvent;

import games.omg.combat.Combat;

public abstract class CombatEvent extends EntityEvent {
  final protected Combat combat;

  public CombatEvent(Combat combat) {
    super(combat.getEntity());
    this.combat = combat;
  }

  public Combat getCombat() {
    return combat;
  }
}
