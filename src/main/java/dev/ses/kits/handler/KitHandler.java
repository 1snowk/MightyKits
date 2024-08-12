package dev.ses.kits.handler;


import dev.ses.kits.manager.kit.Kit;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Getter
public class KitHandler {

    private final Map<UUID, String> variantMap;
    private final Map<UUID, Kit> kitMap;
    private final Map<UUID, String> displayNameMap;
    private final Map<UUID, ItemStack> materialMap;


    public KitHandler(){
        this.displayNameMap = new HashMap<>();
        this.materialMap = new HashMap<>();
        this.variantMap = new HashMap<>();
        this.kitMap = new HashMap<>();
    }
}
