package games.omg.channeling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import games.omg.Main;
import games.omg.menus.InventoryMenu;
import games.omg.resources.Decorations;
import games.omg.utils.StringUtils;
import games.omg.utils.TaskManager;
import games.omg.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatColor;

public class TeleportTools implements Listener {
  final private static String menuTitle = "Teleporting..";
  private static HashMap<Player, ChannelingObject> channels = new HashMap<>();

  public static TeleportReason channelTeleport(Player p, ChannelingObject channelingObject) {
    if (p.getGameMode() == GameMode.CREATIVE || channelingObject.getStartingTime() <= 0) {
      channelingObject.getFinishRunnable().run();
      return TeleportReason.INSTANT_TELEPORT;
    }

    if (channelingObject.getStartingTime() < 10)
      channelingObject.addChannelTime(new ExtendedChannelTime(10 - channelingObject.getStartingTime(), "Roadblock",
          Material.PACKED_ICE, "All channels must either be instant or last at least 10 seconds."));
    channels.put(p, channelingObject);

    if (TaskManager.isTaskRunning(p, "teleportation"))
      return TeleportReason.ALREADY_TELEPORTING;

    InventoryMenu channelingMenu = new InventoryMenu(p, "ChannelingMenu", 9 * 3, menuTitle,
        new InventoryMenu.InventoryClickHandler() {

          private void updateChannelingInventory(Inventory channelingInventory) {
            if (!channels.containsKey(p))
              return;

            channelingInventory.clear();

            ChannelingObject channel = channels.get(p);
            int center = (channelingInventory.getSize() - 1) / 2;
            int current = (int) (center - Math.floor(channel.getChannelTimes().size() / 2));
            boolean skipCenter = channel.getChannelTimes().size() % 2 == 0;

            for (ExtendedChannelTime ect : channel.getChannelTimes()) {
              if (current == center && skipCenter)
                current++;

              long currentTime = ect.getCurrentTime();
              int shownTime = currentTime > 64 ? 64 : (currentTime <= 0 ? 1 : (int) currentTime);
              ItemStack displayItem = new ItemStack(ect.getDisplayItem(), shownTime);// new
                                                                                     // ItemStack(ect.getDisplayItem(),shownTime);
              ItemMeta itemMeta = displayItem.getItemMeta();
              itemMeta.setDisplayName(
                  ChatColor.RESET + "Teleporting in " + StringUtils.getShortenedTime(channel.getCurrentTime()) + "..");
              List<String> lore = new ArrayList<>(Arrays.asList(ChatColor.BLUE + ect.getReason() + ChatColor.WHITE
                  + " (" + StringUtils.getShortenedTime(currentTime) + ")"));
              lore.addAll(Utils.getSummary(ect.getDescription(), 29));
              itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS);
              itemMeta.setLore(lore);
              displayItem.setItemMeta(itemMeta);

              channelingInventory.setItem(current, displayItem);
              p.updateInventory();
              
              current++;
            }
          }

          @Override
          public void inventoryInteractEvent(InventoryInteractEvent e) {
            e.setCancelled(true);
          }

          @Override
          public void create(Inventory i) {
            updateChannelingInventory(i);
            TaskManager.initTask(p, "teleportation",
                Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
                  public void run() {
                    if (!channelingObject.next()) {
                      teleportComplete(p);
                      return;
                    }
                    updateChannelingInventory(i);
                  }
                }, 20, 20));
          }

          @Override
          public void close(Inventory i) {
            if (TaskManager.isTaskRunning(p, "teleportation")) {
              teleportInterrupted(p);
              p.sendMessage(Decorations.createSystemMessage(NamedTextColor.BLUE, "Teleport",
                  Component.text("You cancelled the teleport.")));
            }
          }
        });

    // ScoreboardTools.setGlobalPrefix(p, " ➱");

    // Main.updateScoreboard();
    return TeleportReason.PREPARING_TELEPORT;
  }

  private static void teleportFinished(Player p, boolean complete) {
    TaskManager.stopTask(p, "teleportation");
    // ScoreboardTools.setGlobalPrefix(p, "");
    // Main.updateScoreboard();
    p.closeInventory();
    if (channels.containsKey(p)) {
      ChannelingObject channel = channels.get(p);
      if (complete) {
        channel.getFinishRunnable().run();
      } else {
        channel.getInterruptRunnable().run();
      }
      channels.remove(p);
    }
  }

  public static void addExtendedChannelTime(Player p, ExtendedChannelTime ect) {
    if (!channels.containsKey(p))
      return;

    ChannelingObject channel = channels.get(p);
    for (ExtendedChannelTime ect2 : channel.getChannelTimes()) {
      if (ect2.getReason().equals(ect.getReason())) {
        ect2.setDescription(ect.getDescription());
        ect2.addTime(ect.getCurrentTime());
        // updateChannelingInventory(p);
        return;
      } else if (ect2.getDescription().equals(ect.getDescription())) {
        ect2.setReason(ect.getReason());
        ect2.addTime(ect.getCurrentTime());
        // updateChannelingInventory(p);
        return;
      }
    }
    channels.get(p).addChannelTime(ect);
    // updateChannelingInventory(p);
  }

  public static void teleportComplete(Player p) {
    teleportFinished(p, true);
  }

  public static void teleportInterrupted(Player p) {
    teleportFinished(p, false);
  }

  public static List<ExtendedChannelTime> getChannelingTimesBetweenPoints(Location l1, Location l2) {
    List<ExtendedChannelTime> channelTimes = new ArrayList<>();

    if (!l1.getWorld().equals(l2.getWorld())) {
      channelTimes.add(new ExtendedChannelTime(30, "World Warp", Material.ENDER_PEARL,
          "Traveling to separate worlds increases travel time."));
      if ((l1.getWorld().getEnvironment() == World.Environment.NETHER
          && l2.getWorld().getEnvironment() == World.Environment.NORMAL)
          || (l1.getWorld().getEnvironment() == World.Environment.NORMAL
              && l2.getWorld().getEnvironment() == World.Environment.NETHER)) {
        double locationx1 = l1.getX();
        double locationy1 = l1.getY();
        double locationz1 = l1.getZ();
        double locationx2 = l2.getX();
        double locationy2 = l2.getY();
        double locationz2 = l2.getZ();
        if (l1.getWorld().getEnvironment() != World.Environment.NETHER) {
          locationx1 /= 8;
          locationy1 /= 8;
          locationz1 /= 8;
        }
        if (l2.getWorld().getEnvironment() != World.Environment.NETHER) {
          locationx2 /= 8;
          locationy2 /= 8;
          locationz2 /= 8;
        }
        Location loc1 = new Location(l1.getWorld(), locationx1, locationy1, locationz1);
        Location loc2 = new Location(l1.getWorld(), locationx2, locationy2, locationz2);
        channelTimes.add(new ExtendedChannelTime((int) Math.floor(loc1.distance(loc2) / 8), "Travel Time",
            Material.IRON_BOOTS, "Walking costs hunger, time, and effort."));
      }
    } else {
      channelTimes.add(new ExtendedChannelTime((int) Math.floor(l1.distance(l2) / 8), "Travel Time",
          Material.IRON_BOOTS, "Walking costs hunger, time, and effort."));
    }
    if (!(l1.getWorld().getEnvironment() == World.Environment.THE_END
        && l2.getWorld().getEnvironment() == World.Environment.THE_END)
        && (l1.getWorld().getEnvironment() == World.Environment.THE_END
            || l2.getWorld().getEnvironment() == World.Environment.THE_END)) {
      channelTimes.add(new ExtendedChannelTime(60 * 10, "Eye of Ender", Material.ENDER_PEARL,
          "This might take a while."));
    }

    return channelTimes;
  }

  public static List<ExtendedChannelTime> getChannelingTimesBetweenPlayers(Player p1, Player p2) {
    List<ExtendedChannelTime> channelTimes = getChannelingTimesBetweenPoints(p1.getLocation(), p2.getLocation());

    // TODO

    return channelTimes;
  }

  @EventHandler
  public void damage(EntityDamageEvent e) {
    if (e.getEntity() instanceof Player) {
      addExtendedChannelTime((Player) e.getEntity(), new ExtendedChannelTime((long) e.getFinalDamage(),
          "Outside Damage", Material.SKELETON_SKULL, "Outside damage increases channeling times."));
    }
  }

  @EventHandler
  public void leave(PlayerQuitEvent e) {
    teleportInterrupted(e.getPlayer());
  }
}