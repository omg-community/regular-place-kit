package games.omg.command.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import games.omg.Main;
import games.omg.channeling.ChannelingObject;
import games.omg.channeling.TeleportReason;
import games.omg.channeling.TeleportTools;
import games.omg.utils.StringUtils;
import games.omg.utils.TaskManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.md_5.bungee.api.ChatColor;

public class TeleportCommand implements Listener {

  static HashMap<Player, Player> teleportAsk = new HashMap<>();

  public TextComponent play(CommandSender sender, String label, String[] args) {
    Player p = (Player) sender;
    if (args.length < 1)
      return Component.text("You need to enter a player name. (/" + label.toLowerCase() + " <name>)");
    if (args.length > 1)
      return Component
          .text("You need to enter only a player name. (/" + label.toLowerCase() + " " + args[0] + ")");
    final Player player = Bukkit.getPlayer(args[0]);
    if (player == null || !player.isOnline())
      return Component.text("That is not a valid player.");
    if (player.equals(p))
      return Component.text("You can't teleport to yourself.");
    if (label.equalsIgnoreCase("teleport") || label.equalsIgnoreCase("tp") || label.equalsIgnoreCase("tpa")) {
      if (teleportAsk.containsKey(p)) {
        if (teleportAsk.get(p).equals(player))
          return Component.text("A request is already pending for that player.");
        // Kollections.SM(teleportAsk.get(p), "Teleport",
        // ChatColor.RESET+p.getName()+ChatColor.GRAY+" cancelled the teleport.");
        // Kollections.SM(p, "Teleport", "You cancelled the teleport to
        // "+ChatColor.RESET+teleportAsk.get(p).getName()+ChatColor.GRAY+".");
      }
      teleportAsk.put(p, player);
      TaskManager.initTask(p, "teleport",
          Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
            public void run() {
              teleportAsk.remove(p);
            }
          }, 20 * 60));
      // Kollections.SM(player, "Teleport",
      // ChatColor.RESET+p.getName()+ChatColor.GRAY+" wants to teleport to
      // you.\nAccept with (/tpaccept "+p.getName()+")");
      return Component
          .text("You asked to teleport to " + ChatColor.RESET + player.getName() + ChatColor.GRAY + ".");
    }
    if (!(teleportAsk.containsKey(player) && teleportAsk.get(player).equals(p)))
      return Component.text("That player hasn't asked to teleport to you.");
    teleportAsk.remove(player);
    UUID uuid = p.getUniqueId();
    ChannelingObject channel = new ChannelingObject(TeleportTools.getChannelingTimesBetweenPlayers(p, player),
        new Runnable() {
          @Override
          public void run() {
            if (!player.isOnline())
              return;
            Player teleportPlayer = Bukkit.getPlayer(uuid);
            if (teleportPlayer == null || !teleportPlayer.isOnline()) {
              // Kollections.SM(player,"Teleport","That player is gone.");
              return;
            }
            // Kollections.SM(player,"Teleport","Teleported to
            // "+ChatColor.RESET+teleportPlayer.getName()+ChatColor.GRAY+".");
            // Kollections.SM(teleportPlayer, "Teleport",
            // ChatColor.RESET+player.getName()+ChatColor.GRAY+" has arrived.");
            player.teleport(teleportPlayer);
          }
        }, new Runnable() {
          @Override
          public void run() {
            Player teleportPlayer = Bukkit.getPlayer(uuid);
            if (teleportPlayer == null)
              return;
            // Kollections.SM(teleportPlayer, "Teleport",
            // ChatColor.RESET+player.getName()+ChatColor.GRAY+" stopped channeling.");
          }
        });
    TeleportReason result = TeleportTools.channelTeleport(player, channel);
    if (result == TeleportReason.ALREADY_TELEPORTING) {
      // Kollections.SM(player, "Teleport",
      // ChatColor.RESET+p.getName()+ChatColor.GRAY+" accepted your teleport request,
      // but you are already channeling somewhere!");
      return Component.text("That player is already teleporting somewhere.");
    }
    // Kollections.SM(player, "Teleport",
    // ChatColor.RESET+p.getName()+ChatColor.GRAY+" accepted your teleport
    // request.");
    return Component.text("Accepted " + ChatColor.RESET + player.getName() + ChatColor.GRAY + "'s teleport request."
        + (result == TeleportReason.INSTANT_TELEPORT ? ""
            : " They will arrive in " + ChatColor.RESET + StringUtils.getTextTime(channel.getStartingTime())
                + ChatColor.GRAY + " or longer."));
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent e) {
    teleportAsk.remove(e.getPlayer());
  }

  public List<String> getAliases() {
    return Arrays.asList("tp", "tpa", "teleport", "tpaccept", "teleportaccept", "tpaaccept");
  }

  public boolean canUse(CommandSender sender) {
    return true;
  }

  public boolean doesSuggestedCommandHaveSpace() {
    return true;
  }

  public TextComponent getDescription(CommandSender sender) {
    return Component.text("Teleport to a player.");
  }

  public boolean requireOp() {
    return false;
  }
}