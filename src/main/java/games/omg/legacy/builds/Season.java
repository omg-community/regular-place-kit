package games.omg.legacy.builds;

/**
 * An enum which represents a season of the server.
 */
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
