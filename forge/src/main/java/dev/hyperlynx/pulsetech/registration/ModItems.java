package dev.hyperlynx.pulsetech.registration;

import dev.hyperlynx.pulsetech.feature.datacell.DataCellItem;
import dev.hyperlynx.pulsetech.feature.debugger.DebuggerItem;
import dev.hyperlynx.pulsetech.feature.datasheet.DatasheetItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static dev.hyperlynx.pulsetech.Pulsetech.MODID;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<BlockItem> CONSOLE = ITEMS.register("console", () -> new BlockItem(ModBlocks.CONSOLE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> GREEN_CONSOLE = ITEMS.register("green_console", () -> new BlockItem(ModBlocks.GREEN_CONSOLE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> RED_CONSOLE = ITEMS.register("red_console", () -> new BlockItem(ModBlocks.RED_CONSOLE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> INDIGO_CONSOLE = ITEMS.register("indigo_console", () -> new BlockItem(ModBlocks.INDIGO_CONSOLE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> WHITE_CONSOLE = ITEMS.register("white_console", () -> new BlockItem(ModBlocks.WHITE_CONSOLE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> PATTERN_DETECTOR = ITEMS.register("pattern_detector", () -> new BlockItem(ModBlocks.PATTERN_DETECTOR.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> PATTERN_EMITTER = ITEMS.register("pattern_emitter", () -> new BlockItem(ModBlocks.PATTERN_EMITTER.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> NUMBER_MONITOR = ITEMS.register("number_monitor", () -> new BlockItem(ModBlocks.NUMBER_MONITOR.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> NUMBER_EMITTER = ITEMS.register("number_emitter", () -> new BlockItem(ModBlocks.NUMBER_EMITTER.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> CONTROLLER = ITEMS.register("controller", () -> new BlockItem(ModBlocks.CONTROLLER.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> SCANNER = ITEMS.register("scanner", () -> new BlockItem(ModBlocks.SCANNER.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> SCREEN = ITEMS.register("screen", () -> new BlockItem(ModBlocks.SCREEN.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> CANNON = ITEMS.register("cannon", () -> new BlockItem(ModBlocks.CANNON.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> ORB = ITEMS.register("orb", () -> new BlockItem(ModBlocks.ORB.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> SCOPE = ITEMS.register("scope", () -> new BlockItem(ModBlocks.SCOPE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> PROCESSOR = ITEMS.register("processor", () -> new BlockItem(ModBlocks.PROCESSOR.get(), new Item.Properties()));

    public static final RegistryObject<DatasheetItem> DATASHEET = ITEMS.register("datasheet", () -> new DatasheetItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DataCellItem> DATA_CELL = ITEMS.register("data_cell", () -> new DataCellItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DebuggerItem> DEBUGGER = ITEMS.register("debugger", () -> new DebuggerItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> PULSE_MODULE = ITEMS.register("pulse_module", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PROTOCOL_MODULE = ITEMS.register("protocol_module", () -> new Item(new Item.Properties()));
}
