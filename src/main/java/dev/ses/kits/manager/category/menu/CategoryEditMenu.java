package dev.ses.kits.manager.category.menu;

import dev.ses.kits.manager.category.Category;
import dev.ses.kits.utils.menu.Menu;
import org.bukkit.entity.Player;

public class CategoryEditMenu extends Menu {

    private Category category;

    public CategoryEditMenu(Player player, Category category) {
        super(player, "&cEditing: &f" + category.getName(), 6*9);
        this.category = category;
    }

    @Override
    public void tick() {

    }
}
