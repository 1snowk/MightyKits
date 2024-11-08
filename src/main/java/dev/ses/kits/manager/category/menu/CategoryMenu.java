package dev.ses.kits.manager.category.menu;

import dev.ses.kits.Main;
import dev.ses.kits.manager.category.Category;
import dev.ses.kits.manager.kit.Kit;
import dev.ses.kits.utils.menu.Menu;
import dev.ses.kits.utils.menu.buttons.Button;
import org.bukkit.entity.Player;

public class CategoryMenu extends Menu {

    private final Main main;
    private final Category category;

    public CategoryMenu(Player player, Category category, Main main) {
        super(player, category.getTitle(), category.getRows()*9);
        this.main = main;
        this.category = category;
    }

    @Override
    public void tick() {
        for (Kit kits : category.getKitsList()){
            this.buttons[kits.getIconSlot()] = new Button(kits.getIcon()).setLore(kits.getNewLore(getPlayer())).setClickAction(event -> {
                kits.execute(getPlayer(), event, this);
            });
        }
    }
}
