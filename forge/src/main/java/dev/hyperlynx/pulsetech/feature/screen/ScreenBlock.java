package dev.hyperlynx.pulsetech.feature.screen;

import com.mojang.serialization.MapCodec;
import dev.hyperlynx.pulsetech.core.PulseBlock;
import dev.hyperlynx.pulsetech.core.protocol.ExecutionContext;
import dev.hyperlynx.pulsetech.core.protocol.ProtocolCommand;
import dev.hyperlynx.pulsetech.core.protocol.ProtocolCommands;
import dev.hyperlynx.pulsetech.registration.ModItems;
import dev.hyperlynx.pulsetech.registration.ModSounds;
import dev.hyperlynx.pulsetech.util.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
// DeferredHolder replaced by RegistryObject
import org.jetbrains.annotations.Nullable;

public class ScreenBlock extends PulseBlock {
    protected static final VoxelShape SHAPE_EAST = Shapes.or(Block.box(0, 0, 0, 2, 1, 16), Block.box(0, 1, 0, 2, 15, 1), Block.box(0, 1, 15, 2, 15, 16), Block.box(0, 15, 0, 2, 16, 16), Block.box(14, 0, 0, 16, 2, 16), Block.box(2, 0, 14, 14, 2, 16), Block.box(2, 0, 0, 14, 2, 2), Block.box(1, 1, 1, 6, 15, 15), Block.box(2, 2, 0, 6, 6, 1), Block.box(2, 2, 15, 6, 6, 16));
    protected static final VoxelShape SHAPE_SOUTH = Shapes.or(Block.box(0, 0, 0, 16, 1, 2), Block.box(0, 1, 0, 1, 15, 2), Block.box(15, 1, 0, 16, 15, 2), Block.box(0, 15, 0, 16, 16, 2), Block.box(0, 0, 14, 16, 2, 16), Block.box(14, 0, 2, 16, 2, 14), Block.box(0, 0, 2, 2, 2, 14), Block.box(1, 1, 1, 15, 15, 6), Block.box(0, 2, 2, 1, 6, 6), Block.box(15, 2, 2, 16, 6, 6));
    protected static final VoxelShape SHAPE_WEST = Shapes.or(Block.box(14, 0, 0, 16, 1, 16), Block.box(14, 1, 15, 16, 15, 16), Block.box(14, 1, 0, 16, 15, 1), Block.box(14, 15, 0, 16, 16, 16), Block.box(0, 0, 0, 2, 2, 16), Block.box(2, 0, 0, 14, 2, 2), Block.box(2, 0, 14, 14, 2, 16), Block.box(10, 1, 1, 15, 15, 15), Block.box(10, 2, 15, 14, 6, 16), Block.box(10, 2, 0, 14, 6, 1));
    protected static final VoxelShape SHAPE_NORTH = Shapes.or(Block.box(0, 0, 14, 16, 1, 16), Block.box(15, 1, 14, 16, 15, 16), Block.box(0, 1, 14, 1, 15, 16), Block.box(0, 15, 14, 16, 16, 16), Block.box(0, 0, 0, 16, 2, 2), Block.box(0, 0, 2, 2, 2, 14), Block.box(14, 0, 2, 16, 2, 14), Block.box(1, 1, 10, 15, 15, 15), Block.box(15, 2, 10, 16, 6, 14), Block.box(0, 2, 10, 1, 6, 14));

    public ScreenBlock(Properties properties, SideIO io) {
        super(properties, io, true);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return pulseCodec(ScreenBlock::new);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch(state.getValue(FACING)) {
            case SOUTH -> SHAPE_NORTH;
            case WEST -> SHAPE_EAST;
            case EAST -> SHAPE_WEST;
            default -> SHAPE_SOUTH;
        };
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ScreenBlockEntity(blockPos, blockState);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.isClientSide()) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
        if (level.getBlockEntity(pos) instanceof ScreenBlockEntity screen && stack.is(ModItems.DATA_CELL.get())) {
            ScreenData data = screen.getScreenData();
            if (dev.hyperlynx.pulsetech.feature.datacell.DataCellItem.hasScreenData(stack)) {
                ScreenData to_be_applied_to_screen = dev.hyperlynx.pulsetech.feature.datacell.DataCellItem.getScreenData(stack);
                if (screen.isNotBlank()) {
                    // If the screen has existing screen data, swap them if they're different.
                    if(data.equals(to_be_applied_to_screen)) {
                        return InteractionResult.SUCCESS;
                    }
                    dev.hyperlynx.pulsetech.feature.datacell.DataCellItem.setScreenData(stack, screen.getScreenData());
                    player.displayClientMessage(Component.translatable("pulsetech.screen_swapped"), true);
                }
                level.playSound(null, pos, ModSounds.BEEP.get(), SoundSource.PLAYERS, 1.0F, 1.1F + level.random.nextFloat() * 0.05F);
                screen.setScreenData(to_be_applied_to_screen);
                screen.sendUpdate();
            } else if(screen.isNotBlank()) {
                level.playSound(null, pos, ModSounds.BEEP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                dev.hyperlynx.pulsetech.feature.datacell.DataCellItem.setScreenData(stack, data);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    public static final RegistryObject<ProtocolCommand> BG = ProtocolCommands.COMMANDS.register("screen/bg", () ->
            new ProtocolCommand(3) {
                @Override
                public void run(ExecutionContext context) {
                    if(context.block() instanceof ScreenBlockEntity screen) {
                        screen.setBackgroundColor(new Color(Byte.toUnsignedInt(context.params().get(0)), Byte.toUnsignedInt(context.params().get(1)), Byte.toUnsignedInt(context.params().get(2))));
                        screen.sendUpdate();
                    }
                }
            });

    public static final RegistryObject<ProtocolCommand> CLEAR_BG = ProtocolCommands.COMMANDS.register("screen/clear_bg", () ->
            new ProtocolCommand(0) {
                @Override
                public void run(ExecutionContext context) {
                    if(context.block() instanceof ScreenBlockEntity screen) {
                        screen.setBackgroundColor(Color.black());
                        screen.sendUpdate();
                    }
                }
            });

    public static final RegistryObject<ProtocolCommand> PEN_COLOR = ProtocolCommands.COMMANDS.register("screen/pen_color", () ->
            new ProtocolCommand(3) {
                @Override
                public void run(ExecutionContext context) {
                    if(context.block() instanceof ScreenBlockEntity screen) {
                        screen.setPenColor(new Color(Byte.toUnsignedInt(context.params().get(0)), Byte.toUnsignedInt(context.params().get(1)), Byte.toUnsignedInt(context.params().get(2))));
                    }
                }
            });

    public static final RegistryObject<ProtocolCommand> RESET_PEN_COLOR = ProtocolCommands.COMMANDS.register("screen/reset_pen_color", () ->
            new ProtocolCommand(0) {
                @Override
                public void run(ExecutionContext context) {
                    if(context.block() instanceof ScreenBlockEntity screen) {
                        screen.setPenColor(Color.white());
                    }
                }
            });

    public static final RegistryObject<ProtocolCommand> MARK = ProtocolCommands.COMMANDS.register("screen/mark", () ->
            new ProtocolCommand(2) {
                @Override
                public void run(ExecutionContext context) {
                    if(context.block() instanceof ScreenBlockEntity screen) {
                        screen.drawPixel(context.params().get(0), context.params().get(1));
                        screen.sendUpdate();
                    }
                }
            });

    public static final RegistryObject<ProtocolCommand> BOX = ProtocolCommands.COMMANDS.register("screen/box", () ->
            new ProtocolCommand(4) {
                @Override
                public void run(ExecutionContext context) {
                    if(context.block() instanceof ScreenBlockEntity screen) {
                        screen.drawBox(context.params().get(0), context.params().get(1), context.params().get(2), context.params().get(3));
                        screen.sendUpdate();
                    }
                }
            });

    public static final RegistryObject<ProtocolCommand> TOGGLE_FG = ProtocolCommands.COMMANDS.register("screen/toggle_fg", () ->
            new ProtocolCommand(0) {
                @Override
                public void run(ExecutionContext context) {
                    if(context.block() instanceof ScreenBlockEntity screen) {
                        screen.toggleForeground();
                        screen.sendUpdate();
                    }
                }
            });

    public static final RegistryObject<ProtocolCommand> CLEAR_FG = ProtocolCommands.COMMANDS.register("screen/clear_fg", () ->
            new ProtocolCommand(0) {
                @Override
                public void run(ExecutionContext context) {
                    if(context.block() instanceof ScreenBlockEntity screen) {
                        screen.clearForeground();
                        screen.sendUpdate();
                    }
                }
            });
}
