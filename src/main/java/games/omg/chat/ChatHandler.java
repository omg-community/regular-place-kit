package games.omg.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import games.omg.utils.PlayerUtils;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class ChatHandler implements ChatRenderer, Listener {

	/**
	 * An EventHandler method which is called when a player joins the server.
	 * 
	 * @param event The PlayerJoinEvent
	 */
  @EventHandler
	public void onLogin(PlayerJoinEvent event) {
		Component killedBy = Component.text("You were killed by ").color(NamedTextColor.RED);

		Component nametag = PlayerUtils.getNametag(event.getPlayer());

		nametag = nametag.hoverEvent(HoverEvent.showText(Component.text("This is a test hover event!")));

		Component message = Component.join(
			JoinConfiguration.noSeparators(),
			Component.text("» ").color(NamedTextColor.GRAY),
			killedBy,
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
	 * The render method which is called when a player sends a chat message.
	 * 
	 * From ChatRenderer.
	 * Overrides the default chat renderer.
	 */
  @Override
  public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience audience) {
    return Component.text("[")
      .append(Component.text("Custom", NamedTextColor.GREEN))
      .append(Component.text("] "))
      .append(sourceDisplayName)
      .append(Component.text(": "))
      .decorate(TextDecoration.BOLD)
      .append(message);
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
