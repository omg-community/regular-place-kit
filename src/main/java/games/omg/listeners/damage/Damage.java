package games.omg.listeners.damage;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;

/**
 * An object which represents damage taken by an entity.
 */
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

	public static Damage invoke(Damageable entity, double health, String cause) {
		return invoke(entity, health, null, cause);
	}

	public static Damage invoke(Damageable entity, double health, Entity damager) {
		return invoke(entity, health, damager, null);
	}

	public static Damage invoke(Damageable entity, double health, Entity damager, String cause) {
		Damage damage = new Damage(damager, cause);

		entity.damage(health);
		DamageHandler.recordDamage(entity, damage);

		return damage;
	}
}
