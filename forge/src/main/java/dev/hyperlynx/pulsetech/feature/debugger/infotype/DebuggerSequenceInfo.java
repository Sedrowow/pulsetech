package dev.hyperlynx.pulsetech.feature.debugger.infotype;

import dev.hyperlynx.pulsetech.core.Sequence;

public record DebuggerSequenceInfo(Sequence sequence) {
    public Object toMessage() {
        return new dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerSequenceInfoMessage(sequence);
    }
}
