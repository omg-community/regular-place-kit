package games.omg.combat.combatants;

import org.bukkit.entity.Entity;

import games.omg.utils.EntityUtils;
import net.kyori.adventure.text.TextComponent;

public class EntityCombatant extends Combatant {
  private final Entity entity;

  public EntityCombatant(Entity entity) {
    this.entity = entity;
  }

  public Entity getEntity() {
    return entity;
  }

  @Override
  public TextComponent getName() {
    return EntityUtils.getDisplayNameComponent(entity);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof EntityCombatant) {
      EntityCombatant other = (EntityCombatant) obj;
      return other.entity.equals(entity);
    }
    return false;
  }
}
