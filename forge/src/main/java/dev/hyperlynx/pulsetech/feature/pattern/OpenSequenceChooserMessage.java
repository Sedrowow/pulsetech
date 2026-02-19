package dev.hyperlynx.pulsetech.feature.pattern;

import dev.hyperlynx.pulsetech.client.ClientWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class OpenSequenceChooserMessage {
    private final BlockPos pos;

    public OpenSequenceChooserMessage(BlockPos pos) {
        this.pos = pos;
    }

    public OpenSequenceChooserMessage(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(OpenSequenceChooserMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientWrapper.openSequenceScreen(msg.pos));
        ctx.get().setPacketHandled(true);
    }
}
