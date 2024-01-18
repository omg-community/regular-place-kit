package games.omg.listeners.damage;

/**
 * An interface which represents damage taken by an entity.
 * 
 * This currently isn't used.
 * 
 * It was created to allow for custom messages such as:
 * iilwy fell into the hole.
 */
public interface IDamage {
  public String getCustomText();
}
