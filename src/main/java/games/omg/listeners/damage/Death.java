package games.omg.listeners.damage;

import java.util.List;

import org.bukkit.entity.Entity;

public class Death {
    private Entity killed;
    private Entity killer;
    private List<Entity> assists;
    private List<String> causes;

    public Death(Entity killed, Entity killer, List<Entity> assists, List<String> causes) {
      this.killed = killed;
      this.killer = killer;
      this.assists = assists;
      this.causes = causes;
    }

    public Entity getKilled() {
      return killed;
    }

    public Entity getKiller() {
      return killer;
    }

    public List<Entity> getAssists() {
      return assists;
    }

    public List<String> getCauses() {
      return causes;
    }
  }