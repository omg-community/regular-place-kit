package games.omg.legacy.builds;

import java.util.UUID;

/**
 * An object which represents a user generated content.
 */
public class UGC {

  final private Integer id;
  final private UUID owner;
  private String title;
  private UGCGenre genre;
  private UGCPrivacy privacy;
  final private Season season;
  final private long creationTime;

  /**
   * Creates a user generated content object.
   * 
   * @param id The unique identifier of the UGC
   * @param owner The owner of the UGC
   * @param title The title of the UGC
   * @param genre The genre of the UGC
   * @param privacy The privacy of the UGC
   * @param season The season in which the UGC was created
   * @param creationTime The time the UGC was created
   */
  public UGC(Integer id, UUID owner, String title, UGCGenre genre, UGCPrivacy privacy, Season season,
      long creationTime) {
    this.id = id;
    this.owner = owner;
    this.title = title;
    this.genre = genre;
    this.privacy = privacy;
    this.season = season;
    this.creationTime = creationTime;
  }

  //

  public Integer getId() {
    return id;
  }

  public UUID getOwner() {
    return owner;
  }

  public String getTitle() {
    return title;
  }

  public UGCGenre getGenre() {
    return genre;
  }

  public UGCPrivacy getPrivacy() {
    return privacy;
  }

  public Season getSeason() {
    return season;
  }

  public long getCreationTime() {
    return creationTime;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setGenre(UGCGenre genre) {
    this.genre = genre;
  }

  public void setPrivacy(UGCPrivacy privacy) {
    this.privacy = privacy;
  }
}
