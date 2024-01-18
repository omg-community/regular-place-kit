package games.omg.utils;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Utils {

  // Check out Component.decorationIfAbsent() - seems very useful

  // TODO: check out the commented out code below and see if it's still relevant

  // what is the purpose of this..?

  // public static void teleport(CommandSender summoner, Player player, Object location) {
  //   teleport(summoner, player, location, false);
  //   if (location instanceof Location) player.teleport((Location) location);
  //   if (location instanceof Player) player.teleport(((Player) location).getLocation());
  // }

  // public static void teleport(CommandSender summoner, Player player, Object location, boolean bypass) {
  //   boolean selfTp = summoner.equals(player);
  // }

  // WHATS THE POINT ???

  // public static String buildStringFromArguments(String[] args, int index) {
  //   StringBuilder titleBuilder = new StringBuilder();

  //   for (int i = index; i < args.length; i++) {
  //     if (i != index) titleBuilder.append(" ");
  //     titleBuilder.append(args[i]);
  //   }

  //   return titleBuilder.toString().trim();
  // }

  // public static String buildStringFromArguments(String[] args) {
  //   return buildStringFromArguments(args, 0);
  // }

  /**
   * Gets the display name of an internal name.
   * 
   * This performs general cleanups such as removing prefixes and updating capitalization.
   * 
   * @param internalName The internal name
   * @return The display name
   */
  public static String getDisplayNameFromInternalName(String internalName) {
    String replacementPhase = internalName
      .replaceAll("ENTITY_", "")
      .replaceAll("_", " ");

    String formattingPhase = WordUtils.capitalizeFully(replacementPhase);
    
    String touchupPhase = formattingPhase
      .replaceAll("Tnt", "TNT");

    return touchupPhase;
  }

  /**
   * Plays a sound to all players.
   * 
   * @param sound The sound to play
   * @param volume The volume of the sound
   * @param pitch The pitch of the sound
   * 
   * @deprecated Use native Spigot methods instead.
   */
  public static void playSoundToAll(String sound, float volume, float pitch) {
    for (Player p : Bukkit.getOnlinePlayers()) {
      p.playSound(p.getLocation(), sound, volume, pitch);
    }
  }

  /**
   * Centers items around a specific slot in an inventory.
   * 
   * If the number of items is even, the center slot will be skipped.
   * 
   * @param i The inventory
   * @param itemStacks The items to center
   * @param center The slot to center around
   */
  public static void centerItemsInInventory(Inventory i, List<ItemStack> itemStacks, int center) {
    int current = (int) (center - Math.floor(itemStacks.size() / 2));
    boolean skipCenter = itemStacks.size() % 2 == 0;
    for (ItemStack itemStack : itemStacks) {
      if (current == center && skipCenter) current++;
      i.setItem(current, itemStack);
      current++;
    }
  }

  /**
   * Creates a skull with a specific owner.
   * 
   * @param u The UUID of the owner
   * @return The skull
   */
  public static ItemStack createSkull(UUID u) {
    ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
    if (u == null) return skull;
    skull.setDurability((short) 3);
    SkullMeta sm = (SkullMeta) skull.getItemMeta();
    sm.setOwningPlayer(Bukkit.getOfflinePlayer(u));
    skull.setItemMeta(sm);
    return skull;
  }

  /**
   * Generates a random integer between a minimum and maximum value.
   * 
   * @param min The minimum value
   * @param max The maximum value
   * @return The random integer
   */
  public static int randInt(int min, int max) {
    Random rand = new Random();
    return rand.nextInt((max - min) + 1) + min;
  }

  // TODO: rewrite with components

  // public static ItemStack createItemStack(Material material, String name, List<String> lore, int amount, byte data, boolean unbreakable) {
  //   ItemStack itemStack=new ItemStack(material,amount,data);
  //   ItemMeta itemMeta=itemStack.getItemMeta();
  //   itemMeta.setUnbreakable(unbreakable);
  //   itemMeta.setDisplayName(name);
  //   itemMeta.setLore(lore);
  //   itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
  //   itemStack.setItemMeta(itemMeta);
  //   return itemStack;
  // }

  // TODO: this isnt useful because it doesn't use components. rewrite with components

  // public static List<String> getSummary(String summary, int cutLength) {
  //   List<String> summarized = new ArrayList<>();
  //   String[] words = summary.split(" ");
  //   StringBuilder currentLine = new StringBuilder();
    
  //   for (String word : words) {
  //     if (currentLine.length() + word.length() <= cutLength) {
  //       currentLine.append(word).append(" ");
  //     } else {
  //       summarized.add(ChatColor.GRAY + currentLine.toString().trim());
  //       currentLine = new StringBuilder(word + " ");
  //     }
  //   }
    
  //   if (currentLine.length() > 0) {
  //     summarized.add(ChatColor.GRAY + currentLine.toString().trim());
  //   }
    
  //   return summarized;
  // }
}
