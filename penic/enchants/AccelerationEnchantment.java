package com.example.penic.enchants;

import com.example.penic.CustomEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class AccelerationEnchantmentMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onPlayerTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.isFallFlying()) {
            // Получаем нагрудник (слот для нагрудника)
            ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);

            // Получаем уровень зачарования ускорения
            int level = EnchantmentHelper.getLevel(CustomEnchantment.ACCELERATION, chest);

            if (level > 0) {
                Vec3d rotation = player.getRotationVector();
                Vec3d velocity = player.getVelocity();
                double boost = 0.1 * level;

                // Рассчитываем новую скорость
                double newX = velocity.x + rotation.x * boost;
                double newY = velocity.y + rotation.y * boost;
                double newZ = velocity.z + rotation.z * boost;

                // Устанавливаем новую скорость с ограничением
                player.setVelocity(
                        Math.min(newX, 3.0),
                        Math.min(newY, 3.0),
                        Math.min(newZ, 3.0)
                );

                // Обновляем скорость полета
                player.velocityModified = true;
            }
        }
    }
}