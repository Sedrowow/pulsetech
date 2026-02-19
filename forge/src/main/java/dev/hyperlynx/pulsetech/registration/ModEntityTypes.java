package dev.hyperlynx.pulsetech.registration;

import dev.hyperlynx.pulsetech.Pulsetech;
import dev.hyperlynx.pulsetech.feature.orb.Orb;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Pulsetech.MODID);

    public static final RegistryObject<EntityType<Orb>> ORB = TYPES.register("orb", () ->
            EntityType.Builder.of(Orb::new, MobCategory.MISC)
                    .sized(0.2F, 0.2F)
                    .fireImmune()
                    .setUpdateInterval(1)
                    .build("orb")
    );
}
