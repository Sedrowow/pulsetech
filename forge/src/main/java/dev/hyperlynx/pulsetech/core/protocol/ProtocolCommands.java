package dev.hyperlynx.pulsetech.core.protocol;

import dev.hyperlynx.pulsetech.Pulsetech;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

/// A static registry of all protocol commands.
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ProtocolCommands {
    public static final ResourceKey<Registry<ProtocolCommand>> REGISTRY_KEY = ResourceKey.createRegistryKey(Pulsetech.location("protocol_commands"));
    public static IForgeRegistry<ProtocolCommand> REGISTRY;
    public static final DeferredRegister<ProtocolCommand> COMMANDS = DeferredRegister.create(REGISTRY_KEY, Pulsetech.MODID);

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        REGISTRY = event.create(new RegistryBuilder<ProtocolCommand>()
                .setName(Pulsetech.location("protocol_commands"))
                .setDefaultKey(Pulsetech.location("error")));
    }
}
