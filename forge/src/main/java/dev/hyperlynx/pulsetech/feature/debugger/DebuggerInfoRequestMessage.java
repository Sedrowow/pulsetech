package dev.hyperlynx.pulsetech.feature.debugger;

import dev.hyperlynx.pulsetech.network.ModMessages;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import java.util.function.Supplier;

public class DebuggerInfoRequestMessage {
    private final BlockPos pos;
    private final int index;

    public DebuggerInfoRequestMessage(BlockPos pos, int index) {
        this.pos = pos;
        this.index = index;
    }

    public DebuggerInfoRequestMessage(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.index = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(index);
    }

    public static void handle(DebuggerInfoRequestMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var sender = ctx.get().getSender();
            if (sender == null) return;
            if (!sender.level().isLoaded(msg.pos)) return;
            if (!(sender.level().getBlockEntity(msg.pos) instanceof DebuggerInfoSource source)) return;
            DebuggerInfoManifest manifest = source.getDebuggerInfoManifest();
            if (msg.index >= 0 && msg.index < manifest.entries().size()) {
                var entry = manifest.entries().get(msg.index);
                Object result = entry.supplier().get();
                ModMessages.CHANNEL.send(PacketDistributor.PLAYER.with(() -> sender), result);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
