package dev.ses.kits.command;

import com.google.common.base.Strings;
import dev.ses.kits.Main;
import dev.ses.kits.utils.Color;
import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.annotation.Param;
import io.github.nosequel.command.annotation.Subcommand;
import io.github.nosequel.command.bukkit.executor.BukkitCommandExecutor;


public class ReloadCommand{

    private Main main;

    public ReloadCommand(Main main){
        this.main = main;
    }

    @Command(label = "mighty", permission = "mighty")
    public void mighty(BukkitCommandExecutor executor){
        executor.sendMessage("&7&n"+ Strings.repeat("-", 40));
        executor.sendMessage("&9&lMighty &b&lKits");
        executor.sendMessage("");
        executor.sendMessage("&b* &fAuthor: &9snowk");
        executor.sendMessage("&b* &fVersion: &9"+main.getDescription().getVersion());
        executor.sendMessage("");
        executor.sendMessage("&7&n"+ Strings.repeat("-", 40));
    }

    @Subcommand(parentLabel = "mighty", label = "reload", permission = "mighty.reload")
    public void reload(BukkitCommandExecutor executor){
        main.getConfigFile().reload();
        main.getKitsFile().reload();
        main.getLangFile().reload();
        main.getCategoryFile().reload();
        main.getKitManager().saveAllKits();
        main.getKitManager().loadOrRefreshKits();
        main.getCategoryManager().loadOrRefreshCategories();
        executor.sendMessage("&aPlugin has been reloaded.");
    }

}
