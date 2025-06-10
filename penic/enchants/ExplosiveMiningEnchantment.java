package com.example.penic.enchants;

import com.example.penic.CustomEnchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.example.penic.EnchantmentInitializer;

public class ExplosiveMiningEnchantment extends CustomEnchantment {
    @Override public int getMaxLevel() { return 1; }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void onUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = user.getStackInHand(hand);
        if (stack.getEnchantments().contains(EnchantmentInitializer.EXPLOSIVE_MINING)) {
            // Логика взрыва
            world.createExplosion(null, user.getX(), user.getY(), user.getZ(), 3.0f, false);
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
