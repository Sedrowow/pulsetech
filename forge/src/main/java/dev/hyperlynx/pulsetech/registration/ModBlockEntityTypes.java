package dev.hyperlynx.pulsetech.registration;

import dev.hyperlynx.pulsetech.Pulsetech;
import dev.hyperlynx.pulsetech.feature.cannon.CannonBlockEntity;
import dev.hyperlynx.pulsetech.feature.console.block.ConsoleBlockEntity;
import dev.hyperlynx.pulsetech.feature.controller.ControllerBlockEntity;
import dev.hyperlynx.pulsetech.feature.number.block.NumberEmitterBlockEntity;
import dev.hyperlynx.pulsetech.feature.number.block.NumberMonitorBlockEntity;
import dev.hyperlynx.pulsetech.feature.orb.OrbBlockEntity;
import dev.hyperlynx.pulsetech.feature.pattern.block.PatternDetectorBlockEntity;
import dev.hyperlynx.pulsetech.feature.pattern.block.PatternEmitterBlockEntity;
import dev.hyperlynx.pulsetech.feature.processor.ProcessorBlockEntity;
import dev.hyperlynx.pulsetech.feature.scanner.ScannerBlockEntity;
import dev.hyperlynx.pulsetech.feature.scope.ScopeBlockEntity;
import dev.hyperlynx.pulsetech.feature.screen.ScreenBlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Pulsetech.MODID);

    public static final RegistryObject<BlockEntityType<PatternDetectorBlockEntity>> PATTERN_DETECTOR =
            TYPES.register("pattern_detector", () ->
                    BlockEntityType.Builder.of(PatternDetectorBlockEntity::new, ModBlocks.PATTERN_DETECTOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<PatternEmitterBlockEntity>> PATTERN_EMITTER =
            TYPES.register("pattern_emitter", () ->
                    BlockEntityType.Builder.of(PatternEmitterBlockEntity::new, ModBlocks.PATTERN_EMITTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<NumberEmitterBlockEntity>> NUMBER_EMITTER =
            TYPES.register("number_emitter", () ->
                    BlockEntityType.Builder.of(NumberEmitterBlockEntity::new, ModBlocks.NUMBER_EMITTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<NumberMonitorBlockEntity>> NUMBER_MONITOR =
            TYPES.register("number_monitor", () ->
                    BlockEntityType.Builder.of(NumberMonitorBlockEntity::new, ModBlocks.NUMBER_MONITOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<ConsoleBlockEntity>> CONSOLE =
            TYPES.register("console", () ->
                    BlockEntityType.Builder.of(ConsoleBlockEntity::new,
                            ModBlocks.CONSOLE.get(),
                            ModBlocks.RED_CONSOLE.get(),
                            ModBlocks.GREEN_CONSOLE.get(),
                            ModBlocks.INDIGO_CONSOLE.get(),
                            ModBlocks.WHITE_CONSOLE.get()
                    ).build(null));

    public static final RegistryObject<BlockEntityType<ControllerBlockEntity>> CONTROLLER =
            TYPES.register("controller", () ->
                    BlockEntityType.Builder.of(ControllerBlockEntity::new, ModBlocks.CONTROLLER.get()).build(null));

    public static final RegistryObject<BlockEntityType<ScannerBlockEntity>> SCANNER =
            TYPES.register("scanner", () ->
                    BlockEntityType.Builder.of(ScannerBlockEntity::new, ModBlocks.SCANNER.get()).build(null));

    public static final RegistryObject<BlockEntityType<ScopeBlockEntity>> SCOPE =
            TYPES.register("scope", () ->
                    BlockEntityType.Builder.of(ScopeBlockEntity::new, ModBlocks.SCOPE.get()).build(null));

    public static final RegistryObject<BlockEntityType<ScreenBlockEntity>> SCREEN =
            TYPES.register("screen", () ->
                    BlockEntityType.Builder.of(ScreenBlockEntity::new, ModBlocks.SCREEN.get()).build(null));

    public static final RegistryObject<BlockEntityType<CannonBlockEntity>> CANNON =
            TYPES.register("cannon", () ->
                    BlockEntityType.Builder.of(CannonBlockEntity::new, ModBlocks.CANNON.get()).build(null));

    public static final RegistryObject<BlockEntityType<OrbBlockEntity>> ORB =
            TYPES.register("orb", () ->
                    BlockEntityType.Builder.of(OrbBlockEntity::new, ModBlocks.ORB.get()).build(null));

    public static final RegistryObject<BlockEntityType<ProcessorBlockEntity>> PROCESSOR =
            TYPES.register("processor", () ->
                    BlockEntityType.Builder.of(ProcessorBlockEntity::new, ModBlocks.PROCESSOR.get()).build(null));
}
