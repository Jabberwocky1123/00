package com.example.penic.mixins;

import com.example.penic.conflict.GuardianStepManager;

public class ModEvents {
    public static void register() {
        BlockBreakCallback.EVENT.register((world, pos, state, player) -> {
            if (state.getBlock() == ModBlocks.GUARDIAN_STEP) {
                GuardianStepManager.onBlockRemoved(pos);
            }
            return true;
        });
    }
}
