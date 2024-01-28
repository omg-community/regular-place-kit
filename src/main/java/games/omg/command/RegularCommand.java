package games.omg.command;

import java.util.List;

import org.bukkit.command.CommandSender;

public abstract class RegularCommand {
  public abstract CommandMessage execute(CommandSender sender, String[] args);

  public abstract String getDisplayName();
  
  public String getDescription(CommandSender sender) {
    return null;
  }

  public abstract List<String> getAliases();
  
  public boolean canUse(CommandSender sender) {
    return true;
  }

  // public String getUsage();
  // public String getPermission();
}
