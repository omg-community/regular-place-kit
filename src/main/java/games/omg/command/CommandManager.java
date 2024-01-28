package games.omg.command;

import java.util.ArrayList;
import java.util.List;

import games.omg.Main;
import games.omg.utils.ClassUtils;

public class CommandManager {

  // Load the commands
  public static final List<RegularCommand> COMMANDS = new ArrayList<>();
  static {
    COMMANDS.addAll(
      ClassUtils.instantiateClassesInPackageByType("games.omg.command.commands", RegularCommand.class)
    );
    Main.getPlugin().getLogger().info("Loaded " + COMMANDS.size() + " commands.");
  }
}
