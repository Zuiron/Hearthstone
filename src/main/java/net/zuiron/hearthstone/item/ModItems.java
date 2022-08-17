package net.zuiron.hearthstone.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zuiron.hearthstone.Hearthstone;
import net.zuiron.hearthstone.item.custom.HearthstoneItem;

public class ModItems {
    public static final Item HEARTHSTONE = registerItem("hearthstone",
    new HearthstoneItem(new FabricItemSettings().group(ModItemGroup.HEARTHSTONE).maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Hearthstone.MOD_ID, name), item);
    }
    public static void registerModItems() {
        Hearthstone.LOGGER.debug("Registering Mod Items for " + Hearthstone.MOD_ID);
    }
}
