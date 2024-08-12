package dev.ses.kits.command;

import dev.ses.kits.Main;
import dev.ses.kits.utils.Color;
import dev.ses.kits.utils.command.BaseCommand;
import dev.ses.kits.utils.command.Command;
import dev.ses.kits.utils.command.CommandArgs;

public class ReloadCommand implements BaseCommand {

    private Main main;

    public ReloadCommand(Main main){
        this.main = main;
    }

    @Override
    @Command (name = "mighty", inGameOnly = false, permission = "mighty.reload")
    public void onCommand(CommandArgs command) {
        if (command.getArgs()[0].equalsIgnoreCase("reload")){
            main.getConfigFile().reload();
            main.getKitsFile().reload();
            main.getLangFile().reload();
            main.getKitManager().loadOrRefreshKits();
            command.getPlayer().sendMessage(Color.translate("&aPlugin has been reloaded."));
        }
    }
}
