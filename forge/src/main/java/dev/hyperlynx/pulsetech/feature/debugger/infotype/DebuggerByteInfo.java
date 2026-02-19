package dev.hyperlynx.pulsetech.feature.debugger.infotype;

public record DebuggerByteInfo(byte number) {
    public Object toMessage() {
        return new dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerByteInfoMessage(number);
    }
}
