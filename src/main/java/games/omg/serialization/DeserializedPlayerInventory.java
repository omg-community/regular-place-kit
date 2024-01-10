package games.omg.serialization;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class DeserializedPlayerInventory {
    public ItemStack[] contents;
    public ItemStack[] armorContents;
    public ItemStack[] extraContents;

    public DeserializedPlayerInventory(ItemStack[] contents, ItemStack[] armorContents, ItemStack[] extraContents) {
      this.contents = contents;
      this.armorContents = armorContents;
      this.extraContents = extraContents;
    }

    public ItemStack[] getContents() {
      return contents;
    }

    public ItemStack[] getArmorContents() {
      return armorContents;
    }

    public ItemStack[] getExtraContents() {
      return extraContents;
    }

    public void cloneTo(PlayerInventory inventory) {
      inventory.setContents(contents);
      inventory.setArmorContents(armorContents);
      inventory.setExtraContents(extraContents);
    }
  }