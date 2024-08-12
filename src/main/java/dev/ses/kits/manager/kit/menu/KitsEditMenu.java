package dev.ses.kits.manager.kit.menu;

import dev.ses.kits.Main;
import dev.ses.kits.manager.kit.Kit;
import dev.ses.kits.utils.InventoryUtils;
import dev.ses.kits.utils.StringUtil;
import dev.ses.kits.utils.Utils;
import dev.ses.kits.utils.item.ItemBuilder;
import dev.ses.kits.utils.menu.Menu;
import dev.ses.kits.utils.menu.buttons.Button;
import dev.ses.kits.utils.sound.CompatibleSound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class KitsEditMenu extends Menu implements Listener {

    private final Kit kit;
    private final Main main;

    public KitsEditMenu(Player player, Kit kit, Main main) {
        super(player, "&cEditing: &f"+kit.getName(), 54);
        this.kit = kit;
        this.main = main;
    }

    @Override
    public void tick() {
        this.buttons[13] = new Button(new ItemBuilder(Material.NAME_TAG).build()).setDisplayName("&9Change DisplayName").setLore(new String[]{"", "&cClick here to change kit displayName", "&cValue: &f"+kit.getDisplayName(), ""}).setClickAction(event -> {
            edit("displayName");
            Utils.sendMessage(getPlayer(), "&eType in the chat the new display name of the kit.");
            Utils.sendMessage(getPlayer(), "&eOr you can type &c&lCANCEL &eto stop the procedure.");
            event.setCancelled(true);
        });

        this.buttons[20] = new Button(new ItemBuilder(Material.ENCHANTMENT_TABLE).addFakeGlow().build()).setDisplayName("&9Change Glow Status").setLore(new String[]{"", "&cClick here to change kit icon item.", "&cValue: &f"+kit.isGlow()}).setClickAction(event -> {
            kit.setGlow(!kit.isGlow());
            CompatibleSound.NOTE_PLING.play(getPlayer());
            event.setCancelled(true);
            this.updateMenu();
        });

        this.buttons[22] = new Button(new ItemBuilder(Material.DIAMOND_SWORD).build()).setDisplayName("&9Change Item").setLore(new String[]{"", "&cClick here to change kit icon item.", "&cValue: &f"+kit.getMaterial() + ":"+kit.getItemData(), ""}).setClickAction(event -> {
            edit("icon");
            Utils.sendMessage(getPlayer(), "&eHolding an item in your hand, right click to set it to the kit icon.");
            Utils.sendMessage(getPlayer(), "&eOr you can type &c&lCANCEL &eto stop the procedure.");
            getPlayer().closeInventory();
            event.setCancelled(true);
        });

        this.buttons[24] = new Button(new ItemBuilder(Material.WATCH).build()).setDisplayName("&9Change Cooldown").setLore(new String[]{"", "&cClick here to change kit cooldown.", "&cValue: &f"+ StringUtil.format(kit.getCooldown()), ""}).setClickAction(event -> {
            edit("cooldown");
            Utils.sendMessage(getPlayer(), "&eWrite in the chat the new cooldown value for the kit.");
            Utils.sendMessage(getPlayer(), "&eOr you can type &c&lCANCEL &eto stop the procedure.");
            CompatibleSound.NOTE_PLING.play(getPlayer());
            event.setCancelled(true);
        });

        this.buttons[29] = new Button(Material.CHEST).setDisplayName("&9Change Loot").setLore(new String[]{"", "&cClick here to set kit loot.", ""}).setClickAction(event -> {
            kit.setContents(InventoryUtils.getRealItems(getPlayer().getInventory().getContents()));
            kit.setArmor(InventoryUtils.getRealItems(getPlayer().getInventory().getArmorContents()));
            CompatibleSound.NOTE_PLING.play(getPlayer());
            event.setCancelled(true);
        });

        this.buttons[31] = new Button(Material.BOW).setDisplayName("&9KitMap Mode").setLore(new String[]{"", "&cClick here to set kitMap mode status.", "&cValue: &f"+kit.isKitMapMode(), ""}).setClickAction(event -> {
            kit.setKitMapMode(!kit.isKitMapMode());
            CompatibleSound.NOTE_PLING.play(getPlayer());
            event.setCancelled(true);
            this.updateMenu();
        });

        this.buttons[33] = new Button(Material.ANVIL).setDisplayName("&9Kit Slot").setLore(new String[]{"", "&cClick here to set kitMap mode status.", "&cValue: &f"+kit.getIconSlot(), ""}).setClickAction(event -> {
            edit("slot");
            Utils.sendMessage(getPlayer(), "&eType in the chat the new slot for the kit.");
            Utils.sendMessage(getPlayer(), "&eOr you can type &c&lCANCEL &eto stop the procedure.");
            CompatibleSound.NOTE_PLING.play(getPlayer());
            event.setCancelled(true);
            this.updateMenu();
        });

        this.buttons[45] = new Button(Material.FEATHER).setDisplayName("&cBack").setLore(new String[]{"&cClick here to return."}).setClickAction(event -> {
            new KitSelectionMenu(getPlayer(), main).updateMenu();
            CompatibleSound.NOTE_PLING.play(getPlayer());
            event.setCancelled(true);
        });

        this.buttons[49] = new Button(Material.INK_SACK).setData((byte) 1).setDisplayName("&9Remove Kit").setLore(new String[]{"&cClick here to remove this kit."}).setClickAction(event -> {
            event.setCancelled(true);
            getPlayer().closeInventory();
            if (main.getKitManager().getKitList().size() == 1){
                Utils.sendMessage(getPlayer(), "&cYou cannot delete this kit, there must be at least 1 kit created.");
                CompatibleSound.IRONGOLEM_HIT.play(getPlayer());
                return;
            }
            main.getKitManager().removeKit(kit);
            CompatibleSound.ANVIL_USE.play(getPlayer());
        });

    }

    public void edit(String variant){
        main.getKitHandler().getVariantMap().put(getPlayer().getUniqueId(), variant);
        main.getKitHandler().getKitMap().put(getPlayer().getUniqueId(), kit);
        getPlayer().closeInventory();
    }


}
