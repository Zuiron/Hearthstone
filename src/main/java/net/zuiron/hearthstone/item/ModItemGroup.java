package net.zuiron.hearthstone.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.zuiron.hearthstone.Hearthstone;

public class ModItemGroup {
    public static final ItemGroup HEARTHSTONE = FabricItemGroupBuilder.build(
            new Identifier(Hearthstone.MOD_ID, "hearthstone"), () -> new ItemStack(ModItems.HEARTHSTONE));
}
