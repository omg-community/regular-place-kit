package games.omg;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import games.omg.listeners.chat.ChatHandler;
import games.omg.listeners.test.SerializeTest;

public class Main extends JavaPlugin implements Listener {

	private static JavaPlugin plugin;

	public static void register(Listener ...listeners) {
		for (Listener listener : listeners) {
			plugin.getServer().getPluginManager().registerEvents(listener, plugin);
		}
	}

	@Override
	public void onEnable() {
		plugin = this;

		register(new ChatHandler());
		register(new SerializeTest());
	}

	@Override
	public void onDisable() {
		plugin = null;
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}
}
