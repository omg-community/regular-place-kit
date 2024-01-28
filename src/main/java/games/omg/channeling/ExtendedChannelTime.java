package games.omg.channeling;

import org.bukkit.Material;

public class ExtendedChannelTime {

  private long startingTime;
  private long currentTime;
  private String reason;
  private Material displayItem;
  private String description;

  public ExtendedChannelTime(long time, String reason, Material displayItem, String description) {
    startingTime = time;
    this.currentTime = time;
    this.reason = reason;
    this.displayItem = displayItem;
    this.description = description;
  }

  public long getStartingTime() {
    return startingTime;
  }

  public long getCurrentTime() {
    return currentTime;
  }

  public void addTime(long time) {
    currentTime += time;
    startingTime += time;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean next() {
    if (currentTime <= 0)
      return false;
    currentTime--;
    return true;
  }

  public String getReason() {
    return reason;
  }

  public Material getDisplayItem() {
    return displayItem;
  }

  public String getDescription() {
    return description;
  }
}