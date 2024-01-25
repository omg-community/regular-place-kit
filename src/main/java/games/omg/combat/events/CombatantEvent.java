package games.omg.combat.events;

import games.omg.combat.Combat;
import games.omg.combat.combatants.Combatant;

public abstract class CombatantEvent extends CombatEvent {
  final protected Combatant combatant;

  public CombatantEvent(Combat combat, Combatant combatant) {
    super(combat);
    this.combatant = combatant;
  }

  public Combatant getCombatant() {
    return combatant;
  }
}
