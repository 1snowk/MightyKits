package dev.ses.kits.kit.menu;

import dev.ses.kits.Main;
import dev.ses.kits.kit.Kit;
import dev.ses.kits.utils.Color;
import dev.ses.kits.utils.CooldownUtil;
import dev.ses.kits.utils.Utils;
import dev.ses.kits.utils.item.ItemBuilder;
import dev.ses.kits.utils.menu.Menu;
import dev.ses.kits.utils.menu.buttons.Button;
import dev.ses.kits.utils.sound.CompatibleSound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitPreviewMenu extends Menu {

    private final Kit kit;
    private final Main main;

    public KitPreviewMenu(Player player, Kit kit, Main main) {
        super(player, kit.getDisplayName() + " Preview", 6*9);
        this.kit = kit;
        this.main = main;
    }

    @Override
    public void tick() {
        int index = 0;
        for (ItemStack contents : kit.getContents()){
            if (contents == null) continue;
            this.buttons[index] = new Button(contents.clone()).setClickAction(event -> event.setCancelled(true));

            index++;
        }

        Button panels = new Button(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 7).setName("").build()).setClickAction(event -> event.setCancelled(true));

        for (int i = 36; i < 45; i++){
            this.buttons[i] = panels;
        }

        this.buttons[49] = panels;
        this.buttons[50] = panels;

        this.buttons[51] = new Button(new ItemBuilder(Material.FEATHER).build()).setDisplayName(Color.translate("&cBack")).setClickAction(event -> {
            new KitMenu(getPlayer(), main).updateMenu();
            event.setCancelled(true);
        });

        this.buttons[52] = new Button(new ItemBuilder(Material.SKULL_ITEM, 1, 3).setSkullOwner(getPlayer().getName()).build()).setDisplayName(Color.translate("&aGive To Other Player")).setClickAction(event -> {
            if (CooldownUtil.isInCooldown(getPlayer(), kit.getName())){
                Utils.sendMessage(getPlayer(), "&cYou are in cooldown for: " + CooldownUtil.getCooldown(getPlayer(), kit.getName()));
                return;
            }
            main.getKitHandler().getVariantMap().put(getPlayer().getUniqueId(), "give");
            main.getKitHandler().getKitMap().put(getPlayer().getUniqueId(), kit);
            getPlayer().closeInventory();
            event.setCancelled(true);
        });

        this.buttons[53] = new Button(new ItemBuilder(Material.INK_SACK, 1, 10).build()).setDisplayName(Color.translate("&aClick here to equip this kit &7(Only op players)")).setClickAction(event -> {
            if (!getPlayer().isOp()){
                Utils.noPerms(getPlayer());
                CompatibleSound.IRONGOLEM_HIT.play(getPlayer());
                return;
            }

            if (event.getClick().isLeftClick()){
                if (CooldownUtil.isInCooldown(getPlayer(), kit.getName())){
                    Utils.sendMessage(getPlayer(), "&cYou are in cooldown for: " + CooldownUtil.getCooldown(getPlayer(), kit.getName()));
                    return;
                }

                kit.equip(getPlayer());
                getPlayer().closeInventory();
                return;
            }
        });

        int armorIndex = 48;

        for (ItemStack armorPart : kit.getArmor()){
            if (armorPart != null) this.buttons[armorIndex] = new Button(armorPart.clone()).setClickAction(event -> event.setCancelled(true));
            armorIndex--;
            if (armorIndex < 45) break;
        }
    }
}
