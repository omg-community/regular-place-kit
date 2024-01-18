package games.omg.resources;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

/**
 * A class which contains all of the colors used on the server.
 */
public class ServerColors {

  public static final TextColor OMG_FRIEND_COLOR = TextColor.fromHexString("#fad32a");
  public static final TextColor OMG_MOD_COLOR = TextColor.fromHexString("#84f6ff");
  public static final TextColor OMG_OWNER_COLOR = TextColor.fromHexString("#ffb5e8");

  public static final TextColor PRIMARY_COLOR = NamedTextColor.BLUE;
  public static final TextColor SECONDARY_COLOR = NamedTextColor.GRAY;
  
  public static final TextColor MOTD_ACCENT = OMG_FRIEND_COLOR;

  public static final TextColor COMMAND_COLOR = PRIMARY_COLOR;

  public static final TextColor REGULAR_ROLE_COLOR = OMG_FRIEND_COLOR;
  public static final TextColor MOD_ROLE_COLOR = OMG_MOD_COLOR;
  public static final TextColor OWNER_ROLE_COLOR = OMG_OWNER_COLOR;
}
