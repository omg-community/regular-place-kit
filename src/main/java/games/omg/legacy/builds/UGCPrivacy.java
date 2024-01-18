package games.omg.legacy.builds;

import org.bukkit.Material;

/**
 * An enum which represents the privacy of a build.
 */
public enum UGCPrivacy {

  PUBLIC("Public", "Everyone can see", Material.LIME_WOOL),
  PRIVATE("Private", "Only you and admins can see", Material.RED_WOOL);

  //

  final private String displayName;
  final private String description;
  final private Material displayMaterial;

  UGCPrivacy(String displayName, String description, Material displayMaterial) {
    this.displayName = displayName;
    this.description = description;
    this.displayMaterial = displayMaterial;
  }

  //

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  public Material getDisplayMaterial() {
    return displayMaterial;
  }
}
