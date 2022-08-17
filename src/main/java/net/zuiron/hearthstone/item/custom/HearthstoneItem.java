package net.zuiron.hearthstone.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HearthstoneItem extends Item {
    public HearthstoneItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient() && hand == Hand.MAIN_HAND) {
            if(!Screen.hasShiftDown()) {
                //shift not down, teleport to location. if no location set, output message.
                if(user.getStackInHand(hand).hasNbt()) {
                    //teleport
                    double x = user.getStackInHand(hand).getNbt().getDouble("hearthstone.x");
                    double y = user.getStackInHand(hand).getNbt().getDouble("hearthstone.y");
                    double z = user.getStackInHand(hand).getNbt().getDouble("hearthstone.z");
                    user.teleport(x, y, z);

                    // add a cooldown
                    user.getItemCooldownManager().set(this, 100);
                    user.sendMessage(Text.literal("Teleporting!"));
                }
            } else {
                //holding shift, set stone location
                NbtCompound nbtData = new NbtCompound();
                nbtData.putDouble("hearthstone.x", user.getPos().x);
                nbtData.putDouble("hearthstone.y", user.getPos().y);
                nbtData.putDouble("hearthstone.z", user.getPos().z);
                user.getStackInHand(hand).setNbt(nbtData);
                user.sendMessage(Text.literal("Location set!"));
            }
        }

        return super.use(world, user, hand);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.hasNbt();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.literal("Shift+Right click to bind location. Right click to TP.").formatted(Formatting.AQUA));
        } else {
            tooltip.add(Text.literal("Press [SHIFT] for more info!").formatted(Formatting.RED));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
