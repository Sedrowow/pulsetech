package dev.hyperlynx.pulsetech.core.protocol;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.hyperlynx.pulsetech.core.Sequence;
import dev.hyperlynx.pulsetech.feature.processor.MacroProtocolCommand;
import dev.hyperlynx.pulsetech.util.MapListPairConverter;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.*;

/// Contains a set of associations between {@link Sequence}s and {@link ProtocolCommand}s.
public class Protocol {
    private final int sequence_length;
    private final BiMap<ProtocolCommand, Sequence> commands;

    private static final MapListPairConverter<ProtocolCommand, Sequence> converter = new MapListPairConverter<>();

    // Lazy codec that accesses REGISTRY only when encoding/decoding (after registry is populated)
    private static final Codec<ProtocolCommand> REGISTRY_COMMAND_CODEC = new Codec<ProtocolCommand>() {
        @Override
        public <T> DataResult<Pair<ProtocolCommand, T>> decode(DynamicOps<T> ops, T input) {
            return ResourceLocation.CODEC.decode(ops, input).flatMap(pair -> {
                if (ProtocolCommands.REGISTRY == null) return DataResult.error(() -> "Registry not yet initialized");
                ProtocolCommand cmd = ProtocolCommands.REGISTRY.getValue(pair.getFirst());
                if (cmd != null) return DataResult.success(Pair.of(cmd, pair.getSecond()));
                return DataResult.error(() -> "Unknown protocol command: " + pair.getFirst());
            });
        }
        @Override
        public <T> DataResult<T> encode(ProtocolCommand input, DynamicOps<T> ops, T prefix) {
            if (ProtocolCommands.REGISTRY == null) return DataResult.error(() -> "Registry not yet initialized");
            ResourceLocation rl = ProtocolCommands.REGISTRY.getKey(input);
            if (rl != null) return ResourceLocation.CODEC.encode(rl, ops, prefix);
            return DataResult.error(() -> "No registry key for protocol command: " + input);
        }
    };

    public static final Codec<ProtocolCommand> COMMAND_CODEC = Codec.either(
            REGISTRY_COMMAND_CODEC,
            MacroProtocolCommand.CODEC
    ).xmap(either -> {
        if (either.left().isPresent()) return either.left().orElseThrow();
        return either.right().orElseThrow();
    }, command -> {
        if (command instanceof MacroProtocolCommand macro_command) return Either.right(macro_command);
        return Either.left(command);
    });

    public static final Codec<Protocol> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("sequence_length").forGetter(Protocol::sequenceLength),
                    Codec.pair(
                            COMMAND_CODEC.fieldOf("command").codec(),
                            Sequence.CODEC.fieldOf("sequence").codec()
                    ).listOf().xmap(
                            converter::toMap,
                            converter::fromMap)
                    .fieldOf("commands").forGetter(Protocol::getCommands)
            ).apply(instance, Protocol::new)
    );

    public Protocol(int sequence_length) {
        this.sequence_length = sequence_length;
        commands = HashBiMap.create();
    }

    public Protocol(int sequence_length, Map<ProtocolCommand, Sequence> existing) {
        this.sequence_length = sequence_length;
        commands = HashBiMap.create(existing);
    }

    public Map<ProtocolCommand, Sequence> getCommands() {
        return commands;
    }

    public int sequenceLength() {
        return sequence_length;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Protocol other) {
            return other.commands.equals(commands);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(commands, sequence_length);
    }

    public @Nullable ProtocolCommand getCommand(Sequence buffer) {
        return commands.inverse().get(buffer);
    }
}
