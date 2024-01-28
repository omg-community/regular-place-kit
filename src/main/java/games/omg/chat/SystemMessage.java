package games.omg.chat;

import games.omg.resources.Decorations;
import games.omg.resources.ServerColors;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

public class SystemMessage {

  private String icon = Decorations.SMALL_BULLET_POINT;
  private TextColor color = ServerColors.PRIMARY_COLOR;

  private String header;
  private Component message;

  public static SystemMessage from(String header, Component message) {
    SystemMessage systemMessage = new SystemMessage();
    systemMessage.header = header;
    systemMessage.message = message;
    return systemMessage;
  }

  public static SystemMessage from(String header, String message) {
    return from(header, Component.text(message));
  }

  public static SystemMessage message(String message) {
    return from(null, message);
  }

  public static SystemMessage message(Component message) {
    return from(null, message);
  }

  public SystemMessage header(String header) {
    this.header = header;
    return this;
  }

  public SystemMessage icon(String icon) {
    this.icon = icon;
    return this;
  }

  public SystemMessage color(TextColor color) {
    this.color = color;
    return this;
  }

  public String header() {
    return header;
  }

  public Component message() {
    return message;
  }

  public String icon() {
    return icon;
  }

  public TextColor color() {
    return color;
  }

  public Component build() {
    TextComponent headerComponent;

    if (header == null) {
      headerComponent = Component.empty();
    } else {
      headerComponent = Component
          .text(header + " " + icon + " ")
          .color(color);
    }

    return Component.join(
        JoinConfiguration.noSeparators(),
        headerComponent,
        message);
  }

  public void sendTo(Audience audience) {
    audience.sendMessage(build());
  }
}
