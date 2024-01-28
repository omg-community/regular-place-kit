package games.omg.channeling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChannelingObject {

  private List<ExtendedChannelTime> channelTimes;
  private Runnable interrupt;
  private Runnable finish;

  public ChannelingObject(List<ExtendedChannelTime> channelTimes, Runnable finish, Runnable interrupt) {
    this.channelTimes = channelTimes;
    this.interrupt = interrupt;
    this.finish = finish;
  }

  public ChannelingObject(ExtendedChannelTime channelTime, Runnable finish, Runnable interrupt) {
    this.channelTimes = new ArrayList<>(Collections.singleton(channelTime));
    this.interrupt = interrupt;
    this.finish = finish;
  }

  public long getCurrentTime() {
    long time = 0;
    for (ExtendedChannelTime ect : channelTimes)
      time += ect.getCurrentTime();
    return time;
  }

  public long getStartingTime() {
    long time = 0;
    for (ExtendedChannelTime ect : channelTimes)
      time += ect.getStartingTime();
    return time;
  }

  public boolean next() {
    for (ExtendedChannelTime ect : channelTimes)
      if (ect.next())
        return true;
    return false;
  }

  public List<ExtendedChannelTime> getChannelTimes() {
    return channelTimes;
  }

  public void addChannelTime(ExtendedChannelTime ect) {
    channelTimes.add(ect);
  }

  public Runnable getFinishRunnable() {
    return finish;
  }

  public Runnable getInterruptRunnable() {
    return interrupt;
  }
}