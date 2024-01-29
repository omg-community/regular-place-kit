package games.omg.server;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import games.omg.resources.Decorations;
import games.omg.resources.ServerColors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class MotdService implements Listener {
  
  @EventHandler
  public void onServerListPing(ServerListPingEvent event) {
    event.motd(
      Component.join(
        JoinConfiguration.noSeparators(),
        Component.text("regular").color(ServerColors.MOTD_ACCENT).decorate(TextDecoration.BOLD),
        Component.text(Decorations.SmallLetters.get(".place")).color(ServerColors.MOTD_ACCENT),
        Component.newline(),
        Component.text("v0.0.1").color(NamedTextColor.DARK_GRAY)
          .appendSpace()
          .append(Component.text(Decorations.SMALL_BULLET_POINT))
          .appendSpace(),
        Component.text("a regular place")
      )
    );
  }
}
