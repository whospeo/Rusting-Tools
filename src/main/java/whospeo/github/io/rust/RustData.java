package whospeo.github.io.rust;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;

import java.util.UUID;

public record RustData(UUID id, int stage, int tickUntilNextStage) {

    public static final int MAX_STAGE = 9;
    public static final int BASE_TICK = 20 * 60;

    public static final Codec<RustData> CODEC = RecordCodecBuilder.create(rustDataInstance ->
            rustDataInstance.group(UUIDUtil.CODEC.fieldOf("id").forGetter(RustData::id),
                    Codec.intRange(0, MAX_STAGE).fieldOf("stage").forGetter(RustData::stage),
                    Codec.INT.fieldOf("ticks_until_next_stage").forGetter(RustData::tickUntilNextStage)
            ).apply(rustDataInstance, RustData::new));

    public static RustData initial() {
        return new RustData(UUID.randomUUID(), 0, BASE_TICK);
    }

    public boolean isMaxStage() {
        return stage >= MAX_STAGE;
    }

    public RustData advanceStage(int nextTimerTicks) {
        int newStage = isMaxStage() ? MAX_STAGE : stage + 1;
        return new RustData(id, newStage, nextTimerTicks);
    }

    public float miningSpeedMultiplier() {
        if (stage <= 4) return 1.0f;
        return switch (stage) {
            case 5, 6, 7, 8 -> 0.7f;
            default -> 0.4f;
        };
    }

    public float durabilityLossMultiplier() {
        double ratio = stage / (double) MAX_STAGE;
        return (float) (1.0 + ratio * ratio);
    }
}
