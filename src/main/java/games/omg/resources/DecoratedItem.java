package games.omg.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

/**
 * A class which provides utilities for creating decorated items.
 */
public class DecoratedItem {

  /**
   * Create a decorated item.
   * 
   * @param material The material of the item to create
   * @param header The header of the item
   * @param lore The lore of the item, which can be Strings or Components
   * @return The decorated item
   */
  public static ItemStack create(Material material, String header, List<Object> lore) {
    ItemStack item = new ItemStack(material);
    return decorate(item, header, lore);
  }

  /**
   * Create a decorated item.
   * 
   * @param material The material of the item to create
   * @param header The header of the item
   * @param lore The one-line lore of the item
   * @return The decorated item
   */
  public static ItemStack create(Material material, String header, String lore) {
    return create(material, header, Arrays.asList(lore));
  }

  /**
   * Create a decorated item.
   * 
   * @param material The material of the item to create
   * @param header The header of the item
   * @return The decorated item
   */
  public static ItemStack create(Material material, String header) {
    return create(material, header, new ArrayList<>());
  }

  /**
   * Decorates an item.
   * 
   * @param item The item to decorate
   * @param header The header of the item
   * @param lore The lore of the item, which can be Strings or Components
   * @return The decorated item
   */
  public static ItemStack decorate(ItemStack item, String header, List<Object> lore) {
    ItemMeta meta = item.getItemMeta();
    
    // Set the display name
    meta.displayName(
      Component.text(header)
        .decoration(TextDecoration.ITALIC, false)
        .color(ServerColors.PRIMARY_COLOR)
    );
    
    // Create the lore
    List<Component> createdLore = new ArrayList<>();
    for (Object line : lore) {
      if (line instanceof String) {
        createdLore.add(
          Component.text((String) line)
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.SECONDARY_COLOR)
        );
        continue;
      } else if (line instanceof Component) {
        createdLore.add((Component) line);
        continue;
      }
    }
    // Set the lore
    meta.lore(createdLore);

    // Finish the item
    item.setItemMeta(meta);
    return item;
  }

  /**
   * Decorates an item.
   * 
   * @param item The item to decorate
   * @param header The header of the item
   * @param lore The one-line lore of the item
   * @return The decorated item
   */
  public static ItemStack decorate(ItemStack item, String header, String lore) {
    return decorate(item, header, Arrays.asList(lore));
  }

  /**
   * Decorates an item.
   * 
   * @param item The item to decorate
   * @param header The header of the item
   * @return The decorated item
   */
  public static ItemStack decorate(ItemStack item, String header) {
    return decorate(item, header, new ArrayList<>());
  }
}
