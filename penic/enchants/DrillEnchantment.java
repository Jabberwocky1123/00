package com.example.penic.enchants;

import com.example.penic.CustomEnchantment;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class DrillEnchantment extends CustomEnchantment {
    @Override public int getMaxLevel() { return 3; }

    @Inject(method = "postMine", at = @At("HEAD"))
    private void onBlockBreak(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
        int level = EnchantmentHelper.getLevel(this, stack);
        if (miner instanceof PlayerEntity) {
            breakBlocksInRadius(world, pos, level, (PlayerEntity) miner);
        }
    }

    private void breakBlocksInRadius(World world, BlockPos center, int radius, PlayerEntity player) {
        // Логика разрушения блоков в радиусе
    }
}
