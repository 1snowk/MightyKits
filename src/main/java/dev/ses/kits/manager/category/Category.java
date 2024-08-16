package dev.ses.kits.manager.category;

import dev.ses.kits.utils.Color;
import dev.ses.kits.utils.item.ItemBuilder;
import dev.ses.kits.utils.item.ItemUtils;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
public class Category {

    private String name, title, textureValue, displayName;
    private String material;
    private int materialData;
    private int iconSlot, rows;
    private boolean glow, customHead;
    private List<String> iconLore;

    public ItemStack getIcon(){
        ItemStack icon = null;

        if (isGlow() && !isCustomHead()){
            icon = new ItemBuilder(Material.getMaterial(this.material)).setLore(Color.translate(this.iconLore)).addFakeGlow().setDurability(this.materialData).build();
        }

        if (isGlow() && isCustomHead()){
            icon = new ItemBuilder(Material.SKULL_ITEM).setLore(Color.translate(this.iconLore)).addFakeGlow().setDurability(3).build();
            ItemUtils.setHeadTexture(icon, this.textureValue);
        }

        if (isCustomHead() && !isGlow()){
            icon = new ItemBuilder(Material.SKULL_ITEM).setLore(Color.translate(this.iconLore)).setDurability(3).build();
            ItemUtils.setHeadTexture(icon, this.textureValue);
        }

        if (!isCustomHead() && !isGlow()){
            icon = new ItemBuilder(Material.getMaterial(this.material)).setLore(Color.translate(this.iconLore)).setDurability(this.materialData).build();
        }
        return icon;
    }
}
