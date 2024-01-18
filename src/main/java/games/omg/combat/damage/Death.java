package games.omg.combat.damage;

import java.util.List;

import org.bukkit.entity.Entity;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

/**
 * An object which represents a death.
 */
public class Death {

    final private static TextColor KILLED_COLOR = NamedTextColor.RED;
    final private static TextColor CAUSE_COLOR = NamedTextColor.YELLOW;
    final private static TextColor KILLER_COLOR = NamedTextColor.RED;

    final private static TextColor BASE_DEATH_MESSAGE_COLOR = NamedTextColor.GRAY;
    final private static Component DEATH_MESSAGE_PREFIX = Component.text(" » ").color(BASE_DEATH_MESSAGE_COLOR);
    final private static Component KILLED_BY_COMPONENT = Component.text(" killed by ").color(BASE_DEATH_MESSAGE_COLOR);

    final private static Component UNKNOWN_COMPONENT = Component.text("?¿?¿?");

    private Entity killed;
    private Entity killer;
    private List<Entity> assists;
    private List<String> causes;

    public Death(Entity killed, Entity killer, List<Entity> assists, List<String> causes) {
      this.killed = killed;
      this.killer = killer;
      this.assists = assists;
      this.causes = causes;
    }

    public Entity getKilled() {
      return killed;
    }

    public Entity getKiller() {
      return killer;
    }

    public List<Entity> getAssists() {
      return assists;
    }

    public List<String> getCauses() {
      return causes;
    }

    // public Component getDeathMessage(Entity killed) {
    //   boolean hasKiller = killer != null;
    //   boolean hasCauses = !causes.isEmpty();
    //   boolean hasAssists = !assists.isEmpty();

    //   Component killedComponent = EntityUtils.getDisplayNameComponent(killed);

    //   Component causeComponent = null;
    //   if (hasCauses) {
    //     causeComponent = Component.text(StringUtils.separateListWith(causes, ", "));
    //   }

    //   Component killerComponent = null;
    //   if (hasKiller) {
    //     killerComponent = EntityUtils.getDisplayNameComponent(killer);
    //   }
      
    //   TextComponent assistsComponent = null;
    //   if (hasAssists) {
    //     Component hoverComponent = Component.empty();

    //     for (int i = 0; i < assists.size(); i++) {
    //       Entity assist = assists.get(i);
    //       Component assistComponent = EntityUtils.getDisplayNameComponent(assist);

    //       if (i != 0) {
    //         hoverComponent = hoverComponent.append(Component.newline());
    //       }

    //       hoverComponent = hoverComponent.append(assistComponent);
    //     }

    //     assistsComponent = Component.text(" + " + assists.size());
    //   }

    //   if (!hasKiller) {
    //     // No killer, we can just return the death message now with causes
    //     return DEATH_MESSAGE_PREFIX.append(killedComponent).append(KILLED_BY_COMPONENT).append(causeComponent);
    //     // if (hasCauses) {
    //     //   // No killer, causes
    //     //   // return ChatColor.GRAY + " » " + deadName + ChatColor.RESET + ChatColor.GRAY + " killed by " + causeMod;
    //     // } else {
    //     //   // No killer, no causes
    //     //   // return ChatColor.GRAY + " » " + deadName + ChatColor.RESET + ChatColor.GRAY + " killed by " + CAUSE_COLOR + "?¿?¿?";
    //     // }
    //   }

    //   Component killerName = EntityUtils.getDisplayNameComponent(killer).color(KILLER_COLOR);

    //   if (!hasAssists) {
    //     // No assists, we can just return the death message now with the killer name and causes
    //     Component deathMessage = DEATH_MESSAGE_PREFIX.append(killedComponent).append(KILLED_BY_COMPONENT).append(killerName);
    //     if (hasCauses) {

    //     }
    //     return DEATH_MESSAGE_PREFIX.append(killedComponent).append(KILLED_BY_COMPONENT).append(killerName);
    //     // return ChatColor.GRAY + " » " + deadName + ChatColor.RESET + ChatColor.GRAY + " killed by " + killerName;
    //   }

    //   if (!assists.isEmpty())
    //     killerName = killerName + " + " + assists.size();

    //   if (causes.isEmpty()) {
    //     // Killer, no causes
    //     return ChatColor.GRAY + " » " + killedComponent + ChatColor.RESET + ChatColor.GRAY + " killed by " + killerName;
    //   }
    //   // Killer, causes
    //   return ChatColor.GRAY + " » " + killedComponent + ChatColor.RESET + ChatColor.GRAY + " killed by " + killerName
    //       + ChatColor.RESET + ChatColor.GRAY + " with " + causeMod;
    // }
  }