package com.example.penic.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.example.penic.EnchantmentInitializer;

@Mixin(PlayerEntity.class)
public abstract class PlayerMovementMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        BlockPos pos = player.getBlockPos();
        BlockState state = player.world.getBlockState(pos);
        ItemStack boots = player.getInventory().getArmorStack(0);
        int level = EnchantmentHelper.getLevel(EnchantmentInitializer.LAVAWALKER, boots);

        if (level > 0 && state.isOf(Blocks.LAVA) && player.getFluidHeight(FluidTags.LAVA) > 0) {
            // Создаем временные блоки магмы
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos lavaPos = pos.add(x, -1, z);
                    if (player.world.getBlockState(lavaPos).isOf(Blocks.LAVA)) {
                        player.world.setBlockState(lavaPos, Blocks.MAGMA_BLOCK.getDefaultState());

                        // Удаление через 2 секунды
                        player.world.createAndSchedule(lavaPos, 40, () -> {
                            if (player.world.getBlockState(lavaPos).getBlock() == Blocks.MAGMA_BLOCK) {
                                player.world.setBlockState(lavaPos, Blocks.LAVA.getDefaultState());
                            }
                        });
                    }
                }
            }
        }
    }
}
