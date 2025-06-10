package com.example.penic.enchants;

import com.example.penic.CustomEnchantment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class LavawalkerEnchantment extends CustomEnchantment {
    @Override public int getMaxLevel() { return 3; }

    @Inject(method = "onEntityMove", at = @At("HEAD"))
    private void onMove(LivingEntity entity, BlockPos pos, CallbackInfo ci) {
        if (entity.world.getBlockState(pos.down()).isOf(Blocks.LAVA)) {
            // Логика создания временных блоков
            entity.world.setBlockState(pos, Blocks.MAGMA_BLOCK.getDefaultState());
        }
    }
}
