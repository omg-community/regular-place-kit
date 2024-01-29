package games.omg.utils;

import org.bukkit.Nameable;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

/**
 * A class which contains utility methods for entities.
 */
public class EntityUtils {

  /**
   * Gets the display name of a {@link DamageCause}.
   * 
   * This will call {@link Utils#getDisplayNameFromInternalName(String)}
   * on the name of the DamageCause.
   * 
   * @param cause The DamageCause
   * @return The display name of the DamageCause
   */
  public static String getDisplayName(DamageCause cause) {
    return Utils.getDisplayNameFromInternalName(cause.name());
  }

  /**
   * Gets the display name of an {@link EntityType}.
   * 
   * This will call {@link Utils#getDisplayNameFromInternalName(String)}
   * on the name of the EntityType.
   * 
   * @param type The EntityType
   * @return The display name of the EntityType
   */
  public static String getDisplayName(EntityType type) {
    return Utils.getDisplayNameFromInternalName(type.name());
  }

  /**
   * Gets the display name component of an {@link Entity}.
   * 
   * If the provided entity is a Player, it will return the player's name
   * from {@link PlayerUtils#getPlayerName(Player)}.
   * 
   * Otherwise, if the entity was named, it will return the custom name of the entity.
   * 
   * Otherwise, it will return the display name of the entity's EntityType.
   * For pets, it will also include the owner's name.
   * 
   * @param entity The entity
   * @return The display name component of the entity
   */
  public static Component getDisplayNameComponent(Entity entity) {
    if (entity instanceof Player) {
      String name = PlayerUtils.getPlayerName((Player) entity);

      // if the player has a custom name, maybe it should be signified with an asterisk or something
      // could be implemented in getPlayerName(), but we'll see how it goes

      return Component.text(name);
    }

    if (entity instanceof Nameable) {
      Component name = ((Nameable) entity).customName();

      if (name != null) {
        // return the name component in italics (to make it obvious that it's a custom name)
        return name.decorate(TextDecoration.ITALIC);
      }
    }

    TextComponent entityComponent = Component.text(getDisplayName(entity.getType()));

    if (entity instanceof Tameable) {
      Tameable tameable = (Tameable) entity;

      if (tameable.isTamed()) {
        // get the owner's name
        AnimalTamer owner = tameable.getOwner();

        if (owner instanceof OfflinePlayer) {
          OfflinePlayer offlinePlayer = (OfflinePlayer) owner;

          String ownerName = PlayerUtils.getPlayerName(offlinePlayer);

          // return "Player's Pet"
          return Component.text(StringUtils.makePossessive(ownerName)).append(entityComponent);
        }
      }
    }

    return entityComponent;
  }

  /**
   * If the provided entity is a Player, it will return the player's name
   * from {@link PlayerUtils#getPlayerName(Player)}.
   * 
   * Otherwise, if the entity was named, it will return the custom name of the entity.
   * 
   * Otherwise, it will return the display name of the entity's EntityType.
   * For pets, it will also include the owner's name.
   * 
   * @param entity The entity
   * @return The display name of the entity
   */
  public static String getDisplayName(Entity entity) {
    return PlainTextComponentSerializer.plainText().serialize(
      getDisplayNameComponent(entity)
    );
  }
}
