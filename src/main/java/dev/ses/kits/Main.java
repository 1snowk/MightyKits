package dev.ses.kits;

import com.google.common.base.Strings;
import dev.ses.kits.handler.CategoryHandler;
import dev.ses.kits.handler.KitHandler;
import dev.ses.kits.manager.kit.listener.KitListener;
import dev.ses.kits.manager.category.CategoryManager;
import dev.ses.kits.manager.command.CommandManager;
import dev.ses.kits.manager.kit.KitManager;
import dev.ses.kits.utils.Color;
import dev.ses.kits.utils.ConfigCreator;

import dev.ses.kits.utils.item.ItemUtils;
import dev.ses.kits.utils.menu.MenuHandler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    private ConfigCreator configFile, kitsFile, langFile, categoryFile;
    private KitManager kitManager;
    private CategoryManager categoryManager;
    private CommandManager commandManager;
    private KitHandler kitHandler;
    private CategoryHandler categoryHandler;
    private MenuHandler menuHandler;

    @Override
    public void onEnable() {
        this.kitHandler = new KitHandler();
        this.menuHandler = new MenuHandler(this);
        this.categoryHandler = new CategoryHandler();
        this.configFile = new ConfigCreator("config.yml", this);
        this.kitsFile = new ConfigCreator("kits.yml", this);
        this.langFile = new ConfigCreator("lang.yml", this);
        this.categoryFile = new ConfigCreator("categories.yml", this);

        this.categoryManager = new CategoryManager(this);
        this.kitManager = new KitManager(this);
        this.commandManager = new CommandManager(this);


        this.categoryManager.loadOrRefreshCategories();
        this.kitManager.loadOrRefreshKits();
        this.kitManager.createDefaultKit();
        this.categoryManager.createDefaultCategory();

        Bukkit.getPluginManager().registerEvents(new KitListener(this), this);
        ItemUtils.registerFakeEnchantmentGlow();

        log("&7&n"+Strings.repeat("-", 40));
        log("");
        log("&9&lMighty &b&lKits");
        log("");
        log("&c&l* &9Version: &b"+this.getDescription().getVersion());
        log("&c&l* &9Author: &bsnowk");
        log("&c&l* &9Kits: &b" + this.kitManager.getKitList().size());
        log("&c&l* &9Categories: &b" + this.categoryManager.getCategoryList().size());
        log("");
        log("&7&n"+Strings.repeat("-", 40));
    }

    @Override
    public void onDisable() {
        this.kitManager.saveAll();
        this.categoryManager.saveAll();
    }

    public void log(String text){
        Bukkit.getConsoleSender().sendMessage(Color.translate(text));
    }
}
