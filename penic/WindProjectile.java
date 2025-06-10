package com.example.penic;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class WindProjectile extends ProjectileEntity {
    public WindProjectile(EntityType<?> type, World world) {
        super(type, world);
    }

    protected void onEntityHit(EntityHitResult result) {
        super.onEntityHit(result);
        Entity target = result.getEntity();
        if (target instanceof LivingEntity) {
            target.damage(getDamageSources().magic(), 5.0f);
            target.setVelocity(getVelocity().multiply(2.0));
        }
    }

    protected void onCollision(HitResult result) {
        super.onCollision(result);
        if (!world.isClient) {
            world.sendEntityStatus(this, (byte)3);
            discard();
        }
    }
}
