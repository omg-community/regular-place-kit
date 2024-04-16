package games.omg.improvements;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilCostFix implements Listener {
    
    private static Logger logger = Logger.getLogger("Minecraft");
    // the max extra levels added from a enchant is something like 13?
    private static int max_repair_penalty = 39;
    
    @EventHandler
    public void onAnvilOpen(InventoryOpenEvent event) {
        if (!(event.getInventory() instanceof AnvilInventory)) {
            return;
        }

        AnvilInventory anvil = ((AnvilInventory) event.getInventory());
        // Lol
        anvil.setMaximumRepairCost(Integer.MAX_VALUE);
        
        // this stops the first item from showing as "Too Expensive" since we havent reset the repairCost metadata yet
        // this doesnt actually do that, unfortunately, but ill keep it here anyway
        anvil.setRepairCost(0);
    }

    @EventHandler
    public void onTakeLastItem(InventoryClickEvent event) {
        if (!(event.getInventory() instanceof AnvilInventory)) {
            return;
        }

        AnvilInventory anvil = ((AnvilInventory) event.getInventory());

        ItemStack result = event.getCurrentItem();
        if (event.getRawSlot() == 2 && result != null) {
            result.setItemMeta(modifyMetaRepairCost(result, max_repair_penalty));
        }
    }

    @EventHandler
    public void onAnvilUse(PrepareAnvilEvent event) {
        AnvilInventory anvil = event.getInventory();
        ItemStack[] items = anvil.getContents();
        
        ItemStack item1 = items[0];
        ItemStack item2 = items[1];

        if (
            (item1 == null || item1.getType() == Material.AIR) ||
            (item2 == null || item2.getType() == Material.AIR)
        ) {
            return;
        }

        ItemMeta old_item1_meta = item1.getItemMeta();
        ItemMeta old_item2_meta = item2.getItemMeta();
        
        int combined_repair_penalty = clamp(getRepairPenalty(old_item1_meta) + getRepairPenalty(old_item2_meta), 1, 39);

        item1.setItemMeta(modifyMetaRepairCost(item1, combined_repair_penalty));
        item2.setItemMeta(modifyMetaRepairCost(item2, 1));
        
        ItemStack result = anvil.getResult();
        
        if (result == null) {
            // set the meta back to default
            // As far as im aware, there should be no issue leaving the meta as is Technically,
            // but it would allow people to place a item in the second slot to reset its repair penalty
            // which kinda defeats most of the purpose of me making the costs actually matter...
            item1.setItemMeta(old_item1_meta);
            item2.setItemMeta(old_item2_meta);
            
            return;
        }

        if (isRepair(item1, item2, result)) {
            // incase they take the items out, we want them to have their old meta data again
            // so they cant just reset the penalty
            item1.setItemMeta(old_item1_meta);
            item2.setItemMeta(old_item2_meta);

            anvil.setRepairCost(1);
        } else if (isUpgrade(item1, item2, result)) {
            item1.setItemMeta(old_item1_meta);
            item2.setItemMeta(old_item2_meta);

            int rep_cost = clamp(anvil.getRepairCost(), 1, max_repair_penalty);

            anvil.setRepairCost(clamp(combined_repair_penalty, rep_cost, max_repair_penalty));
        }

        return;
    }

    private int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    private ItemMeta modifyMetaRepairCost(ItemStack item, int v) {
        Repairable i_meta = ((Repairable)item.getItemMeta());
        i_meta.setRepairCost(clamp(getRepairPenalty(i_meta), 1, v));
        
        return i_meta;
    }

    private int getRepairPenalty(ItemMeta meta) {
        // Fucking Null
        if (meta != null) {
            return ((Repairable) meta).getRepairCost();
        }

        return 1;
    }

    private boolean isRepair(ItemStack item1, ItemStack item2, ItemStack result) {
        // if the item doesnt recieve any new enchants or upgrades to those enchants,
        // we can just say its a repair and make it cost 1 level, this is more or less irrelevant because of mending
        // but its nice as a qol feature for before mending is gotten, or tools taht cant have mending (bows w/ infinity)
        if (
            (item1.isRepairableBy(item2) && item2.getType() != item1.getType()) ||
            // if its the same item and the enchants are the same for the result, then we know its just a repair using the same item, and a small hack to stop ench books counting
            (item1.getType() == item2.getType() && item1.getEnchantments().equals(result.getEnchantments()) && item2.getType() != Material.ENCHANTED_BOOK)
        ) {
            return true;
        }

        return false;
    }

    private boolean isUpgrade(ItemStack item1, ItemStack item2, ItemStack result) {
        if (
            (item1.getType() == item2.getType() && !item1.getEnchantments().equals(result.getEnchantments())) || item2.getType() == Material.ENCHANTED_BOOK
        ) {
            return true;
        }

        return false;
    }
}
