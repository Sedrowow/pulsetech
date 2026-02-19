package dev.hyperlynx.pulsetech.feature.debugger.infotype;

import dev.hyperlynx.pulsetech.Pulsetech;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class DebuggerInfoTypes {
    public static final ResourceKey<Registry<DebuggerInfoType>> REGISTRY_KEY = ResourceKey.createRegistryKey(Pulsetech.location("debugger_info_types"));
    public static IForgeRegistry<DebuggerInfoType> REGISTRY;
    public static final DeferredRegister<DebuggerInfoType> TYPES = DeferredRegister.create(REGISTRY_KEY, Pulsetech.MODID);

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        REGISTRY = event.create(new RegistryBuilder<DebuggerInfoType>()
                .setName(Pulsetech.location("debugger_info_types"))
                .setDefaultKey(Pulsetech.location("error")));
    }

    public static final RegistryObject<DebuggerInfoType> SEQUENCE = TYPES.register("sequence", DebuggerInfoType::new);
    public static final RegistryObject<DebuggerInfoType> NUMBER = TYPES.register("number", DebuggerInfoType::new);
    public static final RegistryObject<DebuggerInfoType> TEXT = TYPES.register("text", DebuggerInfoType::new);
    public static final RegistryObject<DebuggerInfoType> BLOCK_POS = TYPES.register("pos", DebuggerInfoType::new);
}
