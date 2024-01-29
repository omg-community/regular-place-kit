package games.omg.resources;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

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

    /**
     * Gets the die representation of a number.
     * 
     * If the number is not 1-6, or is a non-number, an empty string is returned.
     * 
     * @param number The number
     * @return The die representation
     */
    public static String get(int number) {
      try {
        return DICE.charAt(number - 1) + "";
      } catch (IndexOutOfBoundsException e) {
        return "";
      }
    }

    /**
     * Gets the dice representations of a string containing a set of numbers, 1-6.
     * 
     * For any number which is not 1-6, or any non-numbers, an empty string is returned for that character.
     * 
     * @param numbers The string of numbers
     * @return The dice representations
     */
    public static String get(String numbers) {
      StringBuilder builder = new StringBuilder();
      for (char number : numbers.toCharArray()) {
        try {
          builder.append(get(Integer.parseInt(number + "")));
        } catch (NumberFormatException e) {
        }
      }
      return builder.toString();
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

    /**
     * Gets the circled version of a letter.
     * 
     * This is case sensitive and supports both upper and lower case letters.
     * If the letter is not in the alphabet, an empty string is returned.
     * 
     * @param letter The letter
     * @return The circled letter
     */
    public static String get(char letter) {
      try {
        return CIRCLED_LETTERS.charAt(DIRECTORY.indexOf(letter)) + "";
      } catch (IndexOutOfBoundsException e) {
        return "";
      }
    }

    /**
     * Gets the circled versions of a string containing letters.
     * 
     * This is case sensitive and supports both upper and lower case letters.
     * If a letter is not in the alphabet, an empty string is returned for that character.
     * 
     * @param word The string of letters
     * @return The circled letters
     */
    public static String get(String word) {
      StringBuilder builder = new StringBuilder();
      for (char letter : word.toCharArray()) {
        builder.append(get(letter));
      }
      return builder.toString();
    }
  }

  /**
   * A class which maps letters to their small versions.
   */
  public static class SmallLetters {
    private static final String SMALL_LETTERS = "ᴀʙᴄᴅᴇғɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢ";
    private static final String DIRECTORY = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Gets the small version of a letter.
     * 
     * This is case insensitive, and will return a small uppercase letter in any case.
     * If the character is not in the alphabet, it will be returned as itself.
     * 
     * @param letter The letter
     * @return The small letter
     */
    public static String get(char letter) {
      try {
        return SMALL_LETTERS.charAt(DIRECTORY.indexOf((letter + "").toUpperCase())) + "";
      } catch (IndexOutOfBoundsException e) {
        return letter + "";
      }
    }

    /**
     * Gets the small versions of a string containing letters.
     * 
     * This is case insensitive, and will return a small uppercase letter in any case.
     * If a character in the string is not in the alphabet, it is returned as itself.
     * 
     * @param word The string of letters
     * @return The small letters
     */
    public static String get(String word) {
      StringBuilder builder = new StringBuilder();
      for (char letter : word.toCharArray()) {
        builder.append(get(letter));
      }
      return builder.toString();
    }
  }

  /**
   * A class which maps numbers to their circled versions.
   * 
   * It only supports numbers 1-20.
   */
  public static class CircledNumbers {
    private static final String CIRCLED_NUMBERS = "①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳";

    /**
     * Gets the circled version of a number.
     * 
     * If the number is not 1-20, or is a non-number, the number is returned as a string.
     * 
     * @param number The number
     * @return The circled number
     */
    public static String get(int number) {
      try {
        return CIRCLED_NUMBERS.charAt(number - 1) + "";
      } catch (IndexOutOfBoundsException e) {
        return number + "";
      }
    }
  }

  /**
   * Creates a system message. Used in older code.
   * 
   * @param color   The color
   * @param prefix  The prefix
   * @param message The message
   * @return The system message
   * 
   * @deprecated Use SystemMessage instead.
   */
  public static Component createSystemMessage(TextColor color, String prefix, Component message) {
    return Component.text()
        .color(NamedTextColor.GRAY)
        .append(Component.text(prefix).color(color))
        .append(Component.space())
        .append(message)
        .build();
  }
}
