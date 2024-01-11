package games.omg.legacy.builds;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import games.omg.menus.InventoryMenu;

public class BuildUtils {

  final public static String GET_BUILDS = "getBuilds";
  final public static String GET_BUILDS_FROM_PLAYER_SEARCH = "getBuildsFromPlayerSearch";
  final public static String GET_BUILD_FROM_PLAYER_SEARCH_BY_NAME = "getBuildFromPlayerSearchByName";
  final public static String GET_BUILDS_FROM_PLAYER_SEARCH_BY_NAME = "getBuildsFromPlayerSearchByName";
  final public static String GET_BUILDS_FROM_PLAYER_SEARCH_BY_UUID = "getBuildsFromPlayerSearchByUUID";
  final public static String GET_BUILD_BY_EXACT_NAME = "getBuildByExactName";
  final public static String DELETE_BUILD_BY_ID = "deleteBuildByID";
  final public static String CREATE_BUILD = "createBuild";
  final public static String UPDATE_BUILD = "updateBuild";

  //

  public static UGCBuild getBuildFromResultSet(ResultSet resultSet) {
    try {
      int id = resultSet.getInt("id");
      UUID owner = UUID.fromString(resultSet.getString("owner"));
      String titleActual = resultSet.getString("title");
      UGCGenre genre = UGCGenre.values()[resultSet.getInt("genre")];
      UGCPrivacy privacy = UGCPrivacy.values()[resultSet.getInt("privacy")];
      Season season = Season.values()[resultSet.getInt("season")];
      long creationTime = resultSet.getLong("creationTime");
      UUID world = UUID.fromString(resultSet.getString("world"));
      double x = resultSet.getDouble("x");
      double y = resultSet.getDouble("y");
      double z = resultSet.getDouble("z");
      float yaw = resultSet.getFloat("yaw");
      float pitch = resultSet.getFloat("pitch");

      return new UGCBuild(id, owner, titleActual, genre, privacy, season, creationTime, world, x, y, z, yaw, pitch);
    } catch (Exception e) {
      return new UGCBuild(null, null, null, null, null, null, 0, null, 0, 0, 0, 0, 0);
    }
  }

  public static void createBuild(Player p, UGCBuild build) {
    try {
      boolean newBuild = build.getId() == null;
      // PreparedStatement statement = newBuild ? Database.getInstance().getStatement(CREATE_BUILD)
      //     : Database.getInstance().getStatement(UPDATE_BUILD);
      // TODO
      PreparedStatement statement = null;

      statement.setString(1, build.getOwner().toString());
      statement.setString(2, build.getTitle());
      statement.setInt(3, build.getGenre().ordinal());
      statement.setInt(4, build.getPrivacy().ordinal());
      statement.setInt(5, build.getSeason().ordinal());
      statement.setString(6, build.getWorld().toString());
      statement.setDouble(7, build.getX());
      statement.setDouble(8, build.getY());
      statement.setDouble(9, build.getZ());
      statement.setFloat(10, build.getYaw());
      statement.setFloat(11, build.getPitch());
      if (!newBuild)
        statement.setInt(12, build.getId());

      if (statement.executeUpdate() < 1) {
        if (p != null)
          p.sendMessage(
              ChatColor.BLUE + "Build " + ChatColor.GRAY + "Could not " + (newBuild ? "create" : "update") + " build.");
      } else {
        if (p != null) {
          p.sendMessage(ChatColor.BLUE + "Build " + ChatColor.GRAY + "Your build, " + ChatColor.RESET + "\""
              + build.getTitle() + "\"" + ChatColor.GRAY + " was " + (newBuild ? "created!" : "updated."));
          p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      if (p != null)
        p.sendMessage(ChatColor.BLUE + "Build " + ChatColor.GRAY + "An error has occured.");
    }
  }

  public static void openBuildConstructor(Player p, UGCBuild build) {
    boolean newBuild = build.getId() == null;

    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_HIT, 1, 1);
    new InventoryMenu(p, "BuildConstructor", 54, "Build constructor", new InventoryMenu.InventoryClickHandler() {

      boolean complete = false;
      HashMap<Integer, UGCGenre> genreMap = new HashMap<>();
      HashMap<Integer, UGCPrivacy> privacyMap = new HashMap<>();

      @Override
      public void inventoryInteractEvent(InventoryInteractEvent e) {
        e.setCancelled(true);

        Inventory clickedInventory = InventoryMenu.getClickedInventory(e);
        if (clickedInventory == null || !clickedInventory.equals(e.getInventory()))
          return;

        Integer slot = InventoryMenu.getClickedSlot(e);
        if (slot == null)
          return;

        if (genreMap.containsKey(slot)) {
          UGCGenre previousGenre = build.getGenre();

          for (Integer previousSlot : genreMap.keySet()) {
            if (genreMap.get(previousSlot) == previousGenre) {
              ItemStack previous = clickedInventory.getItem(previousSlot);
              ItemMeta previousMeta = previous.getItemMeta();
              previousMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to set genre"));
              previous.setItemMeta(previousMeta);
              previous.removeEnchantment(Enchantment.KNOCKBACK);
              break;
            }
          }

          ItemStack next = clickedInventory.getItem(slot);
          ItemMeta nextMeta = next.getItemMeta();
          nextMeta.setLore(Collections.singletonList(ChatColor.GREEN + "Selected"));
          next.setItemMeta(nextMeta);
          next.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
          build.setGenre(genreMap.get(slot));
        } else if (privacyMap.containsKey(slot)) {
          UGCPrivacy previousPrivacy = build.getPrivacy();
          UGCPrivacy nextPrivacy = privacyMap.get(slot);

          for (Integer previousSlot : privacyMap.keySet()) {
            if (privacyMap.get(previousSlot) == previousPrivacy) {
              ItemStack previous = clickedInventory.getItem(previousSlot);
              ItemMeta previousMeta = previous.getItemMeta();
              previousMeta.setLore(Arrays.asList(ChatColor.GRAY + previousPrivacy.getDescription(),
                  ChatColor.GRAY + "Click to set privacy"));
              previous.setItemMeta(previousMeta);
              previous.removeEnchantment(Enchantment.KNOCKBACK);
              break;
            }
          }

          ItemStack next = clickedInventory.getItem(slot);
          ItemMeta nextMeta = next.getItemMeta();
          nextMeta.setLore(Arrays.asList(ChatColor.GRAY + nextPrivacy.getDescription(), ChatColor.GREEN + "Selected"));
          next.setItemMeta(nextMeta);
          next.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
          build.setPrivacy(nextPrivacy);
        } else {
          if (slot == 41) {
            // create build
            complete = true;
            createBuild(p, build);
            e.getView().close();
          } else {
            if (newBuild) {
              if (slot == 39) {
                // delete build
                e.getView().close();
              }
            } else {
              if (slot == 39) {
                // delete build
                if (e instanceof InventoryClickEvent) {
                  if (((InventoryClickEvent) e).getClick() == ClickType.DOUBLE_CLICK) {
                    complete = true;
                    e.getView().close();
                    deleteBuild(p, build.getId());
                  }
                }
              } else if (slot == 40) {
                // move build
                ItemStack move = clickedInventory.getItem(40);
                if (move != null && move.getType() != Material.ENCHANTING_TABLE) {
                  ItemMeta moveItemMeta = move.getItemMeta();
                  moveItemMeta.setLore(Arrays.asList(ChatColor.DARK_GREEN + "Build move complete",
                      ChatColor.GREEN + "Update the build to apply changes"));
                  move.setItemMeta(moveItemMeta);
                  move.setType(Material.ENCHANTING_TABLE);
                  p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 1);
                }
                build.setLocation(p.getLocation());
              }
            }
          }
        }
      }

      @Override
      public void create(Inventory i) {
        UGCGenre[] genres = UGCGenre.values();
        int genreSize = genres.length;
        int current = (int) (13 - Math.floor(genreSize / 2));
        boolean skipCenter = genreSize % 2 == 0;
        for (UGCGenre genre : genres) {
          if (current == 13 && skipCenter)
            current++;

          boolean selectedGenre = build.getGenre() == genre;

          ItemStack itemStack = new ItemStack(genre.getDisplayMaterial(), 1);
          ItemMeta itemMeta = itemStack.getItemMeta();
          itemMeta.setDisplayName(ChatColor.RESET + genre.getDisplayName());
          itemMeta.setLore(Collections
              .singletonList(selectedGenre ? ChatColor.GREEN + "Selected" : ChatColor.GRAY + "Click to set genre"));

          itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
          itemStack.setItemMeta(itemMeta);

          if (selectedGenre) {
            itemStack.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
          }

          genreMap.put(current, genre);

          i.setItem(current, itemStack);
          current++;
        }

        UGCPrivacy[] privacies = UGCPrivacy.values();
        int privacySize = privacies.length;
        current = (int) (31 - Math.floor(privacySize / 2));
        skipCenter = privacySize % 2 == 0;
        for (UGCPrivacy privacy : privacies) {
          if (current == 31 && skipCenter)
            current++;

          boolean selectedPrivacy = build.getPrivacy() == privacy;

          ItemStack itemStack = new ItemStack(privacy.getDisplayMaterial(), 1);
          ItemMeta itemMeta = itemStack.getItemMeta();
          itemMeta.setDisplayName(ChatColor.RESET + privacy.getDisplayName());
          itemMeta.setLore(Arrays.asList(ChatColor.GRAY + privacy.getDescription(),
              selectedPrivacy ? ChatColor.GREEN + "Selected" : ChatColor.GRAY + "Click to set privacy"));

          itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
          itemStack.setItemMeta(itemMeta);

          if (selectedPrivacy) {
            itemStack.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
          }

          privacyMap.put(current, privacy);

          i.setItem(current, itemStack);
          current++;
        }

        ItemStack delete = new ItemStack(Material.TNT, 1);
        ItemMeta deleteItemMeta = delete.getItemMeta();
        if (newBuild) {
          deleteItemMeta.setDisplayName(ChatColor.RESET + "Discard build");
          deleteItemMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to erase"));
        } else {
          deleteItemMeta.setDisplayName(ChatColor.RED + "Delete build ⚠");
          deleteItemMeta.setLore(Collections.singletonList(ChatColor.GRAY + "DOUBLE-CLICK to delete this build"));
        }
        delete.setItemMeta(deleteItemMeta);
        i.setItem(39, delete);

        if (!newBuild) {
          ItemStack move = new ItemStack(Material.END_PORTAL_FRAME, 1);
          ItemMeta moveItemMeta = move.getItemMeta();
          moveItemMeta.setDisplayName(ChatColor.RESET + "Move build here");
          moveItemMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to update build location"));
          move.setItemMeta(moveItemMeta);
          i.setItem(40, move);
        }

        ItemStack completeAndSave = new ItemStack(newBuild ? Material.GREEN_TERRACOTTA : Material.ORANGE_TERRACOTTA, 1);
        ItemMeta completeAndSaveItemMeta = completeAndSave.getItemMeta();
        if (newBuild) {
          completeAndSaveItemMeta.setDisplayName(ChatColor.RESET + "Complete and save build");
          completeAndSaveItemMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to save and submit"));
        } else {
          completeAndSaveItemMeta.setDisplayName(ChatColor.RESET + "Complete and update build");
          completeAndSaveItemMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to update changes"));
        }
        completeAndSave.setItemMeta(completeAndSaveItemMeta);
        i.setItem(41, completeAndSave);

      }

      @Override
      public void close(Inventory i) {
        if (!complete)
          p.sendMessage(newBuild ? ChatColor.BLUE + "Build " + ChatColor.GRAY + "Build trashed."
              : ChatColor.BLUE + "Build " + ChatColor.GRAY + "Build edit cancelled.");
      }
    });
  }

  public static void deleteBuild(Player p, Integer id) {
    if (id == null)
      return;
    try {
      // PreparedStatement statement = Database.getInstance().getStatement(DELETE_BUILD_BY_ID);
      // TODO
      PreparedStatement statement = null;
      statement.setLong(1, id);
      if (statement.executeUpdate() > 0) {
        if (p != null) {
          p.sendMessage(ChatColor.BLUE + "Build " + ChatColor.GRAY + "Build deleted.");
          p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);
        }
      } else {
        if (p != null)
          p.sendMessage(ChatColor.BLUE + "Build " + ChatColor.GRAY + "Build not found.");
      }
    } catch (Exception e) {
      if (p != null)
        p.sendMessage(ChatColor.BLUE + "Build " + ChatColor.GRAY + "An error has occured.");
    }
  }

  public static BuildTitleResult checkBuildTitle(String title) {
    if (title.length() < 1) {
      return BuildTitleResult.TOO_SHORT;
    } else if (title.length() > 35) {
      return BuildTitleResult.TOO_LONG;
    } else if (title.matches("[0-9a-zA-Z ]*")) {
      return BuildTitleResult.OK;
    } else {
      return BuildTitleResult.NOT_ALPHANUMERIC_OR_SPACE;
    }
  }

  public static void openBuildListing(Player p, String title, PreparedStatement statement, int indexOfPagination) {
    p.playSound(p.getLocation(), Sound.UI_TOAST_IN, 1, 1);
    new InventoryMenu(p, "BuildListing", 54, title, new InventoryMenu.InventoryClickHandler() {

      HashMap<Integer, UGCBuild> buildMap = new HashMap<>();
      ItemStack previous = new ItemStack(Material.BOOK);
      ItemStack next = new ItemStack(Material.BOOK);
      int page = 0;

      private void viewPage(Inventory i) {
        buildMap.clear();
        try {
          statement.setInt(indexOfPagination, page);
          ResultSet resultSet = statement.executeQuery();
          int index = 0;

          if (page > 0) {
            ItemMeta previousMeta = previous.getItemMeta();
            previousMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Page " + page));
            previous.setItemMeta(previousMeta);
            i.setItem(45, previous);
          } else {
            i.setItem(45, null);
          }

          while (resultSet.next()) {
            int id = resultSet.getInt("id");
            UUID owner = UUID.fromString(resultSet.getString("owner"));
            // TODO
            // Crashplayer ownerPlayer = Crashplayer.get(owner);
            String title = resultSet.getString("title");
            UGCGenre genre = UGCGenre.values()[resultSet.getInt("genre")];
            UGCPrivacy privacy = UGCPrivacy.values()[resultSet.getInt("privacy")];
            Season season = Season.values()[resultSet.getInt("season")];
            long creationTime = resultSet.getLong("creationTime");
            UUID world = UUID.fromString(resultSet.getString("world"));
            double x = resultSet.getDouble("x");
            double y = resultSet.getDouble("y");
            double z = resultSet.getDouble("z");
            float yaw = resultSet.getFloat("yaw");
            float pitch = resultSet.getFloat("pitch");

            UGCBuild build = new UGCBuild(id, owner, title, genre, privacy, season, creationTime, world, x, y, z, yaw,
                pitch);

            // ItemStack itemStack = Kollections.createSkull(owner);
            // TODO
            ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.RESET + title);
            itemMeta.setLore(Arrays.asList(
                // ChatColor.GRAY + "" + ChatColor.ITALIC + "by " + ownerPlayer.getDisplayName(),
                ChatColor.GRAY + "" + ChatColor.ITALIC + "by " + "TODO", // TODO
                ChatColor.BLUE + genre.getDisplayName(),
                ChatColor.DARK_GREEN + "[insert world here]",
                privacy == UGCPrivacy.PRIVATE ? ChatColor.RED + "Only visible to you and admins"
                    : ChatColor.DARK_GRAY + "Disqualified"));
            itemStack.setItemMeta(itemMeta);
            i.setItem(index, itemStack);

            buildMap.put(index, build);
            index++;
          }

          if (index == 36) {
            ItemMeta nextMeta = next.getItemMeta();
            nextMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Page " + (page + 2)));
            next.setItemMeta(nextMeta);
            i.setItem(53, next);
          } else {
            i.setItem(53, null);
          }
        } catch (Exception e) {
          buildMap.clear();
          e.printStackTrace();
        }
      }

      @Override
      public void inventoryInteractEvent(InventoryInteractEvent e) {
        e.setCancelled(true);

        Inventory clickedInventory = InventoryMenu.getClickedInventory(e);
        if (clickedInventory == null || !clickedInventory.equals(e.getInventory()))
          return;

        Integer slot = InventoryMenu.getClickedSlot(e);
        if (slot == null)
          return;

        if (slot == 45) {
          page--;
          viewPage(e.getInventory());
        } else if (slot == 53) {
          page++;
          viewPage(e.getInventory());
        } else {
          UGCBuild build = buildMap.get(slot);
          teleportToBuild(p, build);
          close(e.getInventory());
        }
        /*
         * Inventory clickedInventory = InventoryMenu.getClickedInventory(e);
         * Bukkit.broadcastMessage(clickedInventory == null ? "null" :
         * clickedInventory.toString());
         * if (clickedInventory == null || !clickedInventory.equals(e.getInventory()))
         * return;
         * Bukkit.broadcastMessage(ChatColor.GREEN + "Passed clicked inventory check");
         * Integer slot = InventoryMenu.getClickedSlot(e);
         * Bukkit.broadcastMessage(slot == null ? "null" : slot.toString());
         * if (slot == null) return;
         * Bukkit.broadcastMessage(ChatColor.GREEN + "Passed slot check");
         * clickedInventory.setItem(slot, new ItemStack(Material.REDSTONE_BLOCK));
         * InventoryMenu.setCursor(e, new ItemStack(Material.BOAT));
         * Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "Set complete");
         */
      }

      @Override
      public void create(Inventory i) {
        ItemMeta nextMeta = next.getItemMeta();
        nextMeta.setDisplayName(ChatColor.RESET + "Next");
        next.setItemMeta(nextMeta);
        ItemMeta previousMeta = previous.getItemMeta();
        previousMeta.setDisplayName(ChatColor.RESET + "Previous");
        previous.setItemMeta(previousMeta);
        viewPage(i);
      }

      @Override
      public void close(Inventory i) {
        p.playSound(p.getLocation(), Sound.UI_TOAST_OUT, 1, 1);
      }
    });
  }

  public static void teleportToBuild(Player p, UGCBuild build) {
    Location loc = build.getLocation();
    p.teleport(loc);
    if (p.getGameMode() == GameMode.CREATIVE)
      p.setFlying(loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR);
    p.playSound(loc, Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 1);
    OfflinePlayer owner = Bukkit.getOfflinePlayer(build.getOwner());
    String ownerName = owner.getName() == null ? "?¿?¿?'s"
        : (owner.getName().endsWith("s") ? owner.getName() + "'" : owner.getName() + "'s");
    p.sendMessage(ChatColor.BLUE + "Build " + ChatColor.GRAY + "You teleported to " + ChatColor.RESET + ownerName
        + ChatColor.GRAY + " build, " + ChatColor.RESET + "\"" + build.getTitle() + "\"");
  }
}
