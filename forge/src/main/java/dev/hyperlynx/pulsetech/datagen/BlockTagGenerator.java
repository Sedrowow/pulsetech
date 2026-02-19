package dev.hyperlynx.pulsetech.datagen;

import dev.hyperlynx.pulsetech.Pulsetech;
import dev.hyperlynx.pulsetech.registration.ModBlocks;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;


public class BlockTagGenerator extends BlockTagsProvider {
    public BlockTagGenerator(PackOutput output, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Pulsetech.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        net.minecraftforge.registries.ForgeRegistries.BLOCKS.getKey(ModBlocks.CANNON.get()),
                        net.minecraftforge.registries.ForgeRegistries.BLOCKS.getKey(ModBlocks.CONTROLLER.get()),
                        net.minecraftforge.registries.ForgeRegistries.BLOCKS.getKey(ModBlocks.SCANNER.get()),
                        net.minecraftforge.registries.ForgeRegistries.BLOCKS.getKey(ModBlocks.ORB.get()),
                        net.minecraftforge.registries.ForgeRegistries.BLOCKS.getKey(ModBlocks.SCREEN.get()),
                        net.minecraftforge.registries.ForgeRegistries.BLOCKS.getKey(ModBlocks.SCOPE.get()),
                        net.minecraftforge.registries.ForgeRegistries.BLOCKS.getKey(ModBlocks.CONSOLE.get()),
                        net.minecraftforge.registries.ForgeRegistries.BLOCKS.getKey(ModBlocks.RED_CONSOLE.get()),
                        net.minecraftforge.registries.ForgeRegistries.BLOCKS.getKey(ModBlocks.GREEN_CONSOLE.get()),
                        net.minecraftforge.registries.ForgeRegistries.BLOCKS.getKey(ModBlocks.INDIGO_CONSOLE.get()),
                        net.minecraftforge.registries.ForgeRegistries.BLOCKS.getKey(ModBlocks.WHITE_CONSOLE.get())
                );
    }
}
