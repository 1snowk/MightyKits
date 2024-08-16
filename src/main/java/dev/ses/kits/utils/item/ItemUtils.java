package dev.ses.kits.utils.item;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import dev.ses.kits.utils.menu.buttons.Button;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;


import java.lang.reflect.Field;
import java.util.UUID;

@UtilityClass
public class ItemUtils {

    public Enchantment FAKE_GLOW;

    public void decreaseItem(Player player) {
        if(player.getItemInHand().getAmount() > 1) {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
            return;
        }

        player.getInventory().setItemInHand(new ItemStack(Material.AIR));
    }

    public Button getPane(){
        return new Button(Material.STAINED_GLASS_PANE).setData((byte)7).setDisplayName(" ").setClickAction(event -> {
            event.setCancelled(true);
        });
    }

    public void setHeadTexture(ItemStack head, String value){
        SkullMeta iconMeta = (SkullMeta) head.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = profile.getProperties();
        propertyMap.put("textures", new Property("texture", value));

        try {
            Field field = iconMeta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(iconMeta, profile);
        }catch (Exception e){
            e.printStackTrace();
        }

        head.setItemMeta(iconMeta);
    }


    public static boolean isArmor(Material material) {
        switch(material) {
            case DIAMOND_HELMET:
            case GOLD_HELMET:
            case IRON_HELMET:
            case CHAINMAIL_HELMET:
            case LEATHER_HELMET:
            case DIAMOND_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case IRON_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case LEATHER_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case GOLD_LEGGINGS:
            case IRON_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case LEATHER_LEGGINGS:
            case DIAMOND_BOOTS:
            case GOLD_BOOTS:
            case IRON_BOOTS:
            case CHAINMAIL_BOOTS:
            case LEATHER_BOOTS:
                return true;
            default:
                return false;
        }
    }

    public void registerFakeEnchantmentGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);

            Enchantment.registerEnchantment(FAKE_GLOW);
        } catch(IllegalArgumentException e1) {
            FAKE_GLOW = Enchantment.getById(70);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    static {
        FAKE_GLOW = new FakeGlow(70);
    }

}
