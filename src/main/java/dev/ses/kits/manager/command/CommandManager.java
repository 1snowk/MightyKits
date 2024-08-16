package dev.ses.kits.manager.command;

import dev.ses.kits.Main;
import dev.ses.kits.command.CategoryCommand;
import dev.ses.kits.command.KitCommand;
import dev.ses.kits.command.ManageKitCommand;
import dev.ses.kits.command.ReloadCommand;
import io.github.nosequel.command.CommandHandler;
import io.github.nosequel.command.bukkit.BukkitCommandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandManager {

    private final CommandHandler commandHandler;
    private final List<Object> commandsList;
    private final Main main;

    public CommandManager(Main main){
        this.main = main;
        this.commandHandler = new BukkitCommandHandler("commands");
        this.commandHandler.registerTypeAdapter(UUID.class, new UUIDTypeAdapter());
        this.commandsList = new ArrayList<>();
        this.loadCommands();
    }

    public void loadCommands(){
        commandsList.add(new KitCommand(this.main));
        commandsList.add(new ManageKitCommand(this.main));
        commandsList.add(new ReloadCommand(this.main));
        commandsList.add(new CategoryCommand(this.main));
        commandsList.forEach(this.commandHandler::registerCommand);
    }

}
