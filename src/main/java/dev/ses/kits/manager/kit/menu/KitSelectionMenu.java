package dev.ses.kits.manager.kit.menu;


import dev.ses.kits.Main;
import dev.ses.kits.manager.kit.Kit;
import dev.ses.kits.utils.Color;
import dev.ses.kits.utils.item.ItemBuilder;
import dev.ses.kits.utils.menu.buttons.Button;
import dev.ses.kits.utils.menu.pagination.NavigationPosition;
import dev.ses.kits.utils.menu.pagination.PaginatedMenu;
import dev.ses.kits.utils.sound.CompatibleSound;
import org.bukkit.Material;
import org.bukkit.entity.Player;


public class KitSelectionMenu extends PaginatedMenu {

    private final Main main;

    public KitSelectionMenu(Player player, Main main) {
        super(player, "&cSelect your option", 54, 2);
        this.main = main;
    }

    @Override
    public void tick() {

        Button next = new Button(Material.ARROW).setDisplayName("&aNext Page");
        Button previous = new Button(Material.FEATHER).setDisplayName("&cPrevious Page");

        setNavigationPosition(NavigationPosition.BOTTOM);
        this.setNextPageButton(next);
        this.setPreviousPageButton(previous);

        this.buttons[0] = new Button(Material.INK_SACK).setData((byte) 10).setDisplayName("&aCreate Kit").setLore(new String[]{"&aClick here to create a new kit."}).setClickAction(event -> {
           main.getKitHandler().getVariantMap().put(getPlayer().getUniqueId(), "create");
           getPlayer().sendMessage(Color.translate("&aWrite in the chat the name of the new kit"));
           getPlayer().closeInventory();
        });

        this.buttons[1] = new Button(Material.STAINED_GLASS_PANE).setData((byte)7).setDisplayName(" ").setClickAction(event -> {
            event.setCancelled(true);
        });

        this.buttons[9] = new Button(Material.STAINED_GLASS_PANE).setData((byte)7).setDisplayName(" ").setClickAction(event -> {
            event.setCancelled(true);
        });

        int index = 2;

        for (Kit kits : main.getKitManager().getKitList()){
            if (index == 9) continue;
            this.buttons[index] = new Button(new ItemBuilder(Material.getMaterial(kits.getMaterial()))
                    .setDurability(kits.getItemData()).build())
                    .setDisplayName(kits.getDisplayName())
                    .setLore(new String[]{
                    "&9Click here to edit this kit."
            }).setClickAction(event -> {
                new KitsEditMenu(getPlayer(), kits, main).updateMenu();
                        CompatibleSound.NOTE_PLING.play(getPlayer());
                    });
            index++;
        }

    }
}
