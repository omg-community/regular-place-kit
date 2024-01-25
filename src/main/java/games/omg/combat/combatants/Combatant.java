package games.omg.combat.combatants;

import org.bukkit.entity.Entity;

import net.kyori.adventure.text.TextComponent;

/**
 * Represents a combatant.
 * 
 * A combatant is a type of engager that can keep an entity engaged in combat.
 * 
 * Combatants are not necessarily entities, but they can be.
 * Types of non-entity combatants include freezing, burning, lava, wither, poison, etc.
 */
public abstract class Combatant {
  long engagedAt = System.currentTimeMillis();
  
  abstract TextComponent getName();

  public long getEngagedAt() {
    return engagedAt;
  }

  //

  public static Combatant from(String name) {
    return new DamageCombatant(name);
  }

  public static Combatant from(Entity entity) {
    return new EntityCombatant(entity);
  }
}
