package dev.ses.kits.handler;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class CategoryHandler {

    private final Map<UUID, String> variantMap;
    private final Map<UUID, CategoryHandler> categoryMap;

    public CategoryHandler() {
        this.variantMap = new HashMap<>();
        this.categoryMap = new HashMap<>();
    }
}
