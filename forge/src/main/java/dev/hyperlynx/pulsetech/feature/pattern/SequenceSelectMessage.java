package dev.hyperlynx.pulsetech.feature.pattern;

import dev.hyperlynx.pulsetech.core.PatternHolder;
import dev.hyperlynx.pulsetech.core.Sequence;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class SequenceSelectMessage {
    private final BlockPos pos;
    private final Sequence sequence;

    public SequenceSelectMessage(BlockPos pos, Sequence sequence) {
        this.pos = pos;
        this.sequence = sequence;
    }

    public SequenceSelectMessage(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.sequence = Sequence.readFromBuf(buf);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        Sequence.writeToBuf(buf, sequence);
    }

    public static void handle(SequenceSelectMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var sender = ctx.get().getSender();
            if (sender == null) return;
            if (sender.level().getBlockEntity(msg.pos) instanceof PatternHolder holder) {
                holder.setPattern(msg.sequence);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
