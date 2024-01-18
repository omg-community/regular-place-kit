package games.omg.legacy.builds;

import org.bukkit.Material;

/**
 * An enum which represents the genre of a build.
 */
public enum UGCGenre {

  MISC("Miscellaneous", Material.BOOKSHELF),
  TOWNY("Towny", Material.CRAFTING_TABLE),
  STATUE("Statue", Material.STONE_BRICKS),
  PARKOUR("Parkour", Material.IRON_BOOTS),
  ELYTRA_SURF("Elytra Surf", Material.ELYTRA),
  REDSTONE("Redstone", Material.PISTON),
  PIXEL_ART("Pixel Art", Material.LIME_WOOL),
  MINIGAME("Minigame", Material.SLIME_BLOCK);

  //

  final private String displayName;
  final private Material displayMaterial;

  UGCGenre(String displayName, Material displayMaterial) {
    this.displayName = displayName;
    this.displayMaterial = displayMaterial;
  }

  //

  public String getDisplayName() {
    return displayName;
  }

  public Material getDisplayMaterial() {
    return displayMaterial;
  }
}
