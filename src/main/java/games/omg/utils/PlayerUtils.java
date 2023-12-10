package games.omg.utils;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerUtils {
  public static String getPlayerName(Player player) {
    return getPlayerName((OfflinePlayer) player);
  }

  public static String getPlayerName(OfflinePlayer player) {
    // TODO: possibly nicknames in the future

    return player.getName();
  }
}
