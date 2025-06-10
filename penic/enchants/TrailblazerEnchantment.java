package com.example.penic.enchants;

import com.example.penic.CustomEnchantment;
import com.example.penic.mixins.ModBlocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

public class TrailblazerEnchantment extends CustomEnchantment {
    private static final List<BlockPos> activeMarkers = new ArrayList<>();

    @Override public int getMaxLevel() { return 1; }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void onUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack boots = user.getInventory().getArmorStack(0);
        if (EnchantmentHelper.getLevel(this, boots) > 0) {
            BlockPos pos = user.getBlockPos().down();

            // Ограничение в 3 маркера
            if (activeMarkers.size() >= 3) {
                world.breakBlock(activeMarkers.remove(0), false);
            }

            world.setBlockState(pos, ModBlocks.GLOW_MARKER.getDefaultState());
            activeMarkers.add(pos);

            // Автоудаление через 5 минут
            world.createAndSchedule(pos, 6000, () -> {
                if (world.getBlockState(pos).getBlock() == ModBlocks.GLOW_MARKER) {
                    world.breakBlock(pos, false);
                    activeMarkers.remove(pos);
                }
            });

            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
