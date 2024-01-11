package games.omg.legacy.builds;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

public class UGCBuild extends UGC {

  private UUID world;
  private double x;
  private double y;
  private double z;
  private float yaw;
  private float pitch;

  public UGCBuild(Integer id, UUID owner, String title, UGCGenre genre, UGCPrivacy privacy, Season season,
      long creationTime, UUID world, double x, double y, double z, float yaw, float pitch) {
    super(id, owner, title, genre, privacy, season, creationTime);
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
    this.yaw = yaw;
    this.pitch = pitch;
  }

  //

  public UUID getWorld() {
    return world;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

  public float getYaw() {
    return yaw;
  }

  public float getPitch() {
    return pitch;
  }

  public void setLocation(Location location) {
    if (location.getWorld() == null) {
      world = Bukkit.getWorlds().get(0).getUID();
    } else {
      world = location.getWorld().getUID();
    }

    x = location.getX();
    y = location.getY();
    z = location.getZ();
    yaw = location.getYaw();
    pitch = location.getPitch();
  }

  public Location getLocation() {
    return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
  }
}
