package com.example.penic.enchants;

import com.example.penic.CustomEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.example.penic.EnchantmentInitializer;

public class BeesFuryEnchantment extends CustomEnchantment {
    @Override public int getMaxLevel() { return 3; }
    @Override public boolean conflictsWith(Enchantment other) {
        return other == EnchantmentInitializer.IMMUNITY;
    }

    @Inject(method = "applyDamage", at = @At("HEAD"))
    private void onDamage(LivingEntity entity, DamageSource source, float amount, CallbackInfo ci) {
        if (source.getAttacker() != null && entity instanceof PlayerEntity player) {
            ItemStack chest = player.getInventory().getArmorStack(2);
            int level = EnchantmentHelper.getLevel(this, chest);
            if (level > 0 && player.getRandom().nextFloat() < (0.2f * level)) {
                World world = player.getWorld();
                int beeCount = 3 + world.random.nextInt(3);

                for (int i = 0; i < beeCount; i++) {
                    BeeEntity bee = new BeeEntity(EntityType.BEE, world);
                    bee.setPos(player.getX(), player.getY(), player.getZ());
                    bee.setAngryAt(source.getAttacker().getUuid());
                    bee.setAngerTime(600); // 30 секунд
                    world.spawnEntity(bee);
                }
            }
        }
    }
}
