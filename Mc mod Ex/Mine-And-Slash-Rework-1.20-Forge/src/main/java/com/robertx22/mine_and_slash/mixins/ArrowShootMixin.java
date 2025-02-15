package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.mixin_ducks.ProjectileEntityDuck;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public class ArrowShootMixin implements ProjectileEntityDuck {

    public float dmg$multi = 0;

    @Inject(method = "shootFromRotation", at = @At("HEAD"))
    public void myOnShoot(Entity user, float pitch, float yaw, float roll, float modifierZ, float modifierXYZ, CallbackInfo ci) {
        Projectile arrow = (Projectile) (Object) this;
        dmg$multi = Mth.clamp(modifierZ / 3F, 0, 1); // by default it's multiplied by 3 so i need to divide it
    }

    @Override
    public float my$getDmgMulti() {
        return dmg$multi;
    }
}


