package com.example.penic.enchants;

import com.example.penic.CustomEnchantment;
import com.example.penic.WindProjectile;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class WindStrikeEnchantment extends CustomEnchantment {
    @Override public int getMaxLevel() { return 3; }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void onUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = user.getStackInHand(hand);
        int level = EnchantmentHelper.getLevel(this, stack);
        if (level > 0 && !world.isClient) {
            WindProjectile projectile = new WindProjectile(world, user);
            projectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
            world.spawnEntity(projectile);
            stack.damage(10, user, p -> p.sendToolBreakStatus(hand));
            user.getItemCooldownManager().set(stack.getItem(), 20 * (5 - level));
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
