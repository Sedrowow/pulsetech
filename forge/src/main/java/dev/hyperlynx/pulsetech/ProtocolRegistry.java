package dev.hyperlynx.pulsetech;

import dev.hyperlynx.pulsetech.core.protocol.*;
import dev.hyperlynx.pulsetech.feature.cannon.CannonBlock;
import dev.hyperlynx.pulsetech.feature.controller.ControllerBlock;
import dev.hyperlynx.pulsetech.feature.orb.OrbBlock;
import dev.hyperlynx.pulsetech.feature.scanner.ScannerBlock;
import dev.hyperlynx.pulsetech.feature.screen.ScreenBlock;
import dev.hyperlynx.pulsetech.registration.ModBlockEntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class ProtocolRegistry {
    private static final Map<ResourceLocation, Protocol> PROTOCOLS = new HashMap<>();

    public static void register() {
        add(ModBlockEntityTypes.CONTROLLER, ProtocolBuilder.builder(3)
                .add(ControllerBlock.OFF)
                .add(ControllerBlock.ON)
                .add(ControllerBlock.PULSE)
                .add(ControllerBlock.LOOP_PULSE)
                .add(ControllerBlock.DELAY_PULSE)
                .add(ControllerBlock.TIMED_PULSE)
                .add(ControllerBlock.LOOP_DELAY_PULSE)
                .add(ControllerBlock.RANDOMS)
                .build());

        add(ModBlockEntityTypes.SCANNER, ProtocolBuilder.builder(2)
                .add(ScannerBlock.MODE_SELECT)
                .add(ScannerBlock.CHECK)
                .add(ScannerBlock.COUNT)
                .add(ScannerBlock.FIND_NEAREST)
                .build());

        add(ModBlockEntityTypes.SCREEN, ProtocolBuilder.builder(3)
                .add(ScreenBlock.BG)
                .add(ScreenBlock.CLEAR_BG)
                .add(ScreenBlock.PEN_COLOR)
                .add(ScreenBlock.RESET_PEN_COLOR)
                .add(ScreenBlock.MARK)
                .add(ScreenBlock.BOX)
                .add(ScreenBlock.TOGGLE_FG)
                .add(ScreenBlock.CLEAR_FG)
                .build());

        add(ModBlockEntityTypes.CANNON, ProtocolBuilder.builder(2)
                .add(CannonBlock.TARGET)
                .add(CannonBlock.FIRE)
                .build());

        add(ModBlockEntityTypes.ORB, ProtocolBuilder.builder(3)
                .add(OrbBlock.MOVE)
                .add(OrbBlock.HOME)
                .add(OrbBlock.GRAB)
                .add(OrbBlock.DROP)
                .add(OrbBlock.RECALL)
                .build());
    }

    private static void add(RegistryObject<? extends BlockEntityType<?>> typeSupplier, Protocol protocol) {
        BlockEntityType<?> type = typeSupplier.get();
        ResourceLocation key = ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(type);
        if (key != null) {
            PROTOCOLS.put(key, protocol);
        }
    }

    public static Protocol get(BlockEntityType<?> type) {
        ResourceLocation key = ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(type);
        return key != null ? PROTOCOLS.get(key) : null;
    }
}
