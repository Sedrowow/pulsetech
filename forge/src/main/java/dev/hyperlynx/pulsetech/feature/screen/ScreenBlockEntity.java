package dev.hyperlynx.pulsetech.feature.screen;

import dev.hyperlynx.pulsetech.Pulsetech;
import dev.hyperlynx.pulsetech.core.protocol.ProtocolBlockEntity;
import dev.hyperlynx.pulsetech.registration.ModBlockEntityTypes;
import dev.hyperlynx.pulsetech.util.Color;
import net.minecraft.core.BlockPos;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import dev.hyperlynx.pulsetech.feature.screen.ScreenUpdateMessage;
import dev.hyperlynx.pulsetech.network.ModMessages;
import net.minecraftforge.network.PacketDistributor;

import java.util.Objects;

public class ScreenBlockEntity extends ProtocolBlockEntity {
    private ScreenData data = ScreenData.blank();
    private Color pen_color = Color.white();

    public ScreenBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.SCREEN.get(), pos, blockState);
    }

    /// Only used on the Server to send screen updates to clients, for example when a pulse code causes a change
    public void sendUpdate() {
        assert level != null;
        if (level.isClientSide()) {
            Pulsetech.LOGGER.error("Tried to send screen update from client! Ignoring.");
            return;
        }
        ModMessages.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> new ChunkPos(getBlockPos())), new ScreenUpdateMessage(data, getBlockPos()));
    }

    public void setScreenData(ScreenData screenData) {
        this.data = screenData;
    }

    public ScreenData getScreenData() {
        return data;
    }

    public void setBackgroundColor(Color bg_color) {
        this.data = data.withBackgroundColor(bg_color);
    }

    public void setPenColor(Color color) {
        this.pen_color = color;
    }

    public void drawPixel(byte x, byte y) {
        data.setPixel(pen_color, x, y);
    }

    public void drawBox(Byte x1, Byte y1, Byte x2, Byte y2) {
        for(int x = x1; x <= x2; x++) {
            for(int y = y1; y <= y2; y++) {
                data.setPixel(pen_color, x, y);
            }
        }
    }

    public void clearForeground() {
        data.clearForeground();
    }

    public void toggleForeground() {
        data = data.toggleForegroundVisible();
    }

    public boolean isNotBlank() {
        return !data.fg().isEmpty() || !Objects.equals(data.bg_color(), Color.black());
    }

    @Override
    protected void loadAdditional(CompoundTag tag) {
        super.loadAdditional(tag);
        data = ScreenData.CODEC.decode(NbtOps.INSTANCE, tag.get("ScreenData")).getOrThrow(false, e -> {}).getFirst();
        pen_color = Color.CODEC.decode(NbtOps.INSTANCE, tag.get("PenColor")).getOrThrow(false, e -> {}).getFirst();
    }
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("ScreenData", ScreenData.CODEC.encodeStart(NbtOps.INSTANCE, data).getOrThrow(false, e -> {}));
        tag.put("PenColor", Color.CODEC.encodeStart(NbtOps.INSTANCE, pen_color).getOrThrow(false, e -> {}));
    }
    // Create an update tag here. For block entities with only a few fields, this can just call #saveAdditional.

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    // Handle a received update tag here. The default implementation calls #loadAdditional here,

    // so you do not need to override this method if you don't plan to do anything beyond that.

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
    }
}
