package games.omg;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import games.omg.channeling.TeleportTools;
import games.omg.chat.ChatHandler;
import games.omg.command.CommandManager;

public class Main extends JavaPlugin implements Listener {

	private static JavaPlugin plugin;

	public static void register(Listener ...listeners) {
		for (Listener listener : listeners) {
			plugin.getServer().getPluginManager().registerEvents(listener, plugin);
		}
	}

	public static void registerCommand(String command, CommandExecutor executor) {
		plugin.getCommand(command).setExecutor(executor);
	}

	@Override
	public void onEnable() {
		plugin = this;

		register(new ChatHandler(), new TeleportTools());

		// registerCommand("tpa", new Tpa());	
		CommandManager commandManager = new CommandManager();
	}

	@Override
	public void onDisable() {
		plugin = null;
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}
}
