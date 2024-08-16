package dev.ses.kits.listener;

import dev.ses.kits.Main;
import dev.ses.kits.manager.kit.Kit;
import dev.ses.kits.manager.kit.menu.KitPreviewMenu;
import dev.ses.kits.manager.kit.menu.KitSelectionMenu;
import dev.ses.kits.manager.kit.menu.KitsEditMenu;
import dev.ses.kits.utils.Color;
import dev.ses.kits.utils.StringUtil;
import dev.ses.kits.utils.Utils;
import dev.ses.kits.utils.sound.CompatibleSound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class KitListener implements Listener {

    private final Main main;

    public KitListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void otherActionWithChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (!main.getKitHandler().getVariantMap().containsKey(player.getUniqueId())) return;

        if (event.getMessage().equals("CANCEL")){
            new KitSelectionMenu(player, main).updateMenu();
            main.getKitHandler().getVariantMap().remove(player.getUniqueId());
            CompatibleSound.VILLAGER_NO.play(player);
            return;
        }

        if (main.getKitHandler().getVariantMap().get(player.getUniqueId()).equals("create")){
            main.getKitManager().createKit(message);
            main.getKitManager().saveAllKits();
            new KitSelectionMenu(player, main).updateMenu();
            main.getKitHandler().getVariantMap().remove(player.getUniqueId());
            return;
        }

        if (!main.getKitHandler().getKitMap().containsKey(player.getUniqueId())) return;
        Kit kit = main.getKitHandler().getKitMap().get(player.getUniqueId());

        if (main.getKitHandler().getVariantMap().get(player.getUniqueId()).equals("give")){
            Player target = Bukkit.getPlayer(message);

            if (event.getMessage().equals("CANCEL")){
                new KitSelectionMenu(player, main).updateMenu();
                main.getKitHandler().getVariantMap().remove(player.getUniqueId());
                CompatibleSound.VILLAGER_NO.play(player);
                return;
            }

            if (target == null){
                Utils.sendMessage(player, "This player does not exist.");
                CompatibleSound.IRONGOLEM_HIT.play(player);
                return;
            }
            kit.giveToOtherPlayer(player, target);
            new KitPreviewMenu(player, kit, main).updateMenu();
            main.getKitHandler().getVariantMap().remove(player.getUniqueId());
            main.getKitHandler().getKitMap().remove(player.getUniqueId());
            return;
        }

    }

    @EventHandler
    public void editKitWithChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (!main.getKitHandler().getVariantMap().containsKey(player.getUniqueId())) return;
        if (!main.getKitHandler().getKitMap().containsKey(player.getUniqueId())) return;
        event.setCancelled(true);

        Kit kit = main.getKitHandler().getKitMap().get(player.getUniqueId());

        if (event.getMessage().equals("CANCEL")){
            returnToEditMenu(player, kit);
            CompatibleSound.VILLAGER_NO.play(player);
            return;
        }

        switch (main.getKitHandler().getVariantMap().get(player.getUniqueId())){
            case "displayName":{
                kit.setDisplayName(Color.translate(message));
                returnToEditMenu(player, kit);
                CompatibleSound.VILLAGER_YES.play(player);
                return;
            }

            case "cooldown":{
                kit.setCooldown(StringUtil.formatLong(message));
                returnToEditMenu(player, kit);
                CompatibleSound.VILLAGER_YES.play(player);
                return;
            }

            case "slot":{
                kit.setIconSlot(Utils.getOrNull(player, message));
                returnToEditMenu(player, kit);
                CompatibleSound.VILLAGER_YES.play(player);
                return;
            }
        }
    }

    @EventHandler
    public void onEditKitWithInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if (!main.getKitHandler().getVariantMap().containsKey(player.getUniqueId())) return;
        if (!main.getKitHandler().getKitMap().containsKey(player.getUniqueId())) return;
        if (!main.getKitHandler().getVariantMap().get(player.getUniqueId()).equals("icon")) return;

        event.setCancelled(true);

        if (!event.getAction().name().startsWith("RIGHT_")) return;

        Kit kit = main.getKitHandler().getKitMap().get(player.getUniqueId());

        ItemStack item = event.getItem();

        kit.setMaterial(item.getType().name());
        kit.setItemData(item.getDurability());
        returnToEditMenu(player, kit);
        player.setItemInHand(null);
        CompatibleSound.VILLAGER_YES.play(player);

    }

    public void returnToEditMenu(Player player, Kit kit){
        new KitsEditMenu(player, kit, main).updateMenu();
        main.getKitHandler().getVariantMap().remove(player.getUniqueId());
        main.getKitHandler().getKitMap().remove(player.getUniqueId());
    }
}
