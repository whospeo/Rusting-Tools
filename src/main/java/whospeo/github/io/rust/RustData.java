package whospeo.github.io.rust;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record RustData(int stage, int tickUntilNextStage) {

    public static final int MAX_STAGE = 9;

    public static final int BASE_TICK = 20 * 60;

    public static final Codec<RustData> CODEC = RecordCodecBuilder.create(rustDataInstance ->
            rustDataInstance.group(Codec.intRange(0, MAX_STAGE).fieldOf("stage").forGetter(RustData::stage),
                    Codec.INT.fieldOf("ticks_until_next_stage").forGetter(RustData::tickUntilNextStage)).apply(rustDataInstance, RustData::new));

    public static RustData initial() {
        return new RustData(0, BASE_TICK);
    }

    public boolean isMaxStage() {
        return stage >= MAX_STAGE;
    }

    public RustData advanceStage(int nextTimerTicks) {
        if(isMaxStage()) {
            return new RustData(MAX_STAGE, nextTimerTicks);
        }
        return new RustData(stage + 1, nextTimerTicks);
    }

    public RustData tick(int amount) {
        return new RustData(stage, Math.max(0, tickUntilNextStage - amount));
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
