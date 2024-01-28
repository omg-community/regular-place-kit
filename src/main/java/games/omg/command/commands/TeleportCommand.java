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
import games.omg.chat.SystemMessage;
import games.omg.command.CommandMessage;
import games.omg.command.RegularCommand;
import games.omg.utils.PlayerUtils;
import games.omg.utils.StringUtils;
import games.omg.utils.TaskManager;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

public class TeleportCommand extends RegularCommand implements Listener {

  static HashMap<Player, Player> teleportAsk = new HashMap<>();

  public CommandMessage execute(CommandSender sender, String label, String[] args) {
    Player p = (Player) sender;
    if (args.length < 1)
      return CommandMessage.from("You need to enter a player name. (/" + label.toLowerCase() + " <name>)");
    if (args.length > 1)
      return CommandMessage
          .from("You need to enter only a player name. (/" + label.toLowerCase() + " " + args[0] + ")");
    final Player player = PlayerUtils.getSearchedPlayer(args[0]);
    if (player == null || !player.isOnline())
      return CommandMessage.from("That is not a valid player.");
    if (player.equals(p))
      return CommandMessage.from("You can't teleport to yourself.");
    if (label.equalsIgnoreCase("teleport") || label.equalsIgnoreCase("tp") || label.equalsIgnoreCase("tpa")) {
      if (teleportAsk.containsKey(p)) {
        if (teleportAsk.get(p).equals(player))
          return CommandMessage.from("A request is already pending for that player.");
        SystemMessage.from("Teleport",
            ChatColor.RESET + p.getName() + ChatColor.GRAY + " cancelled the teleport.").sendTo(teleportAsk.get(p));
        SystemMessage
            .from("Teleport",
                "You cancelled the teleport to " + ChatColor.RESET + teleportAsk.get(p).getName() + ChatColor.GRAY
                    + ".")
            .sendTo(p);
      }
      teleportAsk.put(p, player);
      TaskManager.initTask(p, "teleport",
          Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
            public void run() {
              teleportAsk.remove(p);
            }
          }, 20 * 60));
      SystemMessage.from("Teleport",
          ChatColor.RESET + p.getName() + ChatColor.GRAY + " wants to teleport to you.\nAccept with (/tpaccept "
              + p.getName() + ")")
          .sendTo(player);
      return CommandMessage
          .from("You asked to teleport to " + ChatColor.RESET + player.getName() + ChatColor.GRAY + ".");
    }
    if (!(teleportAsk.containsKey(player) && teleportAsk.get(player).equals(p)))
      return CommandMessage.from("That player hasn't asked to teleport to you.");
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
            SystemMessage
                .from("Teleport", "Teleported to " + ChatColor.RESET + teleportPlayer.getName() + ChatColor.GRAY + ".")
                .sendTo(player);
            SystemMessage.from("Teleport",
                ChatColor.RESET + player.getName() + ChatColor.GRAY + " has arrived.").sendTo(teleportPlayer);
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
      return CommandMessage.from("That player is already teleporting somewhere.");
    }
    // Kollections.SM(player, "Teleport",
    // ChatColor.RESET+p.getName()+ChatColor.GRAY+" accepted your teleport
    // request.");
    return CommandMessage
        .from("Accepted " + ChatColor.RESET + player.getName() + ChatColor.GRAY + "'s teleport request."
            + (result == TeleportReason.INSTANT_TELEPORT ? ""
                : " They will arrive in about " + ChatColor.RESET + StringUtils.getTextTime(channel.getStartingTime())
                    + ChatColor.GRAY + "."));
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent e) {
    teleportAsk.remove(e.getPlayer());
  }

  @Override
  public List<String> getAliases() {
    return Arrays.asList("tp", "tpa", "teleport", "tpaccept", "teleportaccept", "tpaaccept");
  }

  @Override
  public boolean canUse(CommandSender sender) {
    return true;
  }

  @Override
  public String getDescription(CommandSender sender) {
    return "Teleport to a player.";
  }

  @Override
  public String getDisplayName() {
    return "Teleport";
  }
}