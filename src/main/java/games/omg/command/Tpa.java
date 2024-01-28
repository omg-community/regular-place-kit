package games.omg.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import games.omg.channeling.ChannelingObject;
import games.omg.channeling.ExtendedChannelTime;
import games.omg.channeling.TeleportTools;

public class Tpa implements CommandExecutor {
  
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    List<ExtendedChannelTime> channelTimes = TeleportTools.getChannelingTimesBetweenPoints(((Player) sender).getLocation(), ((Player) sender).getLocation().add(100, 100, 100));
    TeleportTools.channelTeleport(((Player) sender), new ChannelingObject(channelTimes, () -> {
      sender.sendMessage("Teleported!");
    }, () -> {
      sender.sendMessage("Teleportation interrupted!");
    }));
    return true;
  }
}
