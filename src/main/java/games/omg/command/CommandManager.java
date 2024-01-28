package games.omg.command;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.event.Listener;

import games.omg.Main;
import games.omg.chat.SystemMessage;
import games.omg.utils.ClassUtils;
import net.kyori.adventure.text.format.NamedTextColor;

public class CommandManager {

  // Load the commands
  public static final List<RegularCommand> COMMANDS = new ArrayList<>();

  static {
    COMMANDS.addAll(
        ClassUtils.instantiateClassesInPackageByType("games.omg.command.commands", RegularCommand.class));

    // register listeners for all commands that are also listeners
    COMMANDS.stream()
        .filter(command -> command instanceof Listener)
        .forEach(command -> Main.register((Listener) command));

    Main.getPlugin().getLogger().info("Loaded " + COMMANDS.size() + " commands.");

    try {
      final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

      bukkitCommandMap.setAccessible(true);
      CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

      // commandMap.register("seen", new CommandSeen("seen"));
      for (RegularCommand command : COMMANDS) {
        List<String> aliases = command.getAliases();
        if (aliases.size() == 0)
          continue;

        String firstAlias = aliases.get(0);
        List<String> otherAliases = aliases.subList(1, aliases.size());

        BukkitCommand bukkitCommand = new BukkitCommand(firstAlias) {
          @Override
          public boolean execute(org.bukkit.command.CommandSender sender, String label, String[] args) {
            try {
              if (!command.canUse(sender)) {
                SystemMessage
                    .from(command.getDisplayName(), "You don't have permission to use this command!")
                    .sendTo(sender);
                return true;
              }

              CommandMessage result = command.execute(sender, label, args);

              if (result != null) {
                result
                    .headerIfAbsent(command.getDisplayName())
                    .sendTo(sender);
              }
            } catch (Exception e) {
              e.printStackTrace();

              SystemMessage
                  .from("Error", "An error occurred while executing this command.")
                  .color(NamedTextColor.RED)
                  .sendTo(sender);
            }
            return true;
          }
        };

        bukkitCommand.setDescription(command.getDescription(null));
        // bukkitCommand.setUsage(command.getUsage(null));
        // bukkitCommand.setPermission(command.getPermission());
        bukkitCommand.setAliases(otherAliases);

        commandMap.register(Main.getPlugin().getName(), bukkitCommand);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
