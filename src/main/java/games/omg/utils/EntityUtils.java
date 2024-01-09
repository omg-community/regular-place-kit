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

public class EntityUtils {

  public static String getDisplayName(DamageCause cause) {
    return Utils.getDisplayNameFromInternalName(cause.name());
  }

  public static String getDisplayName(EntityType type) {
    return Utils.getDisplayNameFromInternalName(type.name());
  }

  public static TextComponent getDisplayNameComponent(Entity entity) {
    // TODO: maybe not necessary if customName() is useable on Players
    if (entity instanceof Player) {
      String name = PlayerUtils.getPlayerName((Player) entity);

      // if the player has a custom name, maybe it should be signified with an asterisk or something
      // could be implemented in getPlayerName(), but we'll see how it goes

      return Component.text(name);
    }

    if (entity instanceof Nameable) {
      Nameable nameable = (Nameable) entity;
      
      // TODO: a little lengthy, is there a better way?
      TextComponent name = Component.text().append(nameable.customName()).build();

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

  public static String getDisplayName(Entity entity) {
    return PlainTextComponentSerializer.plainText().serialize(
      getDisplayNameComponent(entity)
    );
  }
}
