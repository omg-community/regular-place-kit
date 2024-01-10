package games.omg.listeners.chat;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import games.omg.utils.PlayerUtils;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;

public class ChatHandler implements ChatRenderer, Listener {

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

  @EventHandler
  public void onChat(AsyncChatEvent event) {
    event.renderer(this);
  }
}
