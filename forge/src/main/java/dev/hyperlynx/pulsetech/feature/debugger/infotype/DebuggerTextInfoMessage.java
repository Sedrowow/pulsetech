package dev.hyperlynx.pulsetech.feature.debugger.infotype;

import dev.hyperlynx.pulsetech.client.ClientWrapper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class DebuggerTextInfoMessage {
    private final String text;

    public DebuggerTextInfoMessage(String text) {
        this.text = text;
    }

    public DebuggerTextInfoMessage(FriendlyByteBuf buf) {
        this.text = buf.readUtf();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(text);
    }

    public static void handle(DebuggerTextInfoMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientWrapper.acceptDebuggerTextInfo(new DebuggerTextInfo(msg.text)));
        ctx.get().setPacketHandled(true);
    }
}
