package games.omg.utils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * A class which provides utilities for strings.
 */
public class StringUtils {

  /**
   * Makes a name possessive.
   * 
   * @param name The name to make possessive
   * @return The possessive name
   */
  public static String makePossessive(String name) {
    if (name.endsWith("s")) {
      return name + "'";
    } else {
      return name + "'s";
    }
  }

  /**
   * Creates a grammatical list from a list of strings.
   * 
   * @param items The list of strings
   * @return The grammatical list
   * 
   * @deprecated This may not be useful anymore. You may simply want to use a
   *             JoinConfiguration.
   */
  public static String createGrammaticalList(List<String> items) {
    int listSize = items.size();

    if (listSize == 0)
      return "";
    if (listSize == 1)
      return items.get(0);
    if (listSize == 2)
      return items.get(0) + " and " + items.get(1);

    StringBuilder builder = new StringBuilder(items.get(0));
    for (int i = 1; i < listSize; i++) {
      builder.append(i == listSize - 1 ? ", and" : ", ");
      builder.append(items.get(i));
    }

    return builder.toString();
  }

  /**
   * Separates a list of strings with a separator.
   * 
   * @param items     The list of strings
   * @param separator The separator
   * @return The separated list
   * 
   * @deprecated This may not be useful anymore. You may simply want to use a
   *             JoinConfiguration.
   */
  public static String separateListWith(List<String> items, String separator) {
    int listSize = items.size();

    if (listSize == 0)
      return "";
    if (listSize == 1)
      return items.get(0);

    StringBuilder builder = new StringBuilder(items.get(0));
    for (int i = 1; i < listSize; i++) {
      builder.append(separator);
      builder.append(items.get(i));
    }

    return builder.toString();
  }

  /**
   * Formats a number by adding commas for place separation.
   * 
   * @param num The number to format
   * @return The formatted number
   */
  public static String getCommaSeparatedNumber(int num) {
    DecimalFormat formatter = new DecimalFormat("#,###");
    return formatter.format(num);
  }

  /**
   * Gets the placement string for a number.
   * 
   * 1, 2, and 3 should become 1st, 2nd, and 3rd.
   * 
   * @param placement The number
   * @return The placement string
   */
  public static String getPlacementString(int placement) {
    int rankChecker = placement % 100;
    if (rankChecker > 10 && rankChecker < 20)
      return placement + "th";
    rankChecker %= 10;
    switch (rankChecker) {
      case 1:
        return placement + "st";
      case 2:
        return placement + "nd";
      case 3:
        return placement + "rd";
    }
    return placement + "th";
  }

  /**
   * Gets a formatted time string from a number of seconds.
   * 
   * The format is minutes:seconds.
   * 
   * @param seconds The number of seconds
   * @return The formatted time string
   */
  public static String getFormattedTime(long seconds) {
    return String.format("%d:%02d", seconds / 60, seconds % 60);
  }

  // TODO: this does not match community-bots, the functionality differs slightly
  /**
   * Gets a formatted time string from a number of seconds.
   * 
   * The format is similar to "34.3 minutes".
   * 
   * @param seconds The number of seconds
   * @return The formatted time string
   */
  public static String getTextTime(double time) {
    if (time <= 0)
      return "0 seconds";
    if (time > 31536000) {
      time = Math.ceil(time / 31536000 * 10) / 10;
      return time + " years";
    } else if (time > 2628000) {
      time = Math.ceil(time / 2628000 * 10) / 10;
      return (time == 31536000) ? "1.0 year" : time + " months";
    } else if (time > 86400) {
      time = Math.ceil(time / 86400 * 10) / 10;
      return (time == 2628000) ? "1.0 month" : time + " days";
    } else if (time > 3600) {
      time = Math.ceil(time / 3600 * 10) / 10;
      return (time == 86400) ? "1.0 day" : time + " hours";
    } else if (time >= 60) {
      time = Math.ceil(time / 60 * 10) / 10;
      return (time == 3600) ? "1.0 hour" : time + " minutes";
    } else {
      int timeInt = (int) time;
      return timeInt == 1 ? "1 second" : timeInt + " seconds";
    }
  }

  // TODO rename these functions, they're not very descriptive and they're all too similar to each other
  /**
   * Gets a shortened time string from a number of seconds.
   * 
   * The format is similar to "29m8s".
   * 
   * @param i The number of seconds
   * @return The shortened time string
   */
  public static String getShortenedTime(long i) {
    long s = i % 60;
    long m = (long) Math.floor(i / 60);
    return m > 0 ? m + "m " + s + "s" : s + "s";
  }

  /**
   * Computes the Levenshtein distance between two strings.
   * 
   * This is the minimum number of single-character edits (insertions, deletions
   * or substitutions)
   * required to change one string into the other.
   * 
   * @param str1 The first string
   * @param str2 The second string
   * @return The Levenshtein distance
   */
  public static int computeLevenshteinDistance(String str1, String str2) {
    int[][] distance = new int[str1.length() + 1][str2.length() + 1];
    for (int i = 0; i <= str1.length(); i++)
      distance[i][0] = i;
    for (int j = 1; j <= str2.length(); j++)
      distance[0][j] = j;
    for (int i = 1; i <= str1.length(); i++)
      for (int j = 1; j <= str2.length(); j++)
        distance[i][j] = Math.min(Math.min(distance[i - 1][j] + 1, distance[i][j - 1] + 1),
            distance[i - 1][j - 1] + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));
    return distance[str1.length()][str2.length()];
  }

  public static String getShortenedTime(long time) {
    long y = 0;
    while (time >= 31536000) {
      time -= 31536000;
      y++;
    }

    long mo = 0;
    while (time >= 2628000) {
      time -= 2628000;
      mo++;
    }

    long d = 0;
    while (time >= 86400) {
      time -= 86400;
      d++;
    }

    long h = 0;
    while (time >= 3600) {
      time -= 3600;
      h++;
    }

    long m = 0;
    while (time >= 60) {
      time -= 60;
      m++;
    }

    long s = time;

    if (y > 0) {
      return y + "y " + mo + "mo " + d + "d " + h + "h " + m + "m " + s + "s";
    }
    if (mo > 0) {
      return mo + "mo " + d + "d " + h + "h " + m + "m " + s + "s";
    }
    if (d > 0) {
      return d + "d " + h + "h " + m + "m " + s + "s";
    }
    if (h > 0) {
      return h + "h " + m + "m " + s + "s";
    }
    if (m > 0) {
      return m + "m " + s + "s";
    }
    return s + "s";
  }
}
