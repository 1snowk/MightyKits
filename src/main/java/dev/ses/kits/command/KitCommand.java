package dev.ses.kits.command;


import dev.ses.kits.Main;
import dev.ses.kits.manager.kit.menu.KitMenu;
import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.bukkit.executor.BukkitCommandExecutor;


public class KitCommand {

    private final Main main;

    public KitCommand(Main main){
        this.main = main;
    }

    @Command(label = "kit", aliases = {"gkit", "gkits", "kits"})
    public void execute(BukkitCommandExecutor executor){
        new KitMenu(executor.getPlayer(), main).updateMenu();
    }

}
