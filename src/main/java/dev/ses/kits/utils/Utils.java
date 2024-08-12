package dev.ses.kits.utils;


import dev.ses.kits.utils.sound.CompatibleSound;
import lombok.experimental.UtilityClass;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


@UtilityClass
public class Utils {

    public void sendMessage(CommandSender sender, String string){
        sender.sendMessage(Color.translate(string));
    }

    public static void addItemOrDrop(Player player, ItemStack itemStack) {
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
        } else {
            player.getInventory().addItem(itemStack);
        }
    }

    public void noPerms(Player player){
        sendMessage(player, "&cYou dont have permissions.");
        CompatibleSound.VILLAGER_NO.play(player);
    }

    public void sendMessage(Player player, String string){
        player.sendMessage(Color.translate(string));
    }

    public int getOrNull(Player player, String message){
        try {
            return Integer.parseInt(message);
        }catch (NumberFormatException e){
            sendMessage(player, "&cThat is not a number, the value has been set at 0.");
            return 0;
        }
    }
}
