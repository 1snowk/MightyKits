package dev.ses.kits.kit;

import com.google.common.collect.Lists;
import dev.ses.kits.utils.CooldownUtil;
import dev.ses.kits.utils.StringUtil;
import dev.ses.kits.utils.Utils;
import dev.ses.kits.utils.item.ItemBuilder;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
public class Kit {

    private String name, displayName, material, category;
    private int iconSlot, itemData;
    private long cooldown;
    private ItemStack[] contents;
    private ItemStack[] armor;
    private List<String> iconLore;
    private boolean glow, kitMapMode;

    public Kit(){
        this.name = "";
        this.displayName = "";
        this.material = "";
        this.category = "";
        this.iconSlot = 0;
        this.itemData = 0;
        this.glow = true;
        this.kitMapMode = false;
        this.iconLore = Lists.newArrayList("", "&cClick here to receive this kit", "{cooldown}", "");
        this.cooldown = StringUtil.formatLong("1m");
    }

    public ItemStack getIcon(){
        if (glow){
            return new ItemBuilder(Material.getMaterial(this.material)).setDurability(this.itemData).setName(this.displayName).addFakeGlow().build();
        }
        return new ItemBuilder(Material.getMaterial(this.material)).setDurability(this.itemData).setName(this.displayName).build();
    }

    public String getPermission(){
        return "mighty.kit."+this.name;
    }

    public void equip(Player player){
        give(player, player);
        CooldownUtil.addCooldown(player, this.name, this.cooldown);
    }


    public void giveToOtherPlayer(Player owner, Player target){
        CooldownUtil.addCooldown(owner, this.name, this.cooldown);
        give(owner, target);
    }

    private void give(Player owner, Player target) {
        if (isKitMapMode()){
            target.getInventory().setContents(this.contents);
            target.getInventory().setArmorContents(this.armor);
            return;
        }

        for (ItemStack contents : this.contents){
            if (contents == null) continue;
            Utils.addItemOrDrop(target, contents);
        }

        for (int i = 0; i<4; i++){
            ItemStack armor = this.armor[i];
            if (target.getInventory().getItem(target.getInventory().getSize() + i) != null){
                Utils.addItemOrDrop(target, armor);
                continue;
            }
            owner.getInventory().setItem(owner.getInventory().getSize() + i, armor);
        }
    }

}
