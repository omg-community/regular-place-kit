package games.omg.utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TaskManager {
  private static HashMap<String, Integer> tasks = new HashMap<String, Integer>();
  private static HashMap<String, Integer> alwaystasks = new HashMap<String, Integer>();
  private static HashMap<UUID, HashMap<String, Integer>> ptasks = new HashMap<UUID, HashMap<String, Integer>>();

  public static void initTask(String task, int taskId) {
    initTask(task, taskId, tasks);
  }

  private static void initTask(String task, int taskId, HashMap<String, Integer> hs) {
    if (hs.containsKey(task)) {
      Bukkit.getScheduler().cancelTask(hs.get(task));
      hs.remove(task);
    }
    hs.put(task, taskId);
  }

  public static void initAlwaysTask(String task, int taskId) {
    initTask(task, taskId, alwaystasks);
  }

  public static void initTask(Player p, String task, int taskId) {
    initTask(p.getUniqueId(), task, taskId);
  }

  public static void initTask(UUID u, String task, int taskId) {
    stopTaskAdd(u, task, taskId);
  }

  public static void stopTask(String task) {
    stopTask(task, tasks);
  }

  private static void stopTask(String task, HashMap<String, Integer> hs) {
    if (hs.containsKey(task)) {
      Bukkit.getScheduler().cancelTask(hs.get(task));
      hs.remove(task);
    }
  }

  public static void stopAlwaysTask(String task) {
    stopTask(task, alwaystasks);
  }

  public static void stopTask(Player p, String task) {
    stopTask(p.getUniqueId(), task);
  }

  public static void stopTask(UUID u, String task) {
    boolean cnt = ptasks.containsKey(u);
    HashMap<String, Integer> pts = cnt ? ptasks.get(u) : new HashMap<String, Integer>();
    if (cnt) {
      if (pts.containsKey(task)) {
        Bukkit.getScheduler().cancelTask(pts.get(task));
        pts.remove(task);
      }
    }
    ptasks.put(u, pts);
  }

  private static void stopTaskAdd(UUID u, String task, int taskId) {
    boolean cnt = ptasks.containsKey(u);
    HashMap<String, Integer> pts = cnt ? ptasks.get(u) : new HashMap<String, Integer>();
    if (cnt) {
      if (pts.containsKey(task)) {
        Bukkit.getScheduler().cancelTask(pts.get(task));
        pts.remove(task);
      }
    }
    pts.put(task, taskId);
    ptasks.put(u, pts);
  }

  public static void stopTasks() {
    stopTasks(tasks);
    for (UUID u : ptasks.keySet())
      stopTasks(ptasks.get(u));
  }

  private static void stopTasks(HashMap<String, Integer> hs) {
    for (String s : hs.keySet())
      Bukkit.getScheduler().cancelTask(hs.get(s));
    hs.clear();
  }

  public static void stopAlwaysTasks() {
    stopTasks(alwaystasks);
  }

  public static void stopTasks(Player p) {
    stopTasks(p.getUniqueId());
  }

  public static void stopTasks(UUID u) {
    if (!ptasks.containsKey(u))
      return;
    HashMap<String, Integer> hs = ptasks.get(u);
    for (String s : hs.keySet())
      Bukkit.getScheduler().cancelTask(hs.get(s));
    hs.clear();
    ptasks.put(u, hs);
  }

  public static boolean isTaskRunning(String task) {
    return isTaskRunning(task, tasks);
  }

  private static boolean isTaskRunning(String task, HashMap<String, Integer> hs) {
    return hs.containsKey(task);
  }

  public static boolean isAlwaysTaskRunning(String task) {
    return isTaskRunning(task, alwaystasks);
  }

  public static boolean isTaskRunning(Player p, String task) {
    return isTaskRunning(p.getUniqueId(), task);
  }

  public static boolean isTaskRunning(UUID u, String task) {
    return ptasks.containsKey(u) ? ptasks.get(u).containsKey(task) : false;
  }
}