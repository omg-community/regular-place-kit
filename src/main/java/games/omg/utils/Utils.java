package games.omg.utils;

import org.apache.commons.text.WordUtils;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.DecimalFormat;
import java.util.*;

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

  public static String createEnglishList(List<String> items) {
    if (items.size() == 0) return "";
    if (items.size() == 1) return items.get(0);
    if (items.size() == 2) return items.get(0)+", and "+items.get(1);
    StringBuilder builder = new StringBuilder(items.get(0));
    for (int i = 1; i < items.size(); i++) builder.append(i == items.size() - 1 ? ", and" : ", ").append(items.get(i));
    return builder.toString();
  }

  public static void playSoundToAll(String sound, float volume, float pitch) {
    for (Player p : Bukkit.getOnlinePlayers()) {
      p.playSound(p.getLocation(), sound, volume, pitch);
    }
  }

  public static void centerItemsInInventory(Inventory i, List<ItemStack> itemStacks, int center) {
    int current = (int) (center - Math.floor(itemStacks.size() / 2));
    boolean skipCenter = itemStacks.size() % 2 == 0;
    for (ItemStack itemStack : itemStacks) {
      if (current == center && skipCenter) current++;
      i.setItem(current, itemStack);
      current++;
    }
  }

  public static ItemStack createSkull(UUID u) {
    ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
    if (u == null) return skull;
    skull.setDurability((short) 3);
    SkullMeta sm = (SkullMeta) skull.getItemMeta();
    sm.setOwningPlayer(Bukkit.getOfflinePlayer(u));
    skull.setItemMeta(sm);
    return skull;
  }

  public static int randInt(int min, int max) {
    Random rand = new Random();
    return rand.nextInt((max - min) + 1) + min;
  }

  public static ItemStack createItemStack(Material material, String name, List<String> lore, int amount, byte data, boolean unbreakable) {
    ItemStack itemStack=new ItemStack(material,amount,data);
    ItemMeta itemMeta=itemStack.getItemMeta();
    itemMeta.setUnbreakable(unbreakable);
    itemMeta.setDisplayName(name);
    itemMeta.setLore(lore);
    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  // TODO: this isnt useful because it doesn't use components.

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
