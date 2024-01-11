package games.omg.legacy.builds;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.UUID;

public class UGCWorld extends UGC {

  final private UUID uuid;
  final private WorldType worldType;

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
