package games.omg.serialization;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Serialization {

  public static String serializeItemStack(ItemStack item) {
    return item.serialize().toString();
  }

  public static ItemStack deserializeItemStack(String serializedItem) {
    Map<String, Object> map = new Gson().fromJson(serializedItem, new TypeToken<Map<String, Object>>() {}.getType());
    return ItemStack.deserialize(map);
  }

  //

  public static Map<Integer, String> serializeItemStacksToMap(ItemStack[] inventory) {
    Map<Integer, String> serializedItemStacks = new HashMap<>();
    for (int i = 0; i < inventory.length; i++) {
      ItemStack item = inventory[i];
      if (item != null) {
        serializedItemStacks.put(i, serializeItemStack(item));
      }
    }
    return serializedItemStacks;
  }

  public static String serializeItemStackMap(Map<Integer, String> map) {
    return new Gson().toJson(map);
  }

  public static String serializeItemStacks(ItemStack[] inventory) {
    return serializeItemStackMap(serializeItemStacksToMap(inventory));
  }

  //

  public static Map<Integer, String> deserializeItemStacksToMap(String serializedInventory) {
    Map<Integer, String> map = new Gson().fromJson(serializedInventory, new TypeToken<Map<Integer, String>>() {}.getType());
    return map;
  }
  
  public static ItemStack[] deserializeItemStackMap(Map<Integer, String> itemStackMap, int arraySize) {
    ItemStack[] inventory = new ItemStack[arraySize];
    for (Map.Entry<Integer, String> entry : itemStackMap.entrySet()) {
      inventory[entry.getKey()] = deserializeItemStack(entry.getValue());
    }
    return inventory;
  }

  public static ItemStack[] deserializeItemStacksToArray(String serializedInventory, int arraySize) {
    Map<Integer, String> map = deserializeItemStacksToMap(serializedInventory);
    return deserializeItemStackMap(map, arraySize);
  }

}
