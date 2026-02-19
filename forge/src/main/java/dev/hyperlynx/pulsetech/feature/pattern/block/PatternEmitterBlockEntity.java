package dev.hyperlynx.pulsetech.feature.pattern.block;

import dev.hyperlynx.pulsetech.core.Sequence;
import dev.hyperlynx.pulsetech.core.module.EmitterModule;
import dev.hyperlynx.pulsetech.feature.debugger.DebuggerInfoManifest;
import dev.hyperlynx.pulsetech.feature.debugger.DebuggerInfoSource;
import dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerInfoTypes;
import dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerSequenceInfo;
import dev.hyperlynx.pulsetech.registration.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Objects;

public class PatternEmitterBlockEntity extends PatternBlockEntity implements DebuggerInfoSource {
    private EmitterModule emitter = new EmitterModule();
    public PatternEmitterBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.PATTERN_EMITTER.get(), pos, blockState);
    }

    // Create an update tag here, like above.
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    // Return our packet here. This method returning a non-null result tells the game to use this packet for syncing.
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // The packet uses the CompoundTag returned by #getUpdateTag. An alternative overload of #create exists
        // that allows you to specify a custom update tag, including the ability to omit data the client might not need.
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public boolean isActive() {
        return emitter.isActive();
    }

    @Override
    public void setActive(boolean active) {
        emitter.setActive(active);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        assert level != null;
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_IMMEDIATE);
    }

    @Override
    public void tick() {
        if(!(getLevel() instanceof ServerLevel slevel)) {
            return;
        }
        if(emitter.isActive() && !emitter.outputInitialized()) {
            emitter.enqueueTransmission(Objects.requireNonNull(getPattern()));
        }
        emitter.tick(slevel, this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Emitter", EmitterModule.CODEC.encodeStart(NbtOps.INSTANCE, emitter).getOrThrow());
        tag.put("Pattern", Sequence.CODEC.encodeStart(NbtOps.INSTANCE, getPattern()).getOrThrow());
    }

    @Override
    protected void loadAdditional(CompoundTag tag) {
        super.loadAdditional(tag);
        EmitterModule.CODEC.decode(NbtOps.INSTANCE, tag.get("Emitter")).result().ifPresent(success -> emitter = success.getFirst());
        Sequence.CODEC.decode(NbtOps.INSTANCE, tag.get("Pattern")).result().ifPresent(success -> setPattern(success.getFirst()));
    }

    @Override
    public boolean isDelayed() {
        return emitter.getDelay() > 0;
    }

    @Override
    public DebuggerInfoManifest getDebuggerInfoManifest() {
        return new DebuggerInfoManifest(List.of(
                new DebuggerInfoManifest.Entry(
                        Component.translatable("debugger.pulsetech.output_buffer").getString(),
                        DebuggerInfoTypes.SEQUENCE.get(),
                        () -> new DebuggerSequenceInfo(emitter.getBuffer())),
                new DebuggerInfoManifest.Entry(
                        Component.translatable("debugger.pulsetech.pattern").getString(),
                        DebuggerInfoTypes.SEQUENCE.get(),
                        () -> new DebuggerSequenceInfo(getPattern()))
        ), getBlockPos());
    }
}
