package dev.ses.kits.command;

import dev.ses.kits.Main;
import dev.ses.kits.manager.kit.menu.KitSelectionMenu;
import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.bukkit.executor.BukkitCommandExecutor;


public class ManageKitCommand{

    private final Main main;

    public ManageKitCommand(Main main) {
        this.main = main;
    }

    @Command(label = "managekit", aliases = "kitedit", permission = "mighty.kit.edit")
    public void managekit(BukkitCommandExecutor executor){
        new KitSelectionMenu(executor.getPlayer(), main).updateMenu();
    }

}
