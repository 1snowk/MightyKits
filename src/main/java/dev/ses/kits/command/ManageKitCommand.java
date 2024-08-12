package dev.ses.kits.command;

import dev.ses.kits.Main;
import dev.ses.kits.manager.kit.Kit;
import dev.ses.kits.manager.kit.menu.KitSelectionMenu;
import dev.ses.kits.manager.kit.menu.KitsEditMenu;
import dev.ses.kits.utils.Utils;
import dev.ses.kits.utils.command.BaseCommand;
import dev.ses.kits.utils.command.Command;
import dev.ses.kits.utils.command.CommandArgs;
import dev.ses.kits.utils.sound.CompatibleSound;
import org.bukkit.entity.Player;

public class ManageKitCommand implements BaseCommand {

    private final Main main;

    public ManageKitCommand(Main main) {
        this.main = main;
    }

    @Override
    @Command (name = "managekit", aliases = "kitedit", permission = "mighty.kit.edit")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0){
            new KitSelectionMenu(player, main).updateMenu();
            return;
        }

        Kit kit = main.getKitManager().getKitByName(args[0].toUpperCase());

        if (kit == null){
            Utils.sendMessage(player, "&cThis kit does not exist.");
            CompatibleSound.VILLAGER_NO.play(player);
            return;
        }

        new KitsEditMenu(player, kit, main).updateMenu();

    }


}
