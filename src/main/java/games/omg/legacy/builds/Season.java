package games.omg.legacy.builds;

public enum Season {

  ZERO("Season 0");

  //

  final private String displayName;

  Season(String displayName) {
    this.displayName = displayName;
  }

  //

  public String getDisplayName() {
    return displayName;
  }
}
