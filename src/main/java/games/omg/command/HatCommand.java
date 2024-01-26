package games.omg.command;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.md_5.bungee.api.ChatColor;

public class HatCommand {

  public TextComponent play(CommandSender sender, String label, String[] args) {
      Player p = (Player) sender;
      if (args.length >= 1) return Component.text("You don't need any arguments. (/" + label.toLowerCase() + ")");
      if (p.getInventory().getItemInMainHand().getType() == Material.AIR) return Component.text("You're not even holding anything.");
      //if (!p.getItemInHand().getType().isBlock() && !(p.getItemInHand().getType() == Material.BANNER)) return "You can't put items on your head.";
      ItemStack i = p.getInventory().getHelmet();
      ItemStack i2 = p.getInventory().getItemInMainHand();
      if (i != null && i.getType() != Material.AIR) {
          p.getInventory().setItemInMainHand(i);
      } else {
          p.getInventory().setItemInMainHand(null);
      }
      p.getInventory().setHelmet(i2);
      return Component.text("You are now wearing " + ChatColor.WHITE + WordUtils.capitalizeFully(i2.getType().name().replace("_", " ")) + ChatColor.RESET + ".");
  }

  public List<String> getAliases() {
      return Arrays.asList("hat");
  }

  public boolean canUse(CommandSender sender) {
      return true;
  }

  public boolean doesSuggestedCommandHaveSpace() {
      return false;
  }

  public TextComponent getDescription(CommandSender sender) {
      return Component.text("Put what you're holding on your head.");
  }
}