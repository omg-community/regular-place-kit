package games.omg.command;

import games.omg.chat.SystemMessage;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public class CommandMessage extends SystemMessage {

  private SystemMessage systemMessage;
  private Component component;

  public static CommandMessage from(Component component) {
    CommandMessage commandMessage = new CommandMessage();
    commandMessage.component = component;
    commandMessage.systemMessage = SystemMessage.message(component);
    return commandMessage;
  }

  public static CommandMessage from(SystemMessage systemMessage) {
    CommandMessage commandMessage = new CommandMessage();
    commandMessage.systemMessage = systemMessage;
    return commandMessage;
  }

  public static CommandMessage from(String message) {
    return from(Component.text(message));
  }

  public boolean wasComponent() {
    return component != null;
  }

  public CommandMessage headerIfAbsent(String header) {
    if (systemMessage.header() == null) {
      systemMessage.header(header);
    }
    return this;
  }

  public void sendTo(Audience audience) {
    systemMessage.sendTo(audience);
  }
}