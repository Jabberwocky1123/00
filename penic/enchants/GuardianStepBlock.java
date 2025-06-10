package com.example.penic.enchants;

import com.jcraft.jorbis.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import javax.swing.text.html.parser.Entity;

public class GuardianStepBlock extends Block {
    public GuardianStepBlock() {
        super(FabricBlockSettings.create()
                .strength(0.1f)
                .sounds(BlockSoundGroup.SAND)
                .nonOpaque()
                .ticksRandomly()
                .luminance(state -> 3));
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient && entity instanceof LivingEntity living) {
            // Наносим урон только враждебным мобам
            if (living instanceof HostileEntity) {
                living.damage(world.getDamageSources().cactus(), 1.0f);

                // Частицы крови
                for (int i = 0; i < 3; i++) {
                    world.addParticle(ParticleTypes.DAMAGE_INDICATOR,
                            living.getX(), living.getY() + 0.5, living.getZ(),
                            0, 0.1, 0);
                }
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // Автоматическое исчезновение через 20-30 секунд
        if (random.nextInt(100) == 0) {
            world.breakBlock(pos, false);
        }
    }
}
