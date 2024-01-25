package games.omg.combat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Server.Spigot;
import org.bukkit.entity.Entity;

import games.omg.combat.combatants.Combatant;

public class Combat {

  private static HashMap<Entity, Combat> combatMap = new HashMap<>();

  public static Combat get(Entity entity) {
    return combatMap.get(entity);
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

  private long startTime = System.currentTimeMillis();
  private long endTime = 0;

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

  public long getStartTime() {
    return startTime;
  }

  public boolean hasEnded() {
    return endTime != 0;
  }

  public long getDuration() {
    if (hasEnded()) {
      return endTime - startTime;
    }
    return System.currentTimeMillis() - startTime;
  }

  //

  public void addCombatant(Combatant combatant) {
    currentCombatants.add(combatant);
    allCombatants.add(combatant);
  }

  public void addCombatant(Entity entity) {
    addCombatant(Combatant.from(entity));
  }

  public void removeCombatant(Combatant combatant) {
    currentCombatants.remove(combatant);
  }
}
