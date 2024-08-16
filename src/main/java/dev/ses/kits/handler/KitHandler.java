package dev.ses.kits.handler;


import dev.ses.kits.manager.kit.Kit;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Getter
public class KitHandler {

    private final Map<UUID, String> variantMap;
    private final Map<UUID, Kit> kitMap;


    public KitHandler(){
        this.variantMap = new HashMap<>();
        this.kitMap = new HashMap<>();
    }
}
