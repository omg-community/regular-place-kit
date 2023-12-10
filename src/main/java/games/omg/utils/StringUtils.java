package games.omg.utils;

public class StringUtils {
  public static String makePossessive(String name) {
    if (name.endsWith("s")) {
      return name + "'";
    } else {
      return name + "'s";
    }
  }
}
