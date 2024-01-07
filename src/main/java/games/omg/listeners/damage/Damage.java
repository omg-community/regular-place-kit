package games.omg.listeners.damage;

import org.bukkit.entity.Entity;

public class Damage {

	final private Entity damager;
	final private String cause;
	final private long time;

	public Damage(Entity damager, String cause) {
		this(damager, cause, System.currentTimeMillis());
	}

	public Damage(Entity damager, String cause, long time) {
		this.damager = damager;
		this.cause = cause;
		this.time = time;
	}

	public Entity getDamager() {
		return damager;
	}

	public String getCause() {
		return cause;
	}

	public long getTime() {
		return time;
	}
}
