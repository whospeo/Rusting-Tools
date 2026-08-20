package whospeo.github.io.rust;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import whospeo.github.io.component.ModComponents;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class RustTickHandler {
    private RustTickHandler() {}

    private static final Map<UUID, Integer> LIVE_COUNTDOWN = new HashMap<>();

    private static int currentBaseTicks(ServerPlayer player) {
        Level level = player.level();
        BlockPos pos = player.blockPosition();

        if (isSheltered(level, pos)) {
            return RustData.BASE_TICK;
        }

        if (level.isThundering() && level.canSeeSky(pos)) {
            return 20 * 30;
        }

        if (level.isRaining() && level.canSeeSky(pos)) {
            return 20 * 45;
        }

        return RustData.BASE_TICK;
    }

    private static boolean isSheltered(Level level, BlockPos playerPos) {
        BlockPos head = playerPos.above();
        int maxScanHeight = 32;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos columnBase = head.offset(dx, 0, dz);
                boolean foundValidCover = false;

                for (int dy = 1; dy <= maxScanHeight; dy++) {
                    BlockPos check = columnBase.above(dy);
                    var state = level.getBlockState(check);
                    if (state.isAir()) continue;

                    boolean isLeaves = state.is(BlockTags.LEAVES);
                    boolean isSolid = !state.getCollisionShape(level, check).isEmpty();

                    foundValidCover = isSolid && !isLeaves;
                    break;
                }

                if (!foundValidCover) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isTrackedTool(ItemStack stack) {
        return stack.is(ItemTags.PICKAXES)
                || stack.is(ItemTags.AXES)
                || stack.is(ItemTags.SHOVELS)
                || stack.is(ItemTags.HOES)
                || stack.is(ItemTags.SWORDS);
    }

    public static void tickPlayer(ServerPlayer player) {
        int baseTicks = -1;

        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (!isTrackedTool(stack)) continue;

            RustData data = stack.getOrDefault(ModComponents.RUST_DATA, RustData.initial());

            if (!stack.has(ModComponents.RUST_DATA)){
                stack.set(ModComponents.RUST_DATA, data);
            }

            int live = LIVE_COUNTDOWN.getOrDefault(data.id(), data.tickUntilNextStage());
            live -= 1;

            if (live <= 0) {
                int newBaseTicks = currentBaseTicks(player);
                RustData updated = data.advanceStage(newBaseTicks);
                LIVE_COUNTDOWN.put(data.id(), updated.tickUntilNextStage());
                stack.set(ModComponents.RUST_DATA, updated);
            }

            else {
                LIVE_COUNTDOWN.put(data.id(), live);
            }
        }
    }

    public static void tickServer(MinecraftServer server) {
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            tickPlayer(player);
        }
    }
}
