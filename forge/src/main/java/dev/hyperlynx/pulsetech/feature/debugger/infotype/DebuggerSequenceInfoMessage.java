package dev.hyperlynx.pulsetech.feature.debugger.infotype;

import dev.hyperlynx.pulsetech.client.ClientWrapper;
import dev.hyperlynx.pulsetech.core.Sequence;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class DebuggerSequenceInfoMessage {
    private final Sequence sequence;

    public DebuggerSequenceInfoMessage(Sequence sequence) {
        this.sequence = sequence;
    }

    public DebuggerSequenceInfoMessage(FriendlyByteBuf buf) {
        this.sequence = Sequence.readFromBuf(buf);
    }

    public void encode(FriendlyByteBuf buf) {
        Sequence.writeToBuf(buf, sequence);
    }

    public static void handle(DebuggerSequenceInfoMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientWrapper.acceptDebuggerSequenceInfo(new DebuggerSequenceInfo(msg.sequence)));
        ctx.get().setPacketHandled(true);
    }
}
