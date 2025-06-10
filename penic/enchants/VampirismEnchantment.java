package com.example.penic.enchants;

import com.example.penic.CustomEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.example.penic.EnchantmentInitializer;

public class VampirismEnchantment extends CustomEnchantment {
    @Override public int getMaxLevel() { return 3; }
    @Override public boolean conflictsWith(Enchantment other) {
        return other == EnchantmentInitializer.DESICCANT_CURSE;
    }

    @Inject(method = "onKilled", at = @At("HEAD"))
    private void onKill(LivingEntity target, CallbackInfo ci) {
        if (target.getAttacker() instanceof PlayerEntity player) {
            player.heal(0.5f * EnchantmentHelper.getLevel(this, player.getMainHandStack()));
        }
    }
}
