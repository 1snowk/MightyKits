package dev.ses.kits;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import dev.ses.kits.command.KitCommand;
import dev.ses.kits.command.ManageKitCommand;
import dev.ses.kits.command.ReloadCommand;
import dev.ses.kits.handler.KitHandler;
import dev.ses.kits.listener.KitListener;
import dev.ses.kits.kit.KitManager;
import dev.ses.kits.utils.Color;
import dev.ses.kits.utils.ConfigCreator;
import dev.ses.kits.utils.command.CommandManager;
import dev.ses.kits.utils.item.ItemUtils;
import dev.ses.kits.utils.menu.MenuHandler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    private ConfigCreator configFile, kitsFile, langFile;
    private KitManager kitManager;
    private CommandManager commandManager;
    private KitHandler kitHandler;

    @Override
    public void onEnable() {
        this.kitHandler = new KitHandler();
        new MenuHandler(this);
        this.configFile = new ConfigCreator("config.yml", this);
        this.kitsFile = new ConfigCreator("kits.yml", this);
        this.langFile = new ConfigCreator("lang.yml", this);
        this.kitManager = new KitManager(this);
        this.commandManager = new CommandManager(this);

        this.kitManager.loadOrRefreshKits();
        this.kitManager.createDefaultKit();

        Bukkit.getPluginManager().registerEvents(new KitListener(this), this);

        Lists.newArrayList(new KitCommand(this), new ReloadCommand(this), new ManageKitCommand(this))
                .forEach(command -> this.commandManager.registerCommands(command));

        ItemUtils.registerFakeEnchantmentGlow();


        log("&7&n"+Strings.repeat("-", 40));
        log("");
        log("&9&lMighty &b&lKits");
        log("");
        log("&c&l* &9Version: &b"+this.getDescription().getVersion());
        log("&c&l* &9Author: &bsnowk");
        log("&c&l* &9Kits: &b" + this.kitManager.getKitList().size());
        log("");
        log("&7&n"+Strings.repeat("-", 40));
    }

    @Override
    public void onDisable() {
        this.kitManager.saveAllKits();
    }

    public void log(String text){
        Bukkit.getConsoleSender().sendMessage(Color.translate(text));
    }
}
