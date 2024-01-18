package games.omg.handlers.test;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import games.omg.Main;
import games.omg.serialization.BukkitSerialization;

/**
 * A Listener class which is used to test serialization.
 */
public class SerializeTest implements Listener {
  
  @EventHandler
  public void onRightClick(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    if (event.getAction() == Action.LEFT_CLICK_AIR) {
      // String serializedItem = Serialization.serializeItemStack(player.getInventory().getItemInMainHand());
      // player.getInventory().addItem(Serialization.deserializeItemStack(serializedItem));
      String serializedItem = BukkitSerialization.itemStackArrayToBase64(new ItemStack[] { player.getInventory().getItemInMainHand() });
      try {
        player.getInventory().addItem(BukkitSerialization.itemStackArrayFromBase64(serializedItem));
      } catch (Exception e) {
        e.printStackTrace();
      }
      player.sendMessage("You left clicked air! Giving you a copy of your main hand item..");
    }
    if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
      String[] serializedInventory = BukkitSerialization.playerInventoryToBase64(player.getInventory());
      player.sendMessage("You left clicked a block! Waiting 20 to deserialize...");

      Main.getPlugin().getServer().getScheduler().runTaskLater(Main.getPlugin(), () -> {
        player.sendMessage("Deserializing...");
        try {
          player.getInventory().setContents(BukkitSerialization.itemStackArrayFromBase64(serializedInventory[0]));
          player.getInventory().setArmorContents(BukkitSerialization.itemStackArrayFromBase64(serializedInventory[1]));
          player.getInventory().setExtraContents(BukkitSerialization.itemStackArrayFromBase64(serializedInventory[2]));
        } catch (Exception e) {
          e.printStackTrace();
        }
        player.sendMessage("Deserialized!");
      }, 20 * 20);
    }
  }
}
