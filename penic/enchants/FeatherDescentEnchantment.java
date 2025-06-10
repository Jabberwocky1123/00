package com.example.penic.enchants;

import com.example.penic.CustomEnchantment;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class FeatherDescentEnchantment extends CustomEnchantment {
    private final Long2LongMap cooldowns = new Long2LongOpenHashMap();

    @Override public int getMaxLevel() { return 1; }

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void onFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof PlayerEntity player) {
            long uuid = player.getUuid().getLeastSignificantBits();
            ItemStack legs = player.getInventory().getArmorStack(1);

            if (EnchantmentHelper.getLevel(this, legs) > 0 &&
                    player.fallDistance > 5 &&
                    (world.getTime() - cooldowns.getOrDefault(uuid, 0L)) > 600) {

                cooldowns.put(uuid, world.getTime());
                player.fallDistance = 0;

                // Эффект приземления
                for (int i = 0; i < 20; i++) {
                    world.addParticle(ParticleTypes.CLOUD,
                            player.getX(), player.getY(), player.getZ(),
                            0, 0.1, 0);
                }
                cir.setReturnValue(false);
            }
        }
    }
}
