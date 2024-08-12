package dev.ses.kits.manager.category;

import dev.ses.kits.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private String name, displayName, material;
    private List<String> lore;
    private int slot, iconData;

    public Category(String name, String displayName, String material, List<String> lore, int slot, int iconData) {
        this.name = name;
        this.displayName = displayName;
        this.material = material;
        this.lore = lore;
        this.slot = slot;
        this.iconData = iconData;
    }

    public Category(){
        this.name = "";
        this.displayName = "";
        this.material = "";
        this.lore = new ArrayList<>();
        this.slot = 0;
        this.iconData = 0;
    }

    public ItemStack getIcon(){
        return new ItemBuilder(Material.getMaterial(this.material), 1, iconData).setName(this.displayName).setLore(this.lore).build();
    }
}
