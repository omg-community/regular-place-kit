package games.omg.chat;

import games.omg.resources.Decorations;
import games.omg.resources.ServerColors;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

public class SystemMessage {

  private String icon = Decorations.BULLET_POINT;
  private TextColor color = ServerColors.PRIMARY_COLOR;

  private String header;
  private String message;

  public SystemMessage(String header, String message) {
    this.header = header;
    this.message = message;
  }

  public SystemMessage icon(String icon) {
    this.icon = icon;
    return this;
  }

  public SystemMessage color(TextColor color) {
    this.color = color;
    return this;
  }

  public Component build() {
    TextComponent headerComponent = Component
        .text(header + " " + icon + " ")
        .color(color);

    return Component.join(
        JoinConfiguration.noSeparators(),
        headerComponent,
        Component.text(message));
  }

  public void sendTo(Audience audience) {
    audience.sendMessage(build());
  }
}
