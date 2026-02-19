package dev.hyperlynx.pulsetech;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.IntValue CANNON_MAX_RANGE;
    public static final ForgeConfigSpec.IntValue CANNON_MAX_BLAST_RESIST;
    public static final ForgeConfigSpec.IntValue ORB_MAX_RANGE;
    public static final ForgeConfigSpec.ConfigValue<List<String>> ORB_CANNOT_GRAB;
    public static final ForgeConfigSpec.DoubleValue ORB_SPEED;
    public static final ForgeConfigSpec.IntValue SCANNER_RANGE;
    public static final ForgeConfigSpec.IntValue MAX_MACRO_UNWRAP_COUNT;

    static {
        MAX_MACRO_UNWRAP_COUNT = BUILDER.comment("Maximum number of times the Console or Processor can unwrap macros in a single evaluation. Higher values mean more complex programs are possible, but may cause lag spikes when the macro is evaluated. [Default: 256]").defineInRange("maxMacroUnwrapCount", 256, 4, 512);
        CANNON_MAX_RANGE = BUILDER.comment("Maximum range of the Coil Cannon in blocks. [Default: 25]").defineInRange("cannonMaxRange", 25, 1, 64);
        CANNON_MAX_BLAST_RESIST = BUILDER.comment("The Coil Cannon can break blocks with below this blast resistance. [Default: 50]").defineInRange("cannonMaxBlastResist", 50, 1, Integer.MAX_VALUE);
        ORB_MAX_RANGE = BUILDER.comment("Maximum range of the Orb Projector in blocks. [Default: 32]").defineInRange("orbProjectorMaxRange", 32, 1, 64);
        ORB_CANNOT_GRAB = BUILDER.comment("A list of entities the Orb can never pick up. [Default: \"minecraft:ender_dragon\", \"minecraft:wither\", \"minecraft:warden\"]")
                .define("orbCannotGrab", Lists.newArrayList("minecraft:ender_dragon", "minecraft:wither", "minecraft:warden"));
        ORB_SPEED = BUILDER.comment("How far the Orb moves each movement tick in blocks. [Default: 0.12]").defineInRange("orbSpeed", 0.12, 0.01, 1.0);
        SCANNER_RANGE = BUILDER.comment("The range of detection for the Scanner has a radius of this many blocks. [Default: 16]").defineInRange("scannerRange", 16, 4, 64);
        SPEC = BUILDER.build();
    }
}
