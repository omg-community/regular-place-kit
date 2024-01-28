package games.omg.command.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;

import games.omg.command.CommandMessage;
import games.omg.command.RegularCommand;

public class TestCommand extends RegularCommand {

  @Override
  public CommandMessage execute(CommandSender sender, String label, String[] args) {
    return CommandMessage.from("Complete!");
  }

  @Override
  public String getDisplayName() {
    return "Test Command";
  }

  @Override
  public boolean canUse(CommandSender sender) {
    return true;
  }

  @Override
  public List<String> getAliases() {
    return Arrays.asList("test1", "test2");
  }
}
