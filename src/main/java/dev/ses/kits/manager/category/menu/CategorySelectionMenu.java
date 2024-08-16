package dev.ses.kits.manager.category.menu;


import dev.ses.kits.Main;
import dev.ses.kits.manager.category.Category;
import dev.ses.kits.manager.kit.Kit;
import dev.ses.kits.manager.kit.menu.KitsEditMenu;
import dev.ses.kits.utils.Color;
import dev.ses.kits.utils.item.ItemBuilder;
import dev.ses.kits.utils.item.ItemUtils;
import dev.ses.kits.utils.menu.buttons.Button;
import dev.ses.kits.utils.menu.pagination.NavigationPosition;
import dev.ses.kits.utils.menu.pagination.PaginatedMenu;
import dev.ses.kits.utils.sound.CompatibleSound;
import org.bukkit.Material;
import org.bukkit.entity.Player;


public class CategorySelectionMenu extends PaginatedMenu {

    private final Main main;

    public CategorySelectionMenu(Player player, Main main) {
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

        this.buttons[0] = new Button(Material.INK_SACK).setData((byte) 10).setDisplayName("&aCreate Category").setLore(new String[]{"&aClick here to create a new category."}).setClickAction(event -> {
           main.getKitHandler().getVariantMap().put(getPlayer().getUniqueId(), "create");
           getPlayer().sendMessage(Color.translate("&aWrite in the chat the name of the new category."));
           getPlayer().closeInventory();
        });

        this.buttons[1] = ItemUtils.getPane();

        this.buttons[9] = ItemUtils.getPane();

        int index = 2;

        for (Category categories: main.getCategoryManager().getCategoryList()){
            if (index == 9) continue;
            this.buttons[index] = new Button(new ItemBuilder(Material.getMaterial(categories.getMaterial()))
                    .setDurability(categories.getMaterialData()).build())
                    .setDisplayName(categories.getDisplayName())
                    .setLore(new String[]{
                    "&9Click here to edit this kit."
            }).setClickAction(event -> {
                event.setCancelled(true);
                    });
            index++;
        }

    }
}
