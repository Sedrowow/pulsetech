package dev.hyperlynx.pulsetech.registration;

import dev.hyperlynx.pulsetech.Pulsetech;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Pulsetech.MODID);

    public static final RegistryObject<SoundEvent> CANNON_ZAP = SOUND_EVENTS.register(
            "cannon_zap",
            () -> SoundEvent.createVariableRangeEvent(Pulsetech.location("cannon_zap"))
    );
    public static final RegistryObject<SoundEvent> ORB_SPAWN = SOUND_EVENTS.register(
            "orb_spawn",
            () -> SoundEvent.createVariableRangeEvent(Pulsetech.location("orb_spawn"))
    );
    public static final RegistryObject<SoundEvent> ORB_COMMAND = SOUND_EVENTS.register(
            "orb_command",
            () -> SoundEvent.createVariableRangeEvent(Pulsetech.location("orb_command"))
    );
    public static final RegistryObject<SoundEvent> ORB_CONFIRM = SOUND_EVENTS.register(
            "orb_confirm",
            () -> SoundEvent.createVariableRangeEvent(Pulsetech.location("orb_confirm"))
    );
    public static final RegistryObject<SoundEvent> SCANNER_FOUND = SOUND_EVENTS.register(
            "scanner_found",
            () -> SoundEvent.createVariableRangeEvent(Pulsetech.location("scanner_found"))
    );
    public static final RegistryObject<SoundEvent> BEEP = SOUND_EVENTS.register(
            "beep",
            () -> SoundEvent.createVariableRangeEvent(Pulsetech.location("beep"))
    );
}
