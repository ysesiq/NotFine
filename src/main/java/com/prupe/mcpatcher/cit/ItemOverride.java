package com.prupe.mcpatcher.cit;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.Icon;
import net.minecraft.ResourceLocation;

import com.prupe.mcpatcher.mal.resource.PropertiesFile;
import com.prupe.mcpatcher.mal.tile.TileLoader;

final class ItemOverride extends OverrideBase {

    private Icon icon;
    private final Map<Icon, Icon> iconMap;

    ItemOverride(PropertiesFile properties) {
        super(properties);

        if (items == null) {
            properties.error("no matching items specified");
        }

        iconMap = alternateTextures == null ? null : new HashMap<>();
    }

    @Override
    String getType() {
        return "item";
    }

    Icon getReplacementIcon(Icon origIcon) {
        if (iconMap != null) {
            Icon newIcon = iconMap.get(origIcon);
            if (newIcon != null) {
                return newIcon;
            }
        }
        return icon;
    }

    void preload(TileLoader tileLoader) {
        String special = null;
        if (items != null) {
            if (items.contains(CITUtils.itemCompass)) {
                special = "compass";
            } else if (items.contains(CITUtils.itemClock)) {
                special = "clock";
            }
        }
        if (textureName != null) {
            tileLoader.preloadTile(textureName, false, special);
        }
        if (alternateTextures != null) {
            for (Map.Entry<String, ResourceLocation> entry : alternateTextures.entrySet()) {
                tileLoader.preloadTile(entry.getValue(), false, special);
            }
        }
    }

    void registerIcon(TileLoader tileLoader) {
        if (textureName != null) {
            icon = tileLoader.getIcon(textureName);
        }
        if (alternateTextures != null) {
            for (Map.Entry<String, ResourceLocation> entry : alternateTextures.entrySet()) {
                Icon from = tileLoader.getIcon(entry.getKey());
                Icon to = tileLoader.getIcon(entry.getValue());
                if (from != null && to != null) {
                    iconMap.put(from, to);
                }
            }
        }
    }

    @Override
    String preprocessAltTextureKey(String name) {
        if (name.startsWith("textures/items/")) {
            name = name.substring(15);
            if (name.endsWith(".png")) {
                name = name.substring(0, name.length() - 4);
            }
        }
        return name;
    }
}
