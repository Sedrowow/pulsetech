package dev.hyperlynx.pulsetech.feature.debugger;

import dev.hyperlynx.pulsetech.client.ClientWrapper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class DebuggerInfoManifestMessage {
    private final DebuggerInfoManifest manifest;

    public DebuggerInfoManifestMessage(DebuggerInfoManifest manifest) {
        this.manifest = manifest;
    }

    public DebuggerInfoManifestMessage(FriendlyByteBuf buf) {
        this.manifest = DebuggerInfoManifest.readFromBuf(buf);
    }

    public void encode(FriendlyByteBuf buf) {
        DebuggerInfoManifest.writeToBuf(buf, manifest);
    }

    public static void handle(DebuggerInfoManifestMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientWrapper.openDebuggerScreen(msg.manifest));
        ctx.get().setPacketHandled(true);
    }
}
