package games.omg.utils;

import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;

/**
 * A class which used to contain what I thought were useful methods for components.
 * 
 * I have since realized that these methods are not useful.
 * 
 * That is since most of these methods are already implemented in the Component class,
 * the JoinConfiguration class, and possibly even more classes which I have not yet
 * discovered.
 * 
 * @deprecated This class is no longer used.
 */
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
