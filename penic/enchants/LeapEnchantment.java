package com.example.penic.enchants;

import com.example.penic.CustomEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class LeapEnchantment extends CustomEnchantment {
    @Override public int getMaxLevel() { return 3; }

    @Inject(method = "jump", at = @At("HEAD"))
    private void onJump(LivingEntity entity, CallbackInfo ci) {
        if (entity instanceof PlayerEntity player) {
            ItemStack legs = player.getInventory().getArmorStack(1);
            int level = EnchantmentHelper.getLevel(this, legs);
            if (level > 0 && player.isOnGround()) {
                player.addVelocity(0, 0.15 * level, 0);
            }
        }
    }
}
