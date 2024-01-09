package games.omg.utils;

import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;

public class ComponentUtils {

  // use fallback style instead in Component

  // public static Component colorizeUnsetParts(Component component, TextColor color) {
  //   if (component.style().color() == null) {
  //       component = component.color(color);
  //   }

  //   if (component.children().isEmpty()) {
  //       return component;
  //   }

  //   List<Component> coloredChildren = new ArrayList<>();
  //   for (Component child : component.children()) {
  //       coloredChildren.add(colorizeUnsetParts(child, color));
  //   }

  //   return component.children(coloredChildren);
  // }

  // use a join configuration
  
  // public static Component separateListWith(List<Component> items, Component separator) {
  //   int listSize = items.size();

  //   if (listSize == 0) return Component.empty();
  //   if (listSize == 1) return items.get(0);

  //   JoinConfiguration configuration = JoinConfiguration.builder()
  //       .separator(separator)
  //       .lastSeparator(separator)
  //       .lastSeparatorIfSerial(separator)
  //       .build();

  //   return Component.join(configuration, items);
  // }

  
}
