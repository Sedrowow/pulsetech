package dev.hyperlynx.pulsetech.feature.datacell;

import dev.hyperlynx.pulsetech.core.program.Macros;
import dev.hyperlynx.pulsetech.feature.scanner.ScannerLinkable;
import dev.hyperlynx.pulsetech.registration.ModBlocks;
import dev.hyperlynx.pulsetech.registration.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class DataCellItem extends Item {
    private static final String MACROS_KEY = "Macros";
    private static final String SCREEN_DATA_KEY = "ScreenData";
    private static final String SCANNER_LINK_KEY = "ScannerLinkPos";

    public DataCellItem(Properties properties) {
        super(properties);
    }

    public static boolean hasMacros(ItemStack stack) {
        return stack.hasTag() && stack.getOrCreateTag().contains(MACROS_KEY);
    }

    public static boolean hasScreenData(ItemStack stack) {
        return stack.hasTag() && stack.getOrCreateTag().contains(SCREEN_DATA_KEY);
    }

    public static boolean hasScannerLink(ItemStack stack) {
        return stack.hasTag() && stack.getOrCreateTag().contains(SCANNER_LINK_KEY);
    }

    public static float getLoadedProperty(ItemStack stack) {
        return (hasMacros(stack) || hasScreenData(stack) || hasScannerLink(stack)) ? 1.0F : 0.0F;
    }

    public static Macros getMacros(ItemStack stack) {
        if (!hasMacros(stack)) return null;
        return Macros.fromNbt(stack.getOrCreateTag().getCompound(MACROS_KEY));
    }

    public static void setMacros(ItemStack stack, Macros macros) {
        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag macroTag = new CompoundTag();
        macros.toNbt(macroTag);
        tag.put(MACROS_KEY, macroTag);
    }

    public static net.minecraft.core.BlockPos getScannerLinkPos(ItemStack stack) {
        if (!hasScannerLink(stack)) return null;
        CompoundTag tag = stack.getOrCreateTag();
        return net.minecraft.nbt.NbtUtils.readBlockPos(tag.getCompound(SCANNER_LINK_KEY));
    }

    public static void setScannerLinkPos(ItemStack stack, net.minecraft.core.BlockPos pos) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.put(SCANNER_LINK_KEY, net.minecraft.nbt.NbtUtils.writeBlockPos(pos));
    }

    public static dev.hyperlynx.pulsetech.feature.screen.ScreenData getScreenData(ItemStack stack) {
        if (!hasScreenData(stack)) return null;
        CompoundTag tag = stack.getOrCreateTag().getCompound(SCREEN_DATA_KEY);
        return dev.hyperlynx.pulsetech.feature.screen.ScreenData.CODEC.decode(
                net.minecraft.nbt.NbtOps.INSTANCE, tag
        ).result().map(p -> p.getFirst()).orElse(null);
    }

    public static void setScreenData(ItemStack stack, dev.hyperlynx.pulsetech.feature.screen.ScreenData data) {
        CompoundTag tag = stack.getOrCreateTag();
        dev.hyperlynx.pulsetech.feature.screen.ScreenData.CODEC.encodeStart(
                net.minecraft.nbt.NbtOps.INSTANCE, data
        ).result().ifPresent(nbt -> tag.put(SCREEN_DATA_KEY, nbt));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (player.isShiftKeyDown()) {
            ItemStack stack = player.getItemInHand(usedHand);
            CompoundTag tag = stack.getOrCreateTag();
            tag.remove(MACROS_KEY);
            tag.remove(SCREEN_DATA_KEY);
            tag.remove(SCANNER_LINK_KEY);
            player.playSound(ModSounds.BEEP.get(), 1.0F, 0.5F);
            return InteractionResultHolder.success(stack);
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        if (context.getLevel().getBlockState(context.getClickedPos()).is(ModBlocks.SCANNER.get())) {
            setScannerLinkPos(stack, context.getClickedPos());
            context.getLevel().playSound(null, context.getClickedPos(), ModSounds.BEEP.get(), SoundSource.PLAYERS, 1.0F, 1.1F + context.getLevel().random.nextFloat() * 0.05F);
            if (context.getPlayer() != null) {
                context.getPlayer().displayClientMessage(Component.translatable("pulsetech.stored_scanner_pos"), true);
            }
        } else if (hasScannerLink(stack) && context.getLevel().getBlockEntity(context.getClickedPos()) instanceof ScannerLinkable linkable) {
            boolean success = linkable.setLinkedOrigin(getScannerLinkPos(stack));
            if (context.getPlayer() != null) {
                if (success) {
                    context.getLevel().playSound(null, context.getClickedPos(), ModSounds.BEEP.get(), SoundSource.PLAYERS, 1.0F, 1.0F + context.getLevel().random.nextFloat() * 0.05F);
                    context.getPlayer().displayClientMessage(Component.translatable("pulsetech.linked_to_scanner"), true);
                } else {
                    context.getLevel().playSound(null, context.getClickedPos(), ModSounds.BEEP.get(), SoundSource.PLAYERS, 1.0F, 0.9F + context.getLevel().random.nextFloat() * 0.05F);
                    context.getPlayer().displayClientMessage(Component.translatable("pulsetech.failed_link_to_scanner"), true);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        boolean data = false;
        if (hasMacros(stack)) {
            Macros macros = getMacros(stack);
            if (macros != null) {
                tooltip.add(Component.literal(macros.macros().size() + " ").append(Component.translatable("pulsetech.macros_stored")).withStyle(ChatFormatting.GRAY));
                data = true;
            }
        }
        if (hasScreenData(stack)) {
            tooltip.add(Component.translatable("pulsetech.contains_screen_data").withStyle(ChatFormatting.GRAY));
            data = true;
        }
        if (hasScannerLink(stack)) {
            tooltip.add(Component.translatable("pulsetech.contains_scanner_link_pos").withStyle(ChatFormatting.GRAY));
            data = true;
        }
        if (data) {
            tooltip.add(Component.translatable("pulsetech.data_cell_clear_instructions").withStyle(ChatFormatting.GRAY));
        }
    }
}
