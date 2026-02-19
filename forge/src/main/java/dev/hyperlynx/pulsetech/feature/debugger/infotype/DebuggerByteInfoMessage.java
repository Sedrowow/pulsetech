package dev.hyperlynx.pulsetech.feature.debugger.infotype;

import dev.hyperlynx.pulsetech.client.ClientWrapper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class DebuggerByteInfoMessage {
    private final byte number;

    public DebuggerByteInfoMessage(byte number) {
        this.number = number;
    }

    public DebuggerByteInfoMessage(FriendlyByteBuf buf) {
        this.number = buf.readByte();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeByte(number);
    }

    public static void handle(DebuggerByteInfoMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientWrapper.acceptDebuggerByteInfo(new DebuggerByteInfo(msg.number)));
        ctx.get().setPacketHandled(true);
    }
}
