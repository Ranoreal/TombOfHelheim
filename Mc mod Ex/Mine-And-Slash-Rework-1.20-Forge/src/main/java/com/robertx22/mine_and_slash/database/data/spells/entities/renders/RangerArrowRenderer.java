package com.robertx22.mine_and_slash.database.data.spells.entities.renders;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class RangerArrowRenderer<T extends AbstractArrow> extends ArrowRenderer<T> {
    public static final ResourceLocation RES_ARROW = new ResourceLocation("textures/entity/projectiles/arrow.png");

    public RangerArrowRenderer(EntityRendererProvider.Context manager) {
        super(manager);
    }


    @Override
    public ResourceLocation getTextureLocation(T en) {
        return RES_ARROW;
    }

}