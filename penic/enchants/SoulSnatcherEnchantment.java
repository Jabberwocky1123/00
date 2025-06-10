package com.example.penic.enchants;

import com.example.penic.CustomEnchantment;
import com.example.penic.mixins.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.example.penic.EnchantmentInitializer;

public class SoulSnatcherEnchantment extends CustomEnchantment {
    @Override public int getMaxLevel() { return 3; }
    @Override public boolean conflictsWith(Enchantment other) {
        return other == EnchantmentInitializer.PHOTOSYNTHESIS;
    }

    @Inject(method = "onKilled", at = @At("HEAD"))
    private void onKill(LivingEntity target, CallbackInfo ci) {
        if (target.getAttacker() instanceof PlayerEntity player) {
            ItemStack helmet = player.getInventory().getArmorStack(3);
            int level = EnchantmentHelper.getLevel(this, helmet);
            if (level > 0 && target instanceof Monster) {
                if (player.getRandom().nextFloat() < (0.15f + level * 0.1f)) {
                    target.dropItem(ModItems.SOUL_SHARD);

                    // Анимация частиц
                    for (int i = 0; i < 10; i++) {
                        world.addParticle(ParticleTypes.SOUL,
                                target.getX(), target.getY() + 0.5, target.getZ(),
                                0, 0.1, 0);
                    }
                }
            }
        }
    }
}
