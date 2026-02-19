package dev.hyperlynx.pulsetech.registration;

import dev.hyperlynx.pulsetech.feature.cannon.CannonBlock;
import dev.hyperlynx.pulsetech.feature.console.ConsoleColor;
import dev.hyperlynx.pulsetech.feature.console.block.ConsoleBlock;
import dev.hyperlynx.pulsetech.feature.controller.ControllerBlock;
import dev.hyperlynx.pulsetech.feature.number.block.NumberEmitterBlock;
import dev.hyperlynx.pulsetech.feature.number.block.NumberMonitorBlock;
import dev.hyperlynx.pulsetech.feature.orb.OrbBlock;
import dev.hyperlynx.pulsetech.feature.pattern.block.PatternDetectorBlock;
import dev.hyperlynx.pulsetech.feature.pattern.block.PatternEmitterBlock;
import dev.hyperlynx.pulsetech.core.PulseBlock;
import dev.hyperlynx.pulsetech.feature.pattern.block.PatternBlock;
import dev.hyperlynx.pulsetech.feature.processor.ProcessorBlock;
import dev.hyperlynx.pulsetech.feature.scanner.ScannerBlock;
import dev.hyperlynx.pulsetech.feature.scope.ScopeBlock;
import dev.hyperlynx.pulsetech.feature.screen.ScreenBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static dev.hyperlynx.pulsetech.Pulsetech.MODID;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<PatternBlock> PATTERN_DETECTOR = BLOCKS.register("pattern_detector", () ->
            new PatternDetectorBlock(BlockBehaviour.Properties.copy(Blocks.REPEATER), PulseBlock.FRONT_OUT_OTHER_IN));

    public static final RegistryObject<PatternBlock> PATTERN_EMITTER = BLOCKS.register("pattern_emitter", () ->
            new PatternEmitterBlock(BlockBehaviour.Properties.copy(Blocks.REPEATER), PulseBlock.FRONT_OUT_OTHER_IN));

    public static final RegistryObject<PulseBlock> NUMBER_EMITTER = BLOCKS.register("number_emitter", () ->
            new NumberEmitterBlock(BlockBehaviour.Properties.copy(Blocks.REPEATER), PulseBlock.FRONT_OUT_OTHER_IN));

    public static final RegistryObject<PulseBlock> NUMBER_MONITOR = BLOCKS.register("number_monitor", () ->
            new NumberMonitorBlock(BlockBehaviour.Properties.copy(Blocks.REPEATER), PulseBlock.ALL_SIDES_INPUT));

    public static final RegistryObject<ConsoleBlock> CONSOLE = BLOCKS.register("console", () ->
            new ConsoleBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_QUARTZ_BLOCK), ConsoleColor.AMBER, PulseBlock.MAIN_OUTPUT_ONLY));

    public static final RegistryObject<ConsoleBlock> RED_CONSOLE = BLOCKS.register("red_console", () ->
            new ConsoleBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_QUARTZ_BLOCK), ConsoleColor.REDSTONESTONE, PulseBlock.MAIN_OUTPUT_ONLY));

    public static final RegistryObject<ConsoleBlock> GREEN_CONSOLE = BLOCKS.register("green_console", () ->
            new ConsoleBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_QUARTZ_BLOCK), ConsoleColor.GREEN, PulseBlock.MAIN_OUTPUT_ONLY));

    public static final RegistryObject<ConsoleBlock> INDIGO_CONSOLE = BLOCKS.register("indigo_console", () ->
            new ConsoleBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_QUARTZ_BLOCK), ConsoleColor.INDIGO, PulseBlock.MAIN_OUTPUT_ONLY));

    public static final RegistryObject<ConsoleBlock> WHITE_CONSOLE = BLOCKS.register("white_console", () ->
            new ConsoleBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_QUARTZ_BLOCK), ConsoleColor.WHITE, PulseBlock.MAIN_OUTPUT_ONLY));

    public static final RegistryObject<ControllerBlock> CONTROLLER = BLOCKS.register("controller", () ->
            new ControllerBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), PulseBlock.FRONT_OUT_BACK_IN));

    public static final RegistryObject<ScannerBlock> SCANNER = BLOCKS.register("scanner", () ->
            new ScannerBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK), PulseBlock.FRONT_OUT_BACK_IN));

    public static final RegistryObject<ScopeBlock> SCOPE = BLOCKS.register("scope", () ->
            new ScopeBlock(BlockBehaviour.Properties.copy(Blocks.REPEATER).lightLevel(state -> 0), PulseBlock.MAIN_INPUT_ONLY));

    public static final RegistryObject<ScreenBlock> SCREEN = BLOCKS.register("screen", () ->
            new ScreenBlock(BlockBehaviour.Properties.copy(Blocks.IRON_TRAPDOOR), PulseBlock.MAIN_INPUT_ONLY));

    public static final RegistryObject<CannonBlock> CANNON = BLOCKS.register("cannon", () ->
            new CannonBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), PulseBlock.ALL_SIDES_INPUT));

    public static final RegistryObject<OrbBlock> ORB = BLOCKS.register("orb", () ->
            new OrbBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK), PulseBlock.ALL_SIDES_INPUT));

    public static final RegistryObject<ProcessorBlock> PROCESSOR = BLOCKS.register("processor", () ->
            new ProcessorBlock(BlockBehaviour.Properties.copy(Blocks.COMPARATOR), PulseBlock.FRONT_OUT_OTHER_IN));
}
