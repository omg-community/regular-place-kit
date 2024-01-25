package games.omg.combat.combatants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class DamageCombatant extends Combatant {
  private final String name;

  public DamageCombatant(String name) {
    this.name = name;
  }

  @Override
  public TextComponent getName() {
    return Component.text(name);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof DamageCombatant) {
      DamageCombatant other = (DamageCombatant) obj;
      return other.name.equals(name);
    }
    return false;
  }
}
