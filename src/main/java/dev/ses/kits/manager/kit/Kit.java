package dev.ses.kits.manager.kit;

import com.google.common.collect.Lists;
import dev.ses.kits.Main;
import dev.ses.kits.manager.kit.menu.KitPreviewMenu;
import dev.ses.kits.utils.CooldownUtil;
import dev.ses.kits.utils.StringUtil;
import dev.ses.kits.utils.Utils;
import dev.ses.kits.utils.item.ItemBuilder;
import dev.ses.kits.utils.menu.Menu;
import dev.ses.kits.utils.sound.CompatibleSound;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Data
public class Kit {

    private Main main;
    private String name, displayName, material, category;
    private int iconSlot, itemData;
    private long cooldown;
    private ItemStack[] contents;
    private ItemStack[] armor;
    private List<String> iconLore;
    private boolean glow, kitMapMode;

    public Kit(Main main){
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
        this.main = main;
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

    public String[] getNewLore(Player player) {
        List<String> newlore = new ArrayList<>(this.iconLore);

        for (int i = 0; i < newlore.size(); i++){
            String lines = newlore.get(i);
            newlore.set(i, lines.replace("{cooldown}", (CooldownUtil.isInCooldown(player, this.getName()) ? "&cYou are in cooldown for " + CooldownUtil.getCooldown(player, this.getName()): main.getLangFile().getString("KIT-LANG.NO-COOLDOWN"))));
        }

        return newlore.toArray(new String[0]);
    }

    public void giveToOtherPlayer(Player owner, Player target){
        CooldownUtil.addCooldown(owner, this.name, this.cooldown);
        give(owner, target);
    }

    public void execute(Player player, InventoryClickEvent event, Menu menu){
        event.setCancelled(true);

        if (event.getClick().isRightClick()){
            new KitPreviewMenu(player, this, main).updateMenu();
            return;
        }

        if (!player.hasPermission(getPermission())){
            Utils.sendMessage(player, main.getLangFile().getString("KIT-LANG.NO-PERMISSION").replace("{kit-name}", getDisplayName()));
            CompatibleSound.IRONGOLEM_HIT.play(player);
            return;
        }

        if (event.getClick().isLeftClick()){
            if (CooldownUtil.isInCooldown(player, getName())){
                Utils.sendMessage(player, "&cYou are in cooldown for: " + CooldownUtil.getCooldown(player, getName()));
                return;
            }
            equip(player);
            menu.updateMenu();
            return;
        }
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
