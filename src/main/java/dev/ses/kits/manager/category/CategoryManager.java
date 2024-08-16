package dev.ses.kits.manager.category;


import com.google.common.collect.Lists;
import dev.ses.kits.Main;
import dev.ses.kits.utils.Color;
import dev.ses.kits.utils.ConfigCreator;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class CategoryManager {

    @Getter
    private final List<Category> categoryList;
    private final ConfigCreator categoryFile;
    private Main main;

    public CategoryManager(Main main){
        this.categoryList = new ArrayList<>();
        this.categoryFile = main.getCategoryFile();
        this.main = main;
    }


    public Category getByName(String name){
        return this.categoryList.stream().filter(category -> category.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void loadOrRefreshCategories(){

        if (categoryFile.getSection("CATEGORIES").getKeys(false).isEmpty()){
            Bukkit.getConsoleSender().sendMessage(Color.translate("&cNo categories available"));
            return;
        }
        if (!categoryList.isEmpty()) categoryList.clear();
        for (String categoryName : categoryFile.getSection("CATEGORIES").getKeys(false)){
            ConfigurationSection categorySection = categoryFile.getSection("CATEGORIES."+categoryName);

            String displayName = categorySection.getString("DISPLAY-NAME");
            String title = categorySection.getString("MENU.TITLE");
            String textureValue = categorySection.getString("CUSTOM-HEAD.VALUE");
            String material = categorySection.getString("ICON.MATERIAL");
            int materialData = categorySection.getInt("ICON.DATA"),
            iconSlot = categorySection.getInt("ICON.SLOT"),
            rows = categorySection.getInt("MENU.ROWS");
            boolean glow = categorySection.getBoolean("ICON.GLOW"),
            customHead = categorySection.getBoolean("CUSTOM-HEAD.STATUS");
            List<String> iconLore = categorySection.getStringList("ICON.LORE");
            createCategory(categoryName, title, textureValue, displayName, material, materialData, iconSlot, rows, customHead, glow, iconLore);
        }
        main.log("&cCategories loaded: " + this.categoryList.size());
    }

    public void createDefaultCategory(){
        if (!this.categoryList.isEmpty()) return;
        createCategory("DEFAULT");
    }

    public void createCategory(String name, String title, String textureValue, String displayName, String material, int materialData, int iconSlot, int rows, boolean customHead, boolean glow, List<String> iconLore){
        Category newCategory = new Category();
        newCategory.setName(name);
        newCategory.setDisplayName(displayName);
        newCategory.setTitle(title);
        newCategory.setTextureValue(textureValue);
        newCategory.setMaterial(material);
        newCategory.setMaterialData(materialData);
        newCategory.setIconSlot(iconSlot);
        newCategory.setRows(rows);
        newCategory.setGlow(glow);
        newCategory.setCustomHead(customHead);
        newCategory.setIconLore(iconLore);
        if (categoryList.contains(newCategory)) return;
        categoryList.add(newCategory);
    }

    public void createCategory(String name){
        Category newCategory = new Category();
        newCategory.setName(name);
        newCategory.setDisplayName(name);
        newCategory.setTitle(name + " &cCategory");
        newCategory.setTextureValue("");
        newCategory.setMaterial(Material.STORAGE_MINECART.name());
        newCategory.setMaterialData(0);
        newCategory.setIconSlot(0);
        newCategory.setRows(3);
        newCategory.setGlow(true);
        newCategory.setCustomHead(false);
        newCategory.setIconLore(Lists.newArrayList("", "&9Click here to open this category."));
        if (categoryList.contains(newCategory)) return;
        categoryList.add(newCategory);
    }

    public void saveCategory(Category category){
        if (categoryFile.getSection("CATEGORIES") == null) categoryFile.createSection("CATEGORIES");
        if (categoryFile.getSection("CATEGORIES."+category.getName()) == null) categoryFile.createSection("CATEGORIES."+category.getName());

        categoryFile.set("CATEGORIES."+category.getName()+".DISPLAY-NAME", category.getDisplayName());
        categoryFile.set("CATEGORIES."+category.getName()+".MENU.TITLE", category.getTitle());
        categoryFile.set("CATEGORIES."+category.getName()+".MENU.ROWS", category.getRows());
        categoryFile.set("CATEGORIES."+category.getName()+".CUSTOM-HEAD.VALUE", category.getTextureValue());
        categoryFile.set("CATEGORIES."+category.getName()+".CUSTOM-HEAD.STATUS", category.isCustomHead());
        categoryFile.set("CATEGORIES."+category.getName()+".ICON.MATERIAL", category.getMaterial());
        categoryFile.set("CATEGORIES."+category.getName()+".ICON.DATA", category.getMaterialData());
        categoryFile.set("CATEGORIES."+category.getName()+".ICON.SLOT", category.getIconSlot());
        categoryFile.set("CATEGORIES."+category.getName()+".ICON.LORE", category.getIconLore());
        categoryFile.set("CATEGORIES."+category.getName()+".ICON.GLOW", category.isGlow());
        categoryFile.save();
    }

    public void saveAll(){
        for (Category category : categoryList){
            saveCategory(category);
        }
    }

    public void removeCategory(Category category){
        if (categoryFile.getSection("CATEGORIES."+category.getName()) == null) return;
        categoryFile.set("CATEGORIES."+category.getName()+".DISPLAY-NAME", null);
        categoryFile.set("CATEGORIES."+category.getName()+".MENU.TITLE", null);
        categoryFile.set("CATEGORIES."+category.getName()+".MENU.ROWS", category.getRows());
        categoryFile.set("CATEGORIES."+category.getName()+".CUSTOM-HEAD.VALUE", null);
        categoryFile.set("CATEGORIES."+category.getName()+".CUSTOM-HEAD.STATUS",null);
        categoryFile.set("CATEGORIES."+category.getName()+".ICON.MATERIAL", null);
        categoryFile.set("CATEGORIES."+category.getName()+".ICON.DATA", null);
        categoryFile.set("CATEGORIES."+category.getName()+".ICON.SLOT", null);
        categoryFile.set("CATEGORIES."+category.getName()+".ICON.LORE", null);
        categoryFile.set("CATEGORIES."+category.getName()+".ICON.GLOW", null);
        categoryFile.set("CATEGORIES."+category.getName(), null);
        categoryFile.save();
    }


}
