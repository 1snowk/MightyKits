package dev.ses.kits.manager.kit;


import dev.ses.kits.Main;
import dev.ses.kits.utils.Color;
import dev.ses.kits.utils.ConfigCreator;
import dev.ses.kits.utils.InventoryUtils;
import dev.ses.kits.utils.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class KitManager {

    @Getter private final List<Kit> kitList;
    private final ConfigCreator kitsFile;

    public KitManager(Main main) {
        this.kitList = new ArrayList<>();
        this.kitsFile = main.getKitsFile();

    }

    public void createDefaultKit(){
        if (!this.kitList.isEmpty()) return;
        createKit("DEFAULT");
    }

    public Kit getKitByName(String name){
        return this.kitList.stream().filter(kit -> kit.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void loadOrRefreshKits(){
        if (kitsFile.getSection("KITS").getKeys(false).isEmpty()){
            Bukkit.getConsoleSender().sendMessage(Color.translate("&cNo kits available"));
            return;
        }

        if (!this.kitList.isEmpty()) this.kitList.clear();

        for (String kitName : kitsFile.getSection("KITS").getKeys(false)){
            ConfigurationSection kitSection = kitsFile.getSection("KITS."+kitName);
            String displayName = kitSection.getString("DISPLAY-NAME"),
            material = kitSection.getString("MATERIAL"),
            category = kitSection.getString("CATEGORY.NAME");

            int itemData = kitSection.getInt("DATA"),
            iconSlot = kitSection.getInt("CATEGORY.SLOT");

            long cooldown = kitSection.getLong("COOLDOWN");

            boolean glow = kitSection.getBoolean("GLOW"),
            kitMapMode = kitSection.getBoolean("KITMAP-MODE");
            ItemStack[] contents = InventoryUtils.getRealItems(InventoryUtils.itemStackArrayFromBase64(kitSection.getString("CONTENTS")));
            ItemStack[] armorContents;
            List<String> iconLore = kitSection.getStringList("LORE");

            if (kitSection.getString("ARMOR").isEmpty()){
                armorContents = new ItemStack[4];
            }else{
                armorContents = InventoryUtils.getRealItems(InventoryUtils.itemStackArrayFromBase64(kitSection.getString("ARMOR")));
            }

            createKit(kitName, displayName, iconLore, iconSlot, material, itemData, contents, armorContents, glow, category, cooldown, kitMapMode);
        }
        Bukkit.getConsoleSender().sendMessage(Color.translate("&cKits loaded: " + this.kitList.size()));
    }

    public void createKit(String name){
        Kit newKit = new Kit();
        newKit.setName(name);
        newKit.setDisplayName(name);
        newKit.setIconSlot(0);
        newKit.setMaterial("NETHER_STAR");
        newKit.setItemData(0);
        newKit.setContents(getDefaultLoot());
        newKit.setArmor(new ItemStack[4]);
        newKit.setGlow(true);
        newKit.setCategory("DEFAULT");
        newKit.setKitMapMode(false);
        if (this.kitList.contains(newKit)) return;
        this.kitList.add(newKit);
    }

    public void createKit(String name, String displayName, List<String> lore, int slot, String material, int itemData, ItemStack[] contents, ItemStack[] armorContents, boolean glow, String category, long cooldown, boolean kitMapMode){
        Kit newKit = new Kit();
        newKit.setName(name);
        newKit.setDisplayName(displayName);
        newKit.setIconSlot(slot);
        newKit.setMaterial(material);
        newKit.setItemData(itemData);
        newKit.setContents(InventoryUtils.getRealItems(contents));
        newKit.setGlow(glow);
        newKit.setCategory(category);
        newKit.setArmor(armorContents);
        newKit.setIconLore(lore);
        newKit.setCooldown(cooldown);
        newKit.setKitMapMode(kitMapMode);
        this.kitList.add(newKit);
    }

    public void removeKit(Kit kit){
        this.kitList.remove(kit);
        if (kitsFile.getSection("KITS."+kit.getName()) == null) return;
        kitsFile.set("KITS."+kit.getName()+".DISPLAY-NAME", null);
        kitsFile.set("KITS."+kit.getName()+".MATERIAL", null);
        kitsFile.set("KITS."+kit.getName()+".DATA", null);
        kitsFile.set("KITS."+kit.getName()+".CATEGORY.NAME", null);
        kitsFile.set("KITS."+kit.getName()+".CATEGORY.SLOT", null);
        kitsFile.set("KITS."+kit.getName()+".GLOW", kit.isGlow());
        kitsFile.set("KITS."+kit.getName()+".CONTENTS", null);
        kitsFile.set("KITS."+kit.getName()+".ARMOR", null);
        kitsFile.set("KITS."+kit.getName()+".LORE", null);
        kitsFile.set("KITS."+kit.getName()+".COOLDOWN", null);
        kitsFile.set("KITS."+kit.getName(), null);
        kitsFile.save();
    }

    public ItemStack[] getDefaultLoot(){
        return new ItemStack[]{new ItemBuilder(Material.DIAMOND_SWORD).setName("&c&lDefault Sword").build()};
    }

    public void saveAllKits(){
        if (this.kitList.isEmpty()) return;
        if (kitsFile.getSection("KITS") == null) kitsFile.createSection("KITS");
        for (Kit kit : this.kitList){
            kitsFile.createSection("KITS."+kit.getName());
            kitsFile.createSection("KITS."+kit.getName()+".DISPLAY-NAME");
            kitsFile.createSection("KITS."+kit.getName()+".MATERIAL");
            kitsFile.createSection("KITS."+kit.getName()+".DATA");
            kitsFile.createSection("KITS."+kit.getName()+".LORE");
            kitsFile.createSection("KITS."+kit.getName()+".COOLDOWN");
            kitsFile.createSection("KITS."+kit.getName()+".CATEGORY.NAME");
            kitsFile.createSection("KITS."+kit.getName()+".CATEGORY.SLOT");
            kitsFile.createSection("KITS."+kit.getName()+".GLOW");
            kitsFile.createSection("KITS."+kit.getName()+".CONTENTS");
            kitsFile.createSection("KITS."+kit.getName()+".ARMOR");
            kitsFile.createSection("KITS."+kit.getName()+".KITMAP-MODE");
            kitsFile.save();

            kitsFile.set("KITS."+kit.getName()+".DISPLAY-NAME", kit.getDisplayName());
            kitsFile.set("KITS."+kit.getName()+".MATERIAL", kit.getMaterial());
            kitsFile.set("KITS."+kit.getName()+".DATA", kit.getItemData());
            kitsFile.set("KITS."+kit.getName()+".CATEGORY.NAME", kit.getCategory());
            kitsFile.set("KITS."+kit.getName()+".CATEGORY.SLOT", kit.getIconSlot());
            kitsFile.set("KITS."+kit.getName()+".GLOW", kit.isGlow());
            kitsFile.set("KITS."+kit.getName()+".CONTENTS", InventoryUtils.itemStackArrayToBase64(InventoryUtils.getRealItems(kit.getContents())));
            kitsFile.set("KITS."+kit.getName()+".ARMOR", InventoryUtils.itemStackArrayToBase64(InventoryUtils.getRealItems(kit.getArmor())));
            kitsFile.set("KITS."+kit.getName()+".LORE", kit.getIconLore());
            kitsFile.set("KITS."+kit.getName()+".COOLDOWN", kit.getCooldown());
            kitsFile.set("KITS."+kit.getName()+".KITMAP-MODE", kit.isKitMapMode());
            kitsFile.save();
        }

        this.kitList.clear();
    }
}
