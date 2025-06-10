package com.example.penic.enchants;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.example.penic.EnchantmentInitializer;

@Mixin(PlayerEntity.class)
public abstract class PlayerMovementMixin {
    @Unique
    private BlockPos lastStepPos = BlockPos.ORIGIN;

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void onMovementTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        World world = player.getWorld();
        ItemStack leggings = player.getInventory().getArmorStack(1);

        // Проверяем наличие зачарования
        if (EnchantmentHelper.getLevel(EnchantmentInitializer.GUARDIAN_STEP, leggings) > 0) {
            BlockPos currentPos = player.getBlockPos().down();

            // Создаем следы только при движении и на подходящих блоках
            if (!currentPos.equals(lastStepPos) && isValidSurface(world, currentPos)) {
                createFootprint(world, currentPos, player);
                lastStepPos = currentPos;
            }
        }
    }

    @Unique
    private boolean isValidSurface(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.isIn(BlockTags.SAND)  state.isOf(Blocks.DIRT)  state.isOf(Blocks.MUD);
    }

    @Unique
    private void createFootprint(World world, BlockPos pos, PlayerEntity player) {
        // Проверяем, что позиция свободна
        if (world.isAir(pos) || world.getBlockState(pos).getBlock() instanceof GuardianStepBlock) {
            world.setBlockState(pos, ModBlocks.GUARDIAN_STEP.getDefaultState());

            // Звук шагов
            world.playSound(null, pos, SoundEvents.BLOCK_SAND_PLACE,
                    SoundCategory.BLOCKS, 0.3f, 1.2f);

            // Частицы при создании
            for (int i = 0; i < 5; i++) {
                world.addParticle(ParticleTypes.SOUL,
                        pos.getX() + world.random.nextDouble(),
                        pos.getY() + 0.1,
                        pos.getZ() + world.random.nextDouble(),
                        0, 0.05, 0);
            }
        }
    }
}
