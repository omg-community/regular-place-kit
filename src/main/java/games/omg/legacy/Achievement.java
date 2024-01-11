package games.omg.legacy;

public enum Achievement {

  // PIXEL_ART_WINNER("?????", "Win a season with your favorite build type being pixel art."),

  POPULAR("Popular", "Get 75 eggs."),
  // FLYING_THE_SKIES("Flying the Friendly Skies", "Create 6 qualified elytra surf builds."),

  // THE_FINAL_FIVE("The Final Five", "Finish in at least fifth place at the end of a season."),
  NOW_PLAYING("Now Playing", "Get a qualified build featured in the big text at spawn."),
  METICULOUS("Meticulous", "Get a build qualified within the meticulous zone."),
  PERFECTIONIST("Perfectionist", "Get 5 builds qualified within the meticulous zone."),
  // SPRITES("Sprites!", "Create 10 qualified pixel art builds."),
  BIG_BUILD("Big Build", "Get 350 points on a single build."),
  MONEY_BAGS("Money Bags", "Have 3000 cash at any one time."),
  CHAMPION("The Champion", "Have the most points at the end of a season."),
  // GM("gm", "Create a qualified minigame worth at least 90 points."), // but how?
  NEVERENDING_BUILDING("idk man", "Create 100 qualified builds.");

  //

  String name;
  String description;

  Achievement(String name, String description) {
    this.name = name;
    this.description = description;
  }
}
