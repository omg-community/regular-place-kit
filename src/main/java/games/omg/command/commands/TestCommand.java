package games.omg.command.commands;

import org.bukkit.command.CommandSender;

import games.omg.command.RegularCommand;

public class TestCommand extends RegularCommand {

  @Override
  public void execute(CommandSender sender, String[] args) {
    sender.sendMessage("Test command executed!");
  }

  @Override
  public String getDisplayName() {
    return "Test Command";
  }

  @Override
  public String[] getAliases() {
    return new String[]{ "test1", "test2" };
  }
}
