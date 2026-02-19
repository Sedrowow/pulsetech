package dev.hyperlynx.pulsetech.feature.debugger.infotype;

import dev.hyperlynx.pulsetech.client.ClientWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class DebuggerPosInfoMessage {
    private final BlockPos pos;

    public DebuggerPosInfoMessage(BlockPos pos) {
        this.pos = pos;
    }

    public DebuggerPosInfoMessage(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(DebuggerPosInfoMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientWrapper.acceptDebuggerPosInfo(new DebuggerPosInfo(msg.pos)));
        ctx.get().setPacketHandled(true);
    }
}
