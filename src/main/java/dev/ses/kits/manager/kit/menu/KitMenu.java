package dev.ses.kits.manager.kit.menu;



import dev.ses.kits.Main;
import dev.ses.kits.manager.category.Category;
import dev.ses.kits.manager.category.menu.CategoryMenu;
import dev.ses.kits.manager.kit.Kit;

import dev.ses.kits.utils.CooldownUtil;
import dev.ses.kits.utils.Utils;
import dev.ses.kits.utils.item.ItemBuilder;
import dev.ses.kits.utils.menu.Menu;
import dev.ses.kits.utils.menu.buttons.Button;
import dev.ses.kits.utils.sound.CompatibleSound;
import org.bukkit.entity.Player;


public class KitMenu extends Menu{

    private final Main main;

    public KitMenu(Player player, Main main) {
        super(player, main.getConfigFile().getString("KITS-MENU.TITLE"), main.getConfigFile().getInt("KITS-MENU.ROWS")*9);
        this.main = main;
    }

    @Override
    public void tick() {
        if (main.getConfigFile().getBoolean("CATEGORIES.ENABLED")){
            for (Category category : main.getCategoryManager().getCategoryList()){
                this.buttons[category.getIconSlot()] = new Button(category.getIcon()).setDisplayName(category.getDisplayName()).setClickAction(event -> {
                    new CategoryMenu(getPlayer(), category, main).updateMenu();
                });
            }
            return;
        }

        for (Kit kits : main.getKitManager().getKitList()){
            this.buttons[kits.getIconSlot()] = new Button(new ItemBuilder(kits.getIcon().clone()).build()).setLore(kits.getNewLore(getPlayer())).setClickAction(event -> {
                kits.execute(getPlayer(), event, this);
            });
        }

    }
}
