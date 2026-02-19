package dev.hyperlynx.pulsetech.feature.debugger.infotype;

import net.minecraft.core.BlockPos;

public record DebuggerPosInfo(BlockPos pos) {
    public Object toMessage() {
        return new dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerPosInfoMessage(pos);
    }
}
