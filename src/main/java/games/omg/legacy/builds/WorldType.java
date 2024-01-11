package games.omg.legacy.builds;

import org.bukkit.Material;

public enum WorldType {

  FLATBUILD("Flatbuild", "Flatbuild", Material.GRASS_BLOCK),
  LOW_FLATBUILD("Low Flatbuild", "Low Flatbuild", Material.BEDROCK),
  SKYBUILD("Skybuild", "Skybuild", Material.END_STONE_BRICKS),
  FREEBUILD(null, "Freebuild", Material.DIRT_PATH);

  //

  final private String fileName;
  final private String displayName;
  final private Material displayMaterial;

  WorldType(String fileName, String displayName, Material displayMaterial) {
    this.fileName = fileName;
    this.displayName = displayName;
    this.displayMaterial = displayMaterial;
  }

  //

  public String getFileName() {
    return fileName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public Material getDisplayMaterial() {
    return displayMaterial;
  }
}
