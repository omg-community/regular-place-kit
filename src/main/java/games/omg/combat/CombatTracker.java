package games.omg.combat;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Entity;

public class CombatTracker {
  
  // make protected static methods for engaging entities in combat

  // then make public static methods for checking if entities are in combat

  // etc

  protected static Map<Entity, Combat> combatMap = new HashMap<>();

  protected static void engageEntityInCombat(Entity entity, Entity otherEntity) {

  }

  public static boolean isInCombat(Entity entity) {
    // TODO
    return false;
  }
}
