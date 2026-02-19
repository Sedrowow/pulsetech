package dev.hyperlynx.pulsetech.client.debugger;

import dev.hyperlynx.pulsetech.feature.debugger.DebuggerInfoRequestMessage;
import dev.hyperlynx.pulsetech.network.ModMessages;
import net.minecraftforge.network.PacketDistributor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.PacketDistributor;

public abstract class DebuggerPage implements Renderable {
    protected final int id;
    protected final BlockPos pos;
    protected final String title;
    protected final int x;
    protected final int y;

    public DebuggerPage(BlockPos pos, int id, String title, int x, int y) {
        this.id = id;
        this.pos = pos;
        this.x = x;
        this.y = y;
        this.title = title;
    }

    public void update() {
        ModMessages.CHANNEL.send(PacketDistributor.SERVER.noArg(), new DebuggerInfoRequestMessage(pos, id));
    }

    public abstract void acceptInfo(Object info);
}
