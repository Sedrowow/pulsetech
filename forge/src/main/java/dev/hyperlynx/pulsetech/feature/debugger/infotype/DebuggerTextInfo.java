package dev.hyperlynx.pulsetech.feature.debugger.infotype;

public record DebuggerTextInfo(String text) {
    public Object toMessage() {
        return new dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerTextInfoMessage(text);
    }
}
