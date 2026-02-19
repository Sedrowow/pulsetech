package dev.hyperlynx.pulsetech.network;

import dev.hyperlynx.pulsetech.Pulsetech;
import dev.hyperlynx.pulsetech.feature.console.ConsoleLineMessage;
import dev.hyperlynx.pulsetech.feature.console.ConsolePriorLinesMessage;
import dev.hyperlynx.pulsetech.feature.console.OpenConsoleMessage;
import dev.hyperlynx.pulsetech.feature.debugger.DebuggerInfoManifestMessage;
import dev.hyperlynx.pulsetech.feature.debugger.DebuggerInfoRequestMessage;
import dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerByteInfoMessage;
import dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerPosInfoMessage;
import dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerSequenceInfoMessage;
import dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerTextInfoMessage;
import dev.hyperlynx.pulsetech.feature.number.NumberSelectMessage;
import dev.hyperlynx.pulsetech.feature.pattern.OpenSequenceChooserMessage;
import dev.hyperlynx.pulsetech.feature.pattern.SequenceSelectMessage;
import dev.hyperlynx.pulsetech.feature.screen.ScreenUpdateMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Pulsetech.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;
        CHANNEL.registerMessage(id++, OpenConsoleMessage.class, OpenConsoleMessage::encode, OpenConsoleMessage::new, OpenConsoleMessage::handle);
        CHANNEL.registerMessage(id++, ConsoleLineMessage.class, ConsoleLineMessage::encode, ConsoleLineMessage::new, ConsoleLineMessage::handle);
        CHANNEL.registerMessage(id++, ConsolePriorLinesMessage.class, ConsolePriorLinesMessage::encode, ConsolePriorLinesMessage::new, ConsolePriorLinesMessage::handle);
        CHANNEL.registerMessage(id++, OpenSequenceChooserMessage.class, OpenSequenceChooserMessage::encode, OpenSequenceChooserMessage::new, OpenSequenceChooserMessage::handle);
        CHANNEL.registerMessage(id++, SequenceSelectMessage.class, SequenceSelectMessage::encode, SequenceSelectMessage::new, SequenceSelectMessage::handle);
        CHANNEL.registerMessage(id++, ScreenUpdateMessage.class, ScreenUpdateMessage::encode, ScreenUpdateMessage::new, ScreenUpdateMessage::handle);
        CHANNEL.registerMessage(id++, NumberSelectMessage.class, NumberSelectMessage::encode, NumberSelectMessage::new, NumberSelectMessage::handle);
        CHANNEL.registerMessage(id++, DebuggerInfoManifestMessage.class, DebuggerInfoManifestMessage::encode, DebuggerInfoManifestMessage::new, DebuggerInfoManifestMessage::handle);
        CHANNEL.registerMessage(id++, DebuggerInfoRequestMessage.class, DebuggerInfoRequestMessage::encode, DebuggerInfoRequestMessage::new, DebuggerInfoRequestMessage::handle);
        CHANNEL.registerMessage(id++, DebuggerSequenceInfoMessage.class, DebuggerSequenceInfoMessage::encode, DebuggerSequenceInfoMessage::new, DebuggerSequenceInfoMessage::handle);
        CHANNEL.registerMessage(id++, DebuggerByteInfoMessage.class, DebuggerByteInfoMessage::encode, DebuggerByteInfoMessage::new, DebuggerByteInfoMessage::handle);
        CHANNEL.registerMessage(id++, DebuggerTextInfoMessage.class, DebuggerTextInfoMessage::encode, DebuggerTextInfoMessage::new, DebuggerTextInfoMessage::handle);
        CHANNEL.registerMessage(id++, DebuggerPosInfoMessage.class, DebuggerPosInfoMessage::encode, DebuggerPosInfoMessage::new, DebuggerPosInfoMessage::handle);
    }
}
