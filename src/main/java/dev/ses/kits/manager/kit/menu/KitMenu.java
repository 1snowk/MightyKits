package dev.ses.kits.manager.kit.menu;



import dev.ses.kits.Main;
import dev.ses.kits.manager.kit.Kit;

import dev.ses.kits.utils.Color;
import dev.ses.kits.utils.CooldownUtil;
import dev.ses.kits.utils.Utils;
import dev.ses.kits.utils.item.ItemBuilder;
import dev.ses.kits.utils.menu.Menu;
import dev.ses.kits.utils.menu.buttons.Button;
import dev.ses.kits.utils.sound.CompatibleSound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.ArrayList;
import java.util.List;


public class KitMenu extends Menu{

    private final Main main;

    public KitMenu(Player player, Main main) {
        super(player, main.getConfigFile().getString("KITS-MENU.TITLE"), main.getConfigFile().getInt("KITS-MENU.ROWS")*9);
        this.main = main;
    }

    private String[] getNewLore(Player player, Kit kits) {
        List<String> newlore = new ArrayList<>(main.getKitsFile().getStringList("KITS."+kits.getName()+".LORE"));

        for (int i = 0; i < newlore.size(); i++){
            String lines = newlore.get(i);
            newlore.set(i, lines.replace("{cooldown}", (CooldownUtil.isInCooldown(player, kits.getName()) ? "&cYou are in cooldown for " + CooldownUtil.getCooldown(player, kits.getName()): main.getLangFile().getString("KIT-LANG.NO-COOLDOWN"))));
        }

        return newlore.toArray(new String[0]);
    }

    @Override
    public void tick() {
        for (Kit kits : main.getKitManager().getKitList()){
            this.buttons[kits.getIconSlot()] = new Button(new ItemBuilder(kits.getIcon().clone()).build()).setLore(getNewLore(getPlayer(), kits)).setClickAction(event -> {

                event.setCancelled(true);

                if (event.getClick().isRightClick()){
                    new KitPreviewMenu(getPlayer(), kits, main).updateMenu();
                    return;
                }

                if (!getPlayer().hasPermission(kits.getPermission())){
                    Utils.sendMessage(getPlayer(), main.getLangFile().getString("KIT-LANG.NO-PERMISSION").replace("{kit-name}", kits.getDisplayName()));
                    CompatibleSound.IRONGOLEM_HIT.play(getPlayer());
                    return;
                }

                if (event.getClick().isLeftClick()){
                    if (CooldownUtil.isInCooldown(getPlayer(), kits.getName())){
                        Utils.sendMessage(getPlayer(), "&cYou are in cooldown for: " + CooldownUtil.getCooldown(getPlayer(), kits.getName()));
                        return;
                    }
                    kits.equip(getPlayer());
                    this.updateMenu();
                    return;
                }


            });
        }
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//
//                updateMenu();
//
//            }
//        }.runTaskTimer(main, 0L, 20L);
    }
}
