package dev.hyperlynx.pulsetech.feature.number;

import dev.hyperlynx.pulsetech.feature.number.block.NumberEmitterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class NumberSelectMessage {
    private final BlockPos pos;
    private final byte number;

    public NumberSelectMessage(BlockPos pos, byte number) {
        this.pos = pos;
        this.number = number;
    }

    public NumberSelectMessage(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.number = buf.readByte();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeByte(number);
    }

    public static void handle(NumberSelectMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var sender = ctx.get().getSender();
            if (sender == null) return;
            if (sender.level().getBlockEntity(msg.pos) instanceof NumberEmitterBlockEntity emitter) {
                emitter.setNumber(msg.number);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
