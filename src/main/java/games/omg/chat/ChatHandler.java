package games.omg.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import games.omg.resources.ServerColors;
import games.omg.utils.PlayerUtils;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;

public class ChatHandler implements ChatRenderer, Listener {

	/**
	 * An EventHandler method which is called when a player joins the server.
	 * 
	 * @param event The PlayerJoinEvent
	 */
  @EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Component nametag = PlayerUtils.getNametag(event.getPlayer(), true, false).applyFallbackStyle(ServerColors.SECONDARY_COLOR);

		// nametag = nametag.hoverEvent(HoverEvent.showText(Component.text("This is a test hover event!")));

		Component message = Component.join(
			JoinConfiguration.separator(Component.space()),
			Component.text("Join").color(ServerColors.PRIMARY_COLOR),
			nametag
		);

		// event.joinMessage(
		// 	PlayerUtils.getNametag(event.getPlayer())
		// 	// .colorIfAbsent(NamedTextColor.LIGHT_PURPLE)
		// 	.applyFallbackStyle(Style.style(NamedTextColor.LIGHT_PURPLE))
		// );

		event.getPlayer().playerListName(nametag);
		event.joinMessage(message);
	}

	/**
	 * An EventHandler method which is called when a player quits the server.
	 * 
	 * @param event The PlayerQuitEvent
	 */
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Component message = Component.join(
			JoinConfiguration.separator(Component.space()),
			Component.text("Quit").color(NamedTextColor.DARK_GRAY),
			PlayerUtils.getNametag(event.getPlayer(), true, false)
				.applyFallbackStyle(ServerColors.SECONDARY_COLOR)
		);

		event.quitMessage(message);
	}

	/**
	 * The render method which is called when a player sends a chat message.
	 * 
	 * From ChatRenderer.
	 * Overrides the default chat renderer.
	 */
  @Override
  public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience audience) {
    return Component.join(
			JoinConfiguration.separator(Component.space()),
			PlayerUtils.getNametag(source, true, true),
			message
		);
  }

	/**
	 * An EventHandler method which is called when a player sends a chat message.
	 * 
	 * @param event The AsyncChatEvent
	 */
  @EventHandler
  public void onChat(AsyncChatEvent event) {
    event.renderer(this);
  }
}
