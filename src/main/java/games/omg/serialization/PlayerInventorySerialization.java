package games.omg.serialization;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class PlayerInventorySerialization {
  final private static int version = 1;

  public static String serializePlayerInventory(PlayerInventory inventory) {
    // Serialize main inventory contents
    String serializedContents = Serialization.serializeItemStacks(inventory.getContents());

    // Serialize armor contents
    String serializedArmorContents = Serialization.serializeItemStacks(inventory.getArmorContents());

    // Serialize extra contents (like off-hand slot in newer Minecraft versions)
    String serializedExtraContents = Serialization.serializeItemStacks(inventory.getExtraContents());

    // Constructing a JSON object with all these serialized parts
    JsonObject jsonInventory = new JsonObject();
    jsonInventory.addProperty("version", version);
    jsonInventory.addProperty("contents", serializedContents);
    jsonInventory.addProperty("armorContents", serializedArmorContents);
    jsonInventory.addProperty("extraContents", serializedExtraContents);

    // Convert the JSON object to a string
    return new Gson().toJson(jsonInventory);
  }

  public static DeserializedPlayerInventory deserializePlayerInventory(String serializedInventory) {
    // Convert the string to a JSON object
    JsonObject jsonInventory = new Gson().fromJson(serializedInventory, JsonObject.class);

    // Check if the version of the serialized inventory is the same as the current version
    if (jsonInventory.get("version").getAsInt() != version) {
      throw new IllegalArgumentException("Serialized inventory version is not the same as the current version");
    }

    // Deserialize the main inventory contents
    String serializedContents = jsonInventory.get("contents").getAsString();
    ItemStack[] contents = Serialization.deserializeItemStacksToArray(serializedContents, 36);

    // Deserialize the armor contents
    String serializedArmorContents = jsonInventory.get("armorContents").getAsString();
    ItemStack[] armorContents = Serialization.deserializeItemStacksToArray(serializedArmorContents, 4);

    // Deserialize the extra contents
    String serializedExtraContents = jsonInventory.get("extraContents").getAsString();
    ItemStack[] extraContents = Serialization.deserializeItemStacksToArray(serializedExtraContents, 1);

    // Construct a new PlayerInventory object with the deserialized contents
    DeserializedPlayerInventory deserializedInventory = new DeserializedPlayerInventory(contents, armorContents, extraContents);

    // Return the deserialized PlayerInventory object
    return deserializedInventory;
  }
}
