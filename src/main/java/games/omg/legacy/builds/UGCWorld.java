package games.omg.legacy.builds;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.UUID;

/**
 * An object which represents a world which a user can create.
 */
public class UGCWorld extends UGC {

  final private UUID uuid;
  final private WorldType worldType;

  /**
   * Creates a world object.
   * 
   * @param id The unique identifier of the world
   * @param owner The owner of the world
   * @param title The title of the world
   * @param genre The genre of the world
   * @param privacy The privacy of the world
   * @param season The season in which the world was created
   * @param creationTime The time the world was created
   * @param uuid The UUID of the world
   * @param worldType The type of the world
   */
  public UGCWorld(Integer id, UUID owner, String title, UGCGenre genre, UGCPrivacy privacy, Season season,
      long creationTime, UUID uuid, WorldType worldType) {
    super(id, owner, title, genre, privacy, season, creationTime);
    this.uuid = uuid;
    this.worldType = worldType;
  }

  //

  public UUID getUUID() {
    return uuid;
  }

  public WorldType getWorldType() {
    return worldType;
  }

  public World getWorld() {
    return Bukkit.createWorld(new WorldCreator(uuid.toString()));
  }
}
