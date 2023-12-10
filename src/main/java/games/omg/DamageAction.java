package games.omg;

import org.bukkit.entity.Entity;

public class DamageAction {

	final private Entity damager;
	final private String cause;
	final private long tick;

	public DamageAction(Entity damager, String cause, long tick) {
		this.damager = damager;
		this.cause = cause;
		this.tick = tick;
	}

	public Entity getDamager() {
		return damager;
	}

	public String getCause() {
		return cause;
	}

	public long getTick() {
		return tick;
	}
}
