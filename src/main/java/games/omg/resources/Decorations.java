package games.omg.resources;

import java.util.HashMap;

public class Decorations {
  
  public static final String ENVELOPE = "✉";
  public static final String PENCIL = "✎";
  public static final String SKULL = "☠";
  public static final String FLOWER = "✿";
  public static final String STAR_FILLED = "★";
  public static final String STAR_EMPTY = "☆";
  public static final String AXES = "✥";
  public static final String METEOR = "☄";
  public static final String CROSS_THICK = "✖";
  public static final String CROSS_REGULAR = "✕";
  public static final String CHECKMARK = "✔";
  public static final String MUSIC_NOTE = "♪";
  public static final String MUSIC_NOTE_DOUBLE = "♫";
  public static final String LONG_DIAMOND_FILLED = "♦";
  public static final String LONG_DIAMOND_EMPTY = "♢";
  public static final String DIAMOND_FILLED = "◆";
  public static final String DIAMOND_EMPTY = "◇";
  public static final String CROSSED_CIRCLE = "⌀";
  public static final String HOUSE = "⌂";
  public static final String BULLET_THICK = "●";
  public static final String BULLET_REGULAR = "•";
  public static final String CRESCENT_MOON = "☽";

  public static class CircledLetters {
    private static final String CIRCLED_LETTERS = "ⒶⒷⒸⒹⒺⒻⒼⒽⒾⒿⓀⓁⓂⓃⓄⓅⓆⓇⓈⓉⓊⓋⓌⓍⓎⓏⓐⓑⓒⓓⓔⓕⓖⓗⓘⓙⓚⓛⓜⓝⓞⓟⓠⓡⓢⓣⓤⓥⓦⓧⓨⓩ";
    private static final String DIRECTORY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String get(String letter) {
      try {
        return CIRCLED_LETTERS.charAt(DIRECTORY.indexOf(letter)) + "";
      } catch (IndexOutOfBoundsException e) {
        return "";
      }
    }
  }

  public static class CircledNumbers {
    public static final String CIRCLED_NUMBERS = "①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳";

    public static String get(int number) {
      try {
        return CIRCLED_NUMBERS.charAt(number - 1) + "";
      } catch (IndexOutOfBoundsException e) {
        return "";
      }
    }
  }
}
