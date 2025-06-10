package com.example.penic.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

    @Mixin(LivingEntity.class)
    public abstract class EntityAttackMixin {
        @Inject(method = "onDeath", at = @At("HEAD"))
        private void onDeath(DamageSource source, CallbackInfo ci) {
            // Обработка вампиризма и мудрости
        }
    }

