package games.omg.legacy.builds;

import java.util.UUID;

public class UGC {

  final private Integer id;
  final private UUID owner;
  private String title;
  private UGCGenre genre;
  private UGCPrivacy privacy;
  final private Season season;
  final private long creationTime;

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
