package games.omg.testing;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import games.omg.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;

public class TestComponent implements Listener {
  
  @EventHandler
	public void onLogin(PlayerJoinEvent event) {
		Component killedBy = Component.text("You were killed by ").color(NamedTextColor.RED);

		Component nametag = PlayerUtils.getNametag(event.getPlayer(), true, false);

		nametag = nametag.hoverEvent(HoverEvent.showText(Component.text("This is a test hover event!")));

		Component message = Component.join(
			JoinConfiguration.noSeparators(),
			Component.text("» ").color(NamedTextColor.GRAY),
			killedBy,
			nametag
		);

		event.joinMessage(message);
	}
}
