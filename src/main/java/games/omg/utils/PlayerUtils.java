package games.omg.utils;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerUtils {
  public static String getPlayerName(Player player) {
    return getPlayerName((OfflinePlayer) player);
  }

  public static String getPlayerName(OfflinePlayer player) {
    // TODO: possibly nicknames in the future

    return player.getName();
  }

  public static String getPlayerName(CommandSender sender)  {
    if (sender instanceof OfflinePlayer) {
      return getPlayerName((OfflinePlayer) sender);
    }
    return sender.getName();
  }

  // for commands where you can enter partial usernames and search for online players
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
