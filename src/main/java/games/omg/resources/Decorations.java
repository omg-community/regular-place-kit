package games.omg.resources;

/**
 * A class which contains various decorations.
 */
public class Decorations {

  public static final String THIN_HEART = "♥";
  public static final String HOLLOWED_THIN_HEART = "♡";
  public static final String HEART = "❤";
  public static final String STAR = "★";
  public static final String HOLLOWED_STAR = "☆";
  public static final String POINTED_STAR = "✳";
  public static final String THICK_POINTED_STAR = "✴";
  public static final String SHARP = "♯";
  public static final String FLAT = "♭";
  public static final String EIGHTH_NOTE = "♪";
  public static final String QUARTER_NOTE = "♩";
  public static final String BEAMED_EIGHTH_NOTE = "♫";
  public static final String BEAMED_SIXTEENTH_NOTE = "♬";
  public static final String SMALL_BULLET_POINT = "•";
  public static final String DIAMOND = "◆";
  public static final String HOLLOWED_DIAMOND = "◇";
  public static final String TALL_DIAMOND = "♦";
  public static final String HOLLOWED_TALL_DIAMOND = "♢";
  public static final String SMALL_SQUARE = "■";
  public static final String HOLLOWED_SMALL_SQUARE = "□";
  public static final String BULLET_POINT = "●";
  public static final String HOLLOWED_BULLET_POINT = "○";
  public static final String CROSSED_CIRCLE = "⌀";
  public static final String X = "✕";
  public static final String THICK_X = "✖";
  public static final String CHECK_MARK = "✔";
  public static final String BALLOT_BOX = "☐";
  public static final String BALLOT_BOX_WITH_X = "☒";
  public static final String BALLOT_BOX_WITH_CHECK = "☑";
  public static final String RIGHT_DOUBLE_ARROW = "⇒";
  public static final String RIGHT_ARROW_WITH_UPWARDS_HOOK = "➥";
  public static final String RIGHT_ARROW_WITH_DOWNWARDS_HOOK = "➦";
  public static final String RIGHT_ARROW_WITH_DROP_SHADOW = "➭";
  public static final String THIN_RIGHT_ARROW_WITH_DROP_SHADOW = "➯";
  public static final String CIRCLED_RIGHT_ARROW = "➲";
  public static final String RIGHT_TRIANGLE = "▶";
  public static final String LEFT_TRIANGLE = "◀";
  public static final String SMILEY = "☻";
  public static final String SMILE = "☺";
  public static final String FROWN = "☹";
  public static final String PENCIL = "✎";
  public static final String HOUSE = "⌂";
  public static final String TRIGRAM_HEAVEN = "☰";
  public static final String TRIGRAM_WATER = "☵";
  public static final String SHIELD = "🛡";
  public static final String BOW_AND_ARROW = "🏹";
  public static final String THUNDERSTORM = "☈";
  public static final String NUMERO = "№";
  public static final String CURRENCY_SIGN = "¤";
  public static final String WATCH = "⌚";
  public static final String SUN = "☀";
  public static final String CLOUD = "☁";
  public static final String AIRPLANE = "✈";
  public static final String HOURGLASS = "⌛";
  public static final String UMBRELLA = "☂";
  public static final String SNOWFLAKE = "❄";
  public static final String SNOWMAN = "☃";
  public static final String ENVELOPE = "✉";
  public static final String SKULL = "☠";
  public static final String FLOWER = "✿";
  public static final String AXES = "✥";
  public static final String COMET = "☄";
  public static final String MOON = "☽";
  public static final String SPARKLE = "❇";
  public static final String THIN_BLOCK = "▌";

  /**
   * A class which maps a number to a die representation of that number.
   * 
   * This only supports numbers 1-6.
   */
  public static class Dice {
    private static final String DICE = "⚀⚁⚂⚃⚄⚅";

    public static String get(int number) {
      try {
        return DICE.charAt(number - 1) + "";
      } catch (IndexOutOfBoundsException e) {
        return "";
      }
    }
  }

  /**
   * A class which maps letters to their circled versions.
   * 
   * It is case sensitive, and contains both upper and lower case letters.
   */
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

  /**
   * A class which maps numbers to their circled versions.
   * 
   * It only supports numbers 1-20.
   */
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
