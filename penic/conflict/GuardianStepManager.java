package com.example.penic.conflict;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuardianStepManager {
    private static final Map<BlockPos, Long> activeSteps = new ConcurrentHashMap<>();

    public static void addStep(BlockPos pos, World world) {
        activeSteps.put(pos, world.getTime());
        scheduleRemoval(pos, world);
    }

    private static void scheduleRemoval(BlockPos pos, World world) {
        world.createAndScheduleBlockTick(pos, ModBlocks.GUARDIAN_STEP,
                (20 * 30) + world.random.nextInt(200)); // 30-35 секунд
    }

    public static void onBlockRemoved(BlockPos pos) {
        activeSteps.remove(pos);
    }

    @Environment(EnvType.CLIENT)
    public static void renderFootprints(MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        long currentTime = MinecraftClient.getInstance().world.getTime();

        for (Map.Entry<BlockPos, Long> entry : activeSteps.entrySet()) {
            float age = (currentTime - entry.getValue()) / 600f; // 0-1 за 30 сек
            if (age < 1) {
                renderFootprint(matrices, vertexConsumers, entry.getKey(), 1 - age);
            }
        }
    }

    // Метод для визуализации следов (опционально)
    private static void renderFootprint(MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                        BlockPos pos, float alpha) {
        // ... кастомный рендеринг
    }
}
