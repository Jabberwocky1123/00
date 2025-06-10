package com.example.penic.enchants;

import com.example.penic.CustomEnchantment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class TreeFellerEnchantment extends CustomEnchantment {
    @Override public int getMaxLevel() { return 1; }

    @Inject(method = "postMine", at = @At("HEAD"))
    private void onBlockBreak(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
        if (miner instanceof PlayerEntity && state.isIn(BlockTags.LOGS)) {
            breakTree(world, pos, (PlayerEntity) miner, stack);
        }
    }

    private void breakTree(World world, BlockPos start, PlayerEntity player, ItemStack tool) {
        Set<BlockPos> logs = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            for (Direction dir : Direction.values()) {
                BlockPos neighbor = current.offset(dir);
                if (!logs.contains(neighbor) && world.getBlockState(neighbor).isIn(BlockTags.LOGS)) {
                    logs.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        for (BlockPos log : logs) {
            world.breakBlock(log, true, player);
            tool.damage(1, player, p -> p.sendToolBreakStatus(Hand.MAIN_HAND));
        }
    }
}
