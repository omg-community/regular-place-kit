package games.omg;

import org.bukkit.plugin.java.JavaPlugin;

import games.omg.utils.PlayerUtils;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Main extends JavaPlugin implements Listener {
	
	@EventHandler
	public void onLogin(PlayerJoinEvent event) {
		event.joinMessage(
			PlayerUtils.getNametag(event.getPlayer())
			// .colorIfAbsent(NamedTextColor.LIGHT_PURPLE)
			.applyFallbackStyle(Style.style(NamedTextColor.LIGHT_PURPLE))
		);
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

}
