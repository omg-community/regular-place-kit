package games.omg.legacy.builds;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

/**
 * An object which represents a build.
 */
public class UGCBuild extends UGC {

  private UUID world;
  private double x;
  private double y;
  private double z;
  private float yaw;
  private float pitch;

  /**
   * Creates a build object.
   * 
   * @param id The unique identifier of the build
   * @param owner The owner of the build
   * @param title The title of the build
   * @param genre The genre of the build
   * @param privacy The privacy of the build
   * @param season The season in which the build was created
   * @param creationTime The time the build was created
   * @param world The world the build is in
   * @param x The x coordinate of the build
   * @param y The y coordinate of the build
   * @param z The z coordinate of the build
   * @param yaw The yaw of the build
   * @param pitch The pitch of the build
   */
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
