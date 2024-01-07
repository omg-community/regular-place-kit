package games.omg.utils;

import java.text.DecimalFormat;
import java.util.List;

public class StringUtils {
  public static String makePossessive(String name) {
    if (name.endsWith("s")) {
      return name + "'";
    } else {
      return name + "'s";
    }
  }

  public static String createGrammaticalList(List<String> items) {
    int listSize = items.size();

    if (listSize == 0) return "";
    if (listSize == 1) return items.get(0);
    if (listSize == 2) return items.get(0) + " and " + items.get(1);
    
    StringBuilder builder = new StringBuilder(items.get(0));
    for (int i = 1; i < listSize; i++) {
      builder.append(i == listSize - 1 ? ", and" : ", ");
      builder.append(items.get(i));
    }

    return builder.toString();
  }

  public static String separateListWith(List<String> items, String separator) {
    int listSize = items.size();

    if (listSize == 0) return "";
    if (listSize == 1) return items.get(0);
    
    StringBuilder builder = new StringBuilder(items.get(0));
    for (int i = 1; i < listSize; i++) {
      builder.append(separator);
      builder.append(items.get(i));
    }

    return builder.toString();
  }
  
  public static String getCommaSeparatedNumber(int num) {
    DecimalFormat formatter = new DecimalFormat("#,###");
    return formatter.format(num);
  }

  public static String getPlacementString(int placement) {
    int rankChecker = placement % 100;
    if (rankChecker > 10 && rankChecker < 20) return placement + "th";
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

  public static String getFormattedTime(long seconds) {
    return String.format("%d:%02d", seconds / 60, seconds % 60);
  }

  public static String getTextTime(double time) {
    if (time <= 0) return "0 seconds";
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

  public static int computeLevenshteinDistance(String str1,String str2) {
    int[][] distance = new int[str1.length() + 1][str2.length() + 1];
    for (int i = 0; i <= str1.length(); i++) distance[i][0] = i;
    for (int j = 1; j <= str2.length(); j++) distance[0][j] = j;
    for (int i = 1; i <= str1.length(); i++) for (int j = 1; j <= str2.length(); j++) distance[i][j] = Math.min(Math.min(distance[i - 1][j] + 1,distance[i][j - 1] + 1),distance[i - 1][j - 1] + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));
    return distance[str1.length()][str2.length()];
  }
}
