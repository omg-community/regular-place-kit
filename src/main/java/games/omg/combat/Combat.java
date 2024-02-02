package games.omg.combat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import games.omg.combat.combatants.Combatant;

public class Combat {

  private static final int COMBAT_TIMEOUT = 20 * 10;

  private static HashMap<Entity, Combat> combatMap = new HashMap<>();
  private static HashMap<String, Integer> combatTaskMap = new HashMap<>();

  public static Combat get(Entity entity) {
    return combatMap.get(entity);
  }

  public static boolean isInCombat(Entity entity) {
    return combatMap.containsKey(entity);
  }

  protected static void engageEntityInCombat(Entity entity, Entity otherEntity) {
    Combat combat = combatMap.get(entity);
    
    if (combat == null) {
      combat = new Combat(entity);
      combatMap.put(entity, combat);
    }

    combat.addCombatant(otherEntity);
  }

  //

  private Entity entity;

  // TODO: certain types of non-entity causes
  // ex. freezing, burning, lava, wither, poison
  // should keep the entity engaged in combat and should
  // also be able to start combat

  // maybe this means creating a Combatant class
  // that can be extended by EntityCombatant and NonEntityCombatant (?)
  
  private Set<Combatant> currentCombatants = new HashSet<>();
  private Set<Combatant> allCombatants = new HashSet<>();

  private int startTick = Bukkit.getCurrentTick();
  private int updateTick = startTick;
  private int endTick = 0;

  public Combat(Entity entity) {
    this.entity = entity;

    combatMap.put(entity, this);
  }

  // public Combat(Entity entity, List<Combatant> combatants) {
  //   this(entity);

  //   this.currentCombatants.addAll(combatants);
  //   this.allCombatants.addAll(combatants);
  // }

  // public Combat(Entity entity, Combatant combatant) {
  //   this(entity);

  //   this.currentCombatants.add(combatant);
  //   this.allCombatants.add(combatant);
  // }

  //

  public Entity getEntity() {
    return entity;
  }

  public Set<Combatant> getCurrentCombatants() {
    return currentCombatants;
  }

  public Set<Combatant> getAllCombatants() {
    return allCombatants;
  }

  public int getStartTick() {
    return startTick;
  }

  public int getEndTick() {
    return endTick;
  }

  public boolean hasEnded() {
    return endTick != 0;
  }

  public int getDuration() {
    if (hasEnded()) {
      return endTick - startTick;
    }
    return Bukkit.getCurrentTick() - startTick;
  }

  public int getRemainingTime() {
    if (hasEnded()) {
      return 0;
    }
    return Math.max(0, COMBAT_TIMEOUT - (Bukkit.getCurrentTick() - updateTick));
  }

  //

  public void addCombatant(Combatant combatant) {
    currentCombatants.add(combatant);
    allCombatants.add(combatant);

    updateTick = Bukkit.getCurrentTick();
    // TODO: remove this combatant after the timeout
  }

  public void addCombatant(Entity entity) {
    addCombatant(Combatant.from(entity));
  }

  public void removeCombatant(Combatant combatant) {
    currentCombatants.remove(combatant);
  }
}
