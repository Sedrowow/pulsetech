package dev.hyperlynx.pulsetech.feature.console;

import dev.hyperlynx.pulsetech.Pulsetech;
import dev.hyperlynx.pulsetech.client.ClientWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class ConsoleLineMessage {
    private final BlockPos pos;
    private final String line;

    public ConsoleLineMessage(BlockPos pos, String line) {
        this.pos = pos;
        this.line = line;
    }

    public ConsoleLineMessage(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.line = buf.readUtf();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeUtf(line);
    }

    public static void handle(ConsoleLineMessage msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (context.getDirection().getReceptionSide().isServer()) {
            context.enqueueWork(() -> {
                ServerPlayer player = context.getSender();
                if (player == null) return;
                if (!(player.level().getBlockEntity(msg.pos) instanceof ConsoleBlockEntity console)) {
                    Pulsetech.LOGGER.error("Received command {} for nonexistent console at {}", msg.line, msg.pos);
                    return;
                }
                if (!player.level().isLoaded(msg.pos)) return;
                console.processLine(msg.line, player);
            });
        } else {
            context.enqueueWork(() -> ClientWrapper.acceptConsoleLine(msg.pos, msg.line));
        }
        context.setPacketHandled(true);
    }
}
