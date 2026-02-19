package dev.hyperlynx.pulsetech;

import com.mojang.logging.LogUtils;
import dev.hyperlynx.pulsetech.core.protocol.ProtocolCommands;
import dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerInfoTypes;
import dev.hyperlynx.pulsetech.network.ModMessages;
import dev.hyperlynx.pulsetech.registration.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Pulsetech.MODID)
public class Pulsetech {
    public static final String MODID = "pulsetech";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Pulsetech() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);
        ModBlocks.BLOCKS.register(bus);
        ModBlockEntityTypes.TYPES.register(bus);
        ModItems.ITEMS.register(bus);
        ModEntityTypes.TYPES.register(bus);
        ModCreativeTab.TABS.register(bus);
        ModSounds.SOUND_EVENTS.register(bus);
        ProtocolCommands.COMMANDS.register(bus);
        DebuggerInfoTypes.TYPES.register(bus);
        MinecraftForge.EVENT_BUS.register(ModCommands.class);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModMessages::register);
        event.enqueueWork(ProtocolRegistry::register);
    }

    public static ResourceLocation location(String path) {
        return new ResourceLocation(MODID, path);
    }
}
