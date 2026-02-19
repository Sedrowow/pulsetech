package dev.hyperlynx.pulsetech.feature.debugger;

import dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerInfoType;
import dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerInfoTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/// Holds information about which DebuggerInfoTypes a DebuggerInfoSource provides.
public record DebuggerInfoManifest(List<Entry> entries, BlockPos pos) {

    public static void writeToBuf(FriendlyByteBuf buf, DebuggerInfoManifest manifest) {
        buf.writeBlockPos(manifest.pos());
        buf.writeVarInt(manifest.entries().size());
        for (Entry entry : manifest.entries()) {
            buf.writeUtf(entry.title());
            buf.writeResourceLocation(DebuggerInfoTypes.REGISTRY.getKey(entry.type()));
        }
    }

    public static DebuggerInfoManifest readFromBuf(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        int size = buf.readVarInt();
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String title = buf.readUtf();
            var rl = buf.readResourceLocation();
            DebuggerInfoType type = DebuggerInfoTypes.REGISTRY.getValue(rl);
            if (type == null) type = DebuggerInfoTypes.REGISTRY.getValue(DebuggerInfoTypes.REGISTRY.getDefaultKey());
            entries.add(new Entry(title, type));
        }
        return new DebuggerInfoManifest(entries, pos);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (Entry entry : entries) {
            builder.append(i).append(" = ").append(entry.title()).append(": ").append(DebuggerInfoTypes.REGISTRY.getKey(entry.type())).append("; ");
            i++;
        }
        return builder.toString();
    }

    public DebuggerInfoManifest append(Entry additional_entry) {
        ArrayList<Entry> mutable_entries = new ArrayList<>(entries);
        mutable_entries.add(0, additional_entry);
        return new DebuggerInfoManifest(mutable_entries, pos);
    }

    public static class Entry {
        private String title;
        private DebuggerInfoType type;
        private Supplier<Object> getter;

        public Entry(String title, DebuggerInfoType type) {
            this(title, type, () -> {
                throw new IllegalStateException("Cannot get payload from the client side!");
            });
        }

        public Entry(String title, DebuggerInfoType type, Supplier<Object> getter) {
            this.title = title;
            this.type = type;
            this.getter = getter;
        }

        public String title() {
            return title;
        }

        public DebuggerInfoType type() {
            return type;
        }

        public Supplier<Object> supplier() {
            return getter;
        }
    }
}
