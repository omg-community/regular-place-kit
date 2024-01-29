package games.omg.utils;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import games.omg.resources.ServerColors;
import games.omg.resources.ServerStrings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;

/**
 * A class which contains utility methods for players.
 */
public class PlayerUtils {

  // We don't color the nametag here because we want to be able to color it differently for different teams
  // Or various different colors for various different reasons
  /**
   * Gets the nametag component of a player.
   * 
   * @param player The player
   * @return The nametag component of the player
   */
  public static Component getNametag(OfflinePlayer player, boolean includePrefix, boolean colorName) {
    String name = PlayerUtils.getPlayerName(player);

    TextComponent username = Component.text(name);

    Component nametag;
    if (includePrefix) {
      TextComponent bulletPoint = Component.text(ServerStrings.USERNAME_PREFIX).color(ServerColors.OWNER_ROLE_COLOR);
      
      nametag = Component.join(
        JoinConfiguration.separator(Component.space()),
        bulletPoint,
        username
      );
    } else {
      nametag = username;
    }

    if (colorName) {
      nametag = nametag.color(ServerColors.OWNER_ROLE_COLOR);
    }

    // Later, apply fallback style
    // combined = combined.applyFallbackStyle(Style.style(NamedTextColor.YELLOW)); // Example fallback color

    return nametag;
  }

  /**
   * Gets the name of a player.
   * 
   * We use this method instead of {@link Player#getName()}
   * in the case of nicknames in the future.
   * 
   * @param player The player
   * @return The name of the player
   */
  public static String getPlayerName(Player player) {
    return getPlayerName((OfflinePlayer) player);
  }

  /**
   * Gets the name of an offline player.
   * 
   * We use this method instead of {@link OfflinePlayer#getName()}
   * in the case of nicknames in the future.
   * 
   * @param player The offline player
   * @return The name of the offline player
   */
  public static String getPlayerName(OfflinePlayer player) {
    // TODO: possibly nicknames in the future

    return player.getName();
  }

  /**
   * Gets the name of a command sender.
   * 
   * @param sender The command sender
   * @return The name of the command sender
   */
  public static String getPlayerName(CommandSender sender)  {
    if (sender instanceof OfflinePlayer) {
      return getPlayerName((OfflinePlayer) sender);
    }
    return sender.getName();
  }

  // for commands where you can enter partial usernames and search for online players
  // eventually, this should also be added to the proxy server to also search for players in other servers and possibly teleport to them
  /**
   * Gets the player which matches the specified query.
   * 
   * This method will first look for an exact match for the query.
   * If no exact match is found, it will look for a partial match.
   * 
   * If no match is found, it will return null.
   * 
   * @param query The query
   * @return The player which matches the query
   */
  public static Player getSearchedPlayer(String query) {
    Collection<? extends Player> players = Bukkit.getOnlinePlayers();
    query = query.toLowerCase();

    // exact matches will always be returned immediately,
    // otherwise we store the next best matches and return them if no exact match is found
    Player partialMatch = null;
    Player displayExactMatch = null;
    Player displayPartialMatch = null;
    
    // look for an exact match first
    for (Player player : players) {
      String name = player.getName().toLowerCase();
      String displayName = getPlayerName(player).toLowerCase();

      if (name.equals(query)) {
        // an exact match was hit for the username - return it
        return player;
      } else if (name.startsWith(query)) {
        partialMatch = player;
      }

      if (displayName.equals(query)) {
        displayExactMatch = player;
      } else if (displayName.startsWith(query)) {
        displayPartialMatch = player;
      }
    }

    // if no exact match was found, return the next best matches
    if (partialMatch != null) return partialMatch;
    if (displayExactMatch != null) return displayExactMatch;
    if (displayPartialMatch != null) return displayPartialMatch;
    return null;
  }
}
