package dev.hyperlynx.pulsetech.feature.console;

import dev.hyperlynx.pulsetech.Pulsetech;
import dev.hyperlynx.pulsetech.client.ClientWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class ConsolePriorLinesMessage {
    private final BlockPos pos;
    private final String priorLines;
    private final String commandBoxText;

    public ConsolePriorLinesMessage(BlockPos pos, String priorLines, String commandBoxText) {
        this.pos = pos;
        this.priorLines = priorLines;
        this.commandBoxText = commandBoxText;
    }

    public ConsolePriorLinesMessage(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.priorLines = buf.readUtf();
        this.commandBoxText = buf.readUtf();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeUtf(priorLines);
        buf.writeUtf(commandBoxText);
    }

    public static void handle(ConsolePriorLinesMessage msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (context.getDirection().getReceptionSide().isServer()) {
            context.enqueueWork(() -> {
                var sender = context.getSender();
                if (sender == null) return;
                if (!(sender.level().getBlockEntity(msg.pos) instanceof ConsoleBlockEntity console)) {
                    Pulsetech.LOGGER.error("Received command for nonexistent console at {}", msg.pos);
                    return;
                }
                if (!sender.level().isLoaded(msg.pos)) return;
                console.savePriorLines(msg.priorLines, msg.commandBoxText);
            });
        } else {
            context.enqueueWork(() -> ClientWrapper.setPriorConsoleLines(msg.pos, msg.priorLines, msg.commandBoxText));
        }
        context.setPacketHandled(true);
    }
}
