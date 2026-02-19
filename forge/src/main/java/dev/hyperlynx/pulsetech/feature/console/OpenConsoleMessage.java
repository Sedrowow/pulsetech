package dev.hyperlynx.pulsetech.feature.console;

import dev.hyperlynx.pulsetech.client.ClientWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class OpenConsoleMessage {
    private final BlockPos pos;
    private final String priorLines;
    private final String commandBoxText;

    public OpenConsoleMessage(BlockPos pos, String priorLines, String commandBoxText) {
        this.pos = pos;
        this.priorLines = priorLines;
        this.commandBoxText = commandBoxText;
    }

    public OpenConsoleMessage(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.priorLines = buf.readUtf();
        this.commandBoxText = buf.readUtf();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeUtf(priorLines);
        buf.writeUtf(commandBoxText);
    }

    public static void handle(OpenConsoleMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientWrapper.openConsoleScreen(msg.pos, msg.priorLines, msg.commandBoxText));
        ctx.get().setPacketHandled(true);
    }
}
