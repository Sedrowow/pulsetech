package dev.hyperlynx.pulsetech.datagen;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class PulsetechDataGen {
    @SubscribeEvent
    public static void gatherData (GatherDataEvent event) throws ExecutionException, InterruptedException {
        var output = event.getGenerator().getPackOutput();

        event.getGenerator().addProvider(
                event.includeServer(),
                new RecipeGenerator(output)
        );

        event.getGenerator().addProvider(
                event.includeServer(),
                new LootTableProvider(
                        output,
                        Set.of(),
                        List.of(
                                new LootTableProvider.SubProviderEntry(
                                        BlockLootTableGenerator::new,
                                        LootContextParamSets.BLOCK
                                )
                        )
                )
        );

        event.getGenerator().addProvider(
                event.includeServer(),
                new BlockTagGenerator(output, event.getExistingFileHelper())
        );

        event.getGenerator().addProvider(
                event.includeClient(),
                new SoundsJsonGenerator(output, event.getExistingFileHelper())
        );
    }
}
