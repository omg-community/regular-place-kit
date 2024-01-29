package games.omg.server;

public class RunService {

  // use the config for this at some point
  private static boolean production = false;
  
  public static boolean isProduction() {
    return production;
  }

  public static boolean isDevelopment() {
    return !production;
  }
}
