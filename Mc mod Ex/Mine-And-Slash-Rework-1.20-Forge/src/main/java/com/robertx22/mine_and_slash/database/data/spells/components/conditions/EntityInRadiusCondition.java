package com.robertx22.mine_and_slash.database.data.spells.components.conditions;

import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityFinder;
import net.minecraft.world.entity.LivingEntity;

import java.util.Arrays;

import static com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField.*;

public class EntityInRadiusCondition extends EffectCondition {

    public EntityInRadiusCondition() {
        super(Arrays.asList(RADIUS, SELECTION_TYPE, ENTITY_PREDICATE));
    }

    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {
        AllyOrEnemy predicate = data.getEntityPredicate();
        Double radius = data.get(RADIUS);

        radius *= ctx.calculatedSpellData.data.getNumber(EventData.AREA_MULTI, 1).number;

        EntityFinder.Setup<LivingEntity> finder = EntityFinder.start(ctx.caster, LivingEntity.class, ctx.getBlockPos())
                .finder(EntityFinder.SelectionType.RADIUS)
                .searchFor(predicate)
                .height(data.getOrDefault(HEIGHT, radius))
                .radius(radius);

        return !finder.build()
                .isEmpty();
    }

    public MapHolder enemiesInRadius(Double radius) {
        return create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies);
    }

    public MapHolder alliesInRadius(Double radius) {
        return create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies);
    }

    public MapHolder create(Double radius, EntityFinder.SelectionType type, AllyOrEnemy pred) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(RADIUS, radius);
        d.put(SELECTION_TYPE, type.name());
        d.put(ENTITY_PREDICATE, pred.name());
        validate(d);
        return d;
    }

    @Override
    public String GUID() {
        return "is_en_in_radius";
    }

}

