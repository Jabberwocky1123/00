package com.example.penic.enchants;

import com.example.penic.CustomEnchantment;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class PhotosynthesisEnchantment extends CustomEnchantment {
    @Override public int getMaxLevel() { return 1; }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onPlayerTick(PlayerEntity player, CallbackInfo ci) {
        if (player.world.isDay() && player.world.isSkyVisible(player.getBlockPos())) {
            player.getHungerManager().add(1, 0.0f); // Восстановление голода
        }
    }
}
