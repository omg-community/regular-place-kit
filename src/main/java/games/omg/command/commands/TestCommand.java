package games.omg.command.commands;

import java.util.Arrays;
import java.util.List;

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
  public List<String> getAliases() {
    return Arrays.asList("test1", "test2");
  }
}
