package net.zuiron.hearthstone.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
        if(!world.isClient() && hand == Hand.MAIN_HAND && world.getDimensionKey().getValue().getPath() == "overworld") {
            if(!Screen.hasShiftDown()) {
                //shift not down, teleport to location. if no location set, output message.
                if(user.getStackInHand(hand).hasNbt()) {
                    //teleport
                    double x = user.getStackInHand(hand).getNbt().getDouble("hearthstone.x");
                    double y = user.getStackInHand(hand).getNbt().getDouble("hearthstone.y");
                    double z = user.getStackInHand(hand).getNbt().getDouble("hearthstone.z");
                    user.teleport(x, y, z);

                    // add a cooldown
                    user.getItemCooldownManager().set(this, 50);
                    user.sendMessage(Text.literal("Teleporting!"));
                    //play sound.
                    world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.BLOCKS, 0.4f, 1f);
                }
            } else {
                //holding shift, set stone location, only if not already set.
                if(!user.getStackInHand(hand).hasNbt()) {
                    NbtCompound nbtData = new NbtCompound();
                    nbtData.putDouble("hearthstone.x", user.getPos().x);
                    nbtData.putDouble("hearthstone.y", user.getPos().y);
                    nbtData.putDouble("hearthstone.z", user.getPos().z);
                    user.getStackInHand(hand).setNbt(nbtData);
                    user.sendMessage(Text.literal("Location set!"));

                    world.playSound(null, user.getBlockPos(), SoundEvents.AMBIENT_CAVE, SoundCategory.BLOCKS, 1f, 1f);
                }
            }
        }

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.literal("Shift+Right click to bind to current location").formatted(Formatting.AQUA));
        } else {
            if(stack.hasNbt()) {
                tooltip.add(Text.literal("BOUND - Use/Right click to TP").formatted(Formatting.DARK_AQUA));
            } else {
                tooltip.add(Text.literal("Hold [SHIFT] for more info").formatted(Formatting.RED));
            }
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
