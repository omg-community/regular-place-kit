package games.omg.utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import games.omg.Main;

public class TaskManager {
  /**
   * A list of server-specific tasks
   */
  final private static HashMap<String, Integer> serverTasks = new HashMap<String, Integer>();

  /**
   * A list of player-specific tasks
   */
  final private static HashMap<UUID, HashMap<String, Integer>> playerTasks = new HashMap<UUID, HashMap<String, Integer>>();

  /**
   * Stops a task in a task list, and overrides it with a new task id
   * 
   * @param task The task to override
   * @param taskId The new task id
   * @param taskList The task list to override the task in
   */
  private static void overrideTaskInTaskList(String task, int taskId, HashMap<String, Integer> taskList) {
    cancelTask(task, taskList);
    taskList.put(task, taskId);
  }

  /**
   * Cancels a task from a task list
   * 
   * @param task The task to cancel
   * @param taskList The task list to cancel the task from
   */
  private static void cancelTask(String task, HashMap<String, Integer> taskList) {
    if (taskList.containsKey(task)) {
      Bukkit.getScheduler().cancelTask(taskList.get(task));
      taskList.remove(task);
    }
  }

  /**
   * Checks if a task is running (or will eventually be running) in a task list
   * 
   * @param task The task to check
   * @param taskList The task list to check the task in
   */
  private static boolean isTaskRunning(String task, HashMap<String, Integer> taskList) {
    if (taskList.containsKey(task)) {
      int taskId = taskList.get(task);
      return Bukkit.getScheduler().isQueued(taskId) || Bukkit.getScheduler().isCurrentlyRunning(taskId);
    }
    return false;
  }
  
  /**
   * Starts a server task
   * 
   * @param task The task name
   * @param runnable The task to run
   * @param delay The delay before the task starts
   */
  public static void startTask(String task, Runnable runnable, long delay) {
    startTask(task, Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), runnable, delay));
  }

  /**
   * Starts a repeating server task
   * 
   * @param task The task name
   * @param runnable The task to run
   * @param delay The delay for the task starts
   * @param period The period between each task
   */
  public static void startRepeatingTask(String task, Runnable runnable, long delay, long period) {
    startTask(task, Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), runnable, delay, period));
  }
  
  /**
   * Starts a server task
   * 
   * @param task The task name
   * @param taskId The task's ID
   */
  public static void startTask(String task, int taskId) {
    overrideTaskInTaskList(task, taskId, serverTasks);
  }

  /**
   * Cancels a server task
   * 
   * @param task The task to cancel
   */
  public static void cancelTask(String task) {
    cancelTask(task, serverTasks);
  }

  /**
   * Starts a player task
   * 
   * @param player The player to start the task for
   * @param task The task name
   * @param runnable The task to run
   * @param delay The delay before the task starts
   */
  public static void startTask(Player player, String task, Runnable runnable, long delay) {
    startTask(player, task, Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), runnable, delay));
  }

  /**
   * Starts a repeating player task
   * 
   * @param player The player to start the task for
   * @param task The task name
   * @param runnable The task to run
   * @param delay The delay for the task starts
   * @param period The period between each task
   */
  public static void startRepeatingTask(Player player, String task, Runnable runnable, long delay, long period) {
    startTask(player, task, Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), runnable, delay, period));
  }
  
  /**
   * Starts a player task
   * 
   * @param player The player to start the task for
   * @param task The task name
   * @param taskId The task's ID
   */
  public static void startTask(Player player, String task, int taskId) {
    startTask(player.getUniqueId(), task, taskId);
  }

  /**
   * Starts a player task
   * 
   * @param uuid The player's UUID
   * @param task The task name
   * @param taskId The task's ID
   */
  public static void startTask(UUID uuid, String task, int taskId) {
    if (!playerTasks.containsKey(uuid)) {
      playerTasks.put(uuid, new HashMap<String, Integer>());
    }
    overrideTaskInTaskList(task, taskId, playerTasks.get(uuid));
  }

  /**
   * Cancels a player task
   * 
   * @param player The player to cancel the task for
   * @param task The task to cancel
   */
  public static void cancelTask(Player player, String task) {
    cancelTask(player.getUniqueId(), task);
  }

  /**
   * Cancels a player task
   * 
   * @param uuid The player's UUID
   * @param task The task to cancel
   */
  public static void cancelTask(UUID uuid, String task) {
    if (playerTasks.containsKey(uuid)) {
      cancelTask(task, playerTasks.get(uuid));
    }
  }

  /**
   * Cancels all server tasks
   */
  public static void cancelAllTasks() {
    for (String task : serverTasks.keySet()) {
      cancelTask(task, serverTasks);
    }
  }

  /**
   * Cancels all tasks for a player
   * 
   * @param player The player to cancel the tasks for
   */
  public static void cancelAllTasks(Player player) {
    cancelAllTasks(player.getUniqueId());
  }

  /**
   * Cancels all tasks for a player
   * 
   * @param uuid The player's UUID
   */
  public static void cancelAllTasks(UUID uuid) {
    if (playerTasks.containsKey(uuid)) {
      HashMap<String, Integer> tasks = playerTasks.get(uuid);
      for (String task : tasks.keySet()) {
        cancelTask(task, tasks);
      }
    }
    playerTasks.remove(uuid);
  }

  /**
   * Checks if a server task is running (or will eventually be running)
   * 
   * @param task The task to check
   * @return Whether the task is running
   */
  public static boolean isTaskRunning(String task) {
    return isTaskRunning(task, serverTasks);
  }

  /**
   * Checks if a player task is running (or will eventually be running)
   * 
   * @param player The player to check the task for
   * @param task The task to check
   * @return Whether the task is running
   */
  public static boolean isTaskRunning(Player player, String task) {
    return isTaskRunning(player.getUniqueId(), task);
  }

  /**
   * Checks if a player task is running (or will eventually be running)
   * 
   * @param uuid The player's UUID
   * @param task The task to check
   * @return Whether the task is running
   */
  public static boolean isTaskRunning(UUID uuid, String task) {
    if (playerTasks.containsKey(uuid)) {
      return isTaskRunning(task, playerTasks.get(uuid));
    }
    return false;
  }
}