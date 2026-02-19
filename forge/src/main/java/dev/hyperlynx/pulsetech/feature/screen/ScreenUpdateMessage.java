package dev.hyperlynx.pulsetech.feature.screen;

import dev.hyperlynx.pulsetech.client.ClientWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class ScreenUpdateMessage {
    private final ScreenData data;
    private final BlockPos pos;

    public ScreenUpdateMessage(ScreenData data, BlockPos pos) {
        this.data = data;
        this.pos = pos;
    }

    public ScreenUpdateMessage(FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        this.data = ScreenData.CODEC.decode(NbtOps.INSTANCE, tag).getOrThrow(false, e -> {}).getFirst();
        this.pos = buf.readBlockPos();
    }

    public void encode(FriendlyByteBuf buf) {
        CompoundTag tag = (CompoundTag) ScreenData.CODEC.encodeStart(NbtOps.INSTANCE, data).getOrThrow(false, e -> {});
        buf.writeNbt(tag);
        buf.writeBlockPos(pos);
    }

    public static void handle(ScreenUpdateMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientWrapper.updateScreenBlock(msg.data, msg.pos));
        ctx.get().setPacketHandled(true);
    }
}
