package dev.ses.kits.command;

import dev.ses.kits.Main;
import dev.ses.kits.kit.menu.KitMenu;
import dev.ses.kits.utils.command.BaseCommand;
import dev.ses.kits.utils.command.Command;
import dev.ses.kits.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class KitCommand implements BaseCommand {

    private final Main main;

    public KitCommand(Main main){
        this.main = main;
    }

    @Override
    @Command(name = "kit", aliases = {"gkit", "gkits", "kits"}, inGameOnly = true)
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        new KitMenu(player, main).updateMenu();
    }
}
