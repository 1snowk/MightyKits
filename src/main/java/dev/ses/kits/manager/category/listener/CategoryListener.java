package dev.ses.kits.manager.category.listener;

import dev.ses.kits.Main;
import dev.ses.kits.manager.category.menu.CategorySelectionMenu;
import dev.ses.kits.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class CategoryListener implements Listener {

    private final Main main;

    public CategoryListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void otherActionWithChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (!main.getCategoryHandler().getVariantMap().containsKey(player.getUniqueId())){
            return;
        }

        if (message.equals("CANCEL")){
            Utils.sendMessage(player, "&aThe process has been cancelled.");
            main.getCategoryHandler().getVariantMap().remove(player.getUniqueId());
            return;
        }

        if (main.getCategoryHandler().getVariantMap().get(player.getUniqueId()).equals("create2")){
            main.getCategoryManager().createCategory(message);
            main.getCategoryHandler().getVariantMap().remove(player.getUniqueId());
            new CategorySelectionMenu(player, main).updateMenu();
            return;
        }
    }
}
