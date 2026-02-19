package dev.hyperlynx.pulsetech.feature.pattern.block;

import dev.hyperlynx.pulsetech.core.Sequence;
import dev.hyperlynx.pulsetech.core.PatternHolder;
import dev.hyperlynx.pulsetech.core.PulseBlockEntity;
import net.minecraft.core.BlockPos;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class PatternBlockEntity extends PulseBlockEntity implements PatternHolder {
    protected Sequence trigger = new Sequence();

    public PatternBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public Sequence getPattern() {
        return trigger;
    }

    @Override
    public void setPattern(Sequence sequence) {
        this.trigger = sequence;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if(trigger.isEmpty()) {
            var attempt_encode = Sequence.CODEC.encodeStart(NbtOps.INSTANCE, trigger);
            if(attempt_encode.resultOrPartial(e -> {}).isPresent()) {
                tag.put("TriggerSequence", attempt_encode.getOrThrow(false, e -> {}));
            }
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag) {
        super.loadAdditional(tag);
        if(tag.contains("TriggerSequence")) {
            var attempt_decode = Sequence.CODEC.decode(NbtOps.INSTANCE, tag.get("TriggerSequence"));
            if(attempt_decode.resultOrPartial(e -> {}).isPresent()) {
                trigger = attempt_decode.getOrThrow(false, e -> {}).getFirst();
            }
        }
    }
}
