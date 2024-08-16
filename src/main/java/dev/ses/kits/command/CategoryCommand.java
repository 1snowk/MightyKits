package dev.ses.kits.command;

import dev.ses.kits.Main;
import dev.ses.kits.manager.category.menu.CategorySelectionMenu;
import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.annotation.Param;
import io.github.nosequel.command.annotation.Subcommand;
import io.github.nosequel.command.bukkit.executor.BukkitCommandExecutor;

public class CategoryCommand  {

    private final Main main;

    public CategoryCommand(Main main) {
        this.main = main;
    }

    @Command(label = "categoryedit", permission = "mighty.category.edit", aliases = "managecategories", userOnly = true)
    public void execute(BukkitCommandExecutor executor){
        new CategorySelectionMenu(executor.getPlayer(), main);
    }

    @Command(label = "category", permission = "mighty.category.edit", aliases = "categories", userOnly = true)
    public void execute2(BukkitCommandExecutor executor){
        executor.sendMessage("Nothing");
    }

    @Subcommand(parentLabel = "category", label = "edit", permission = "mighty.category.edit", userOnly = true)
    public void subExecute2(BukkitCommandExecutor executor, @Param(name = "category-name") String categoryName){
        executor.sendMessage("Nothing");
    }
}
