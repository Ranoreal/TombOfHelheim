package com.robertx22.mine_and_slash.database.data.spells.components.actions;

import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellUtils;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class SummonAtTargetFeet extends SpellAction {

    public SummonAtTargetFeet() {
        super(Arrays.asList(MapField.ENTITY_NAME, MapField.PROJECTILE_ENTITY, MapField.LIFESPAN_TICKS, MapField.HEIGHT));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        Optional<EntityType<?>> projectile = EntityType.byString(data.get(MapField.PROJECTILE_ENTITY));

        Double height = data.getOrDefault(MapField.HEIGHT, 0D);

        targets.forEach(x -> {
            Vec3 pos = x.position();
            Entity en = projectile.get()
                    .create(ctx.world);
            SpellUtils.initSpellEntity(en, ctx.caster, ctx.calculatedSpellData, data);
            en.setPos(pos.x, pos.y + height, pos.z);
            ctx.caster.level().addFreshEntity(en);
        });

    }

    public MapHolder create(Item item, EntityType type, Double lifespan) {
        MapHolder c = new MapHolder();
        c.put(MapField.PROJECTILE_COUNT, 1D);
        c.put(MapField.ENTITY_NAME, Spell.DEFAULT_EN_NAME);
        c.put(MapField.PROJECTILE_SPEED, 0D);
        c.put(MapField.LIFESPAN_TICKS, lifespan);
        c.put(MapField.ITEM, VanillaUTIL.REGISTRY.items().getKey(item)
                .toString());
        c.put(MapField.GRAVITY, false);
        c.put(MapField.PROJECTILE_ENTITY, EntityType.getKey(type)
                .toString());
        c.type = GUID();
        return c;
    }

    @Override
    public String GUID() {
        return "summon_at_target_feet";
    }

}
