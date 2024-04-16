package games.omg.security;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
// import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;

public class SafeExplosions implements Listener {

    private final Logger logger = Logger.getLogger("Minecraft");

    private final List<EntityType> protectedEntities = List.of(
        EntityType.CAT,
        EntityType.WOLF,
        
        EntityType.PARROT,
        
        EntityType.HORSE,
        EntityType.SKELETON_HORSE,
        EntityType.ZOMBIE_HORSE,
        EntityType.MULE,
        EntityType.DONKEY,
        EntityType.CAMEL,

        EntityType.LLAMA,
        EntityType.TRADER_LLAMA,

        EntityType.ARMOR_STAND,

        // i Genuinely cant tell if these actually do anything, but whatever
        EntityType.ITEM_FRAME,
        EntityType.GLOW_ITEM_FRAME
    );

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getLocation().getWorld().getEnvironment() != Environment.NORMAL) {
            return;
        }

        // Stop destroying BLOCKS !
        Iterator<Block> iterator = event.blockList().iterator();

        while (iterator.hasNext()) {
            Block block = iterator.next();

            // anything with a inventory, any sign, or a mob spawner :D
            if (
                (block.getState() instanceof Container) || 
                (block.getState() instanceof Sign) || 
                (block.getType() == Material.SPAWNER)
            ) {
                iterator.remove();
                continue;
            }

            // should we protect the blocks armor stands/signs are on?
            // i feel like its more hassle than its worth for a one time use thing
            // if we choose to, we need to protect structural blocks, stuff like scaffolding, dripstone, vines, etc
            // blocks that will chain destroy themselves if one part is cut off
            // this also applys for item frames/paintings, but is it really worth it to safe guard those?
        }
    }

    @EventHandler
    public void onExplosionDamage(EntityDamageEvent event) {
        // if not overworld and if not block/entity explosion damage
        if (
            (event.getEntity().getWorld().getEnvironment() != Environment.NORMAL) ||
            (event.getCause() != EntityDamageEvent.DamageCause.BLOCK_EXPLOSION && event.getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
        ) {
            return;
        }

        Entity ent = event.getEntity();

        if (
            (ent.getType() == EntityType.DROPPED_ITEM || event.getEntity() instanceof Item) || 
            (event.getEntity().customName() != null || protectedEntities.stream().anyMatch(en -> event.getEntityType().equals(en)))
        ) {
            event.setCancelled(true);
        }
    }

    // Specifically for paintings and item frames (mainly item frames)
    @EventHandler
    public void onBreakHangingEntity(HangingBreakEvent event) {
        if (event.getEntity().getLocation().getWorld().getEnvironment() != Environment.NORMAL) {
            return;
        }
        
        if (!(event.getEntity() instanceof ItemFrame)) {
            return;
        }

        if (event.getCause() == HangingBreakEvent.RemoveCause.EXPLOSION) {
            event.setCancelled(true);
        }
    }
}