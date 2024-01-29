package games.omg.server;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import games.omg.Main;
import games.omg.resources.Decorations;
import games.omg.resources.ServerColors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class MotdService implements Listener {
  
  @EventHandler
  public void onServerListPing(ServerListPingEvent event) {
    Component message;

    if (RunService.isDevelopment()) {
      message = Component.join(
        JoinConfiguration.separator(Component.space()),
        Component.text("development test server"),
        Component.text(Decorations.PENCIL)
      );
    } else {
      message = Component.text("a regular place");
    }
    
    event.motd(
      Component.join(
        JoinConfiguration.noSeparators(),
        
        Component.text(Decorations.FLOWER).color(ServerColors.MOTD_ACCENT),
        Component.space(),
        Component.text("regular.place").color(ServerColors.MOTD_ACCENT).decorate(TextDecoration.BOLD),
        Component.space(),
        Component.text(Decorations.FLOWER).color(ServerColors.MOTD_ACCENT),

        Component.newline(),
        
        Component.text("v")
          .append(Component.text(Main.getPlugin().getPluginMeta().getVersion()))
          .appendSpace()
          .append(Component.text(Decorations.SMALL_BULLET_POINT))
          .appendSpace()
          .color(NamedTextColor.DARK_GRAY),
        
        message
      )
    );
  }
}
