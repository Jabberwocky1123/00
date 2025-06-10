package com.example.penic;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import com.example.penic.conflict.*;
import java.util.Iterator;
import java.util.Map;

public class ServerTickHandler {
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            for (ServerWorld world : MinecraftServer.getServer().getWorlds()) {
                Iterator<Map.Entry<BlockPos, Long>> it = GuardianStepManager.getStepsForWorld(world).entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<BlockPos, Long> entry = it.next();
                    // Автоматическое удаление старых следов
                    if (world.getTime() - entry.getValue() > 20 * 35) {
                        world.breakBlock(entry.getKey(), false);
                        it.remove();
                    }
                }
            }
        }
    }
}
