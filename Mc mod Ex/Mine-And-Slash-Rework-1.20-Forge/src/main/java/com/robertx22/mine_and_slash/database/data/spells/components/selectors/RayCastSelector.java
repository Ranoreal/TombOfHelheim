package com.robertx22.mine_and_slash.database.data.spells.components.selectors;

import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.LookUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.List;

import static com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField.*;

public class RayCastSelector extends BaseTargetSelector {

    public RayCastSelector() {
        super(Arrays.asList(DISTANCE, WIDTH, ENTITY_PREDICATE));
    }

    @Override
    public List<LivingEntity> get(SpellCtx ctx, LivingEntity caster, LivingEntity target, Vec3 pos, MapHolder data) {
        AllyOrEnemy predicate = data.getEntityPredicate();
        float distance = data.get(DISTANCE)
                .floatValue();

       
        List<LivingEntity> list = LookUtils.getLivingEntityLookedAt(caster, distance, false);
        list = predicate.getMatchingEntities(list, caster);
        return list;

    }

    public MapHolder create(Double distance, AllyOrEnemy pred) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(DISTANCE, distance);
        d.put(ENTITY_PREDICATE, pred.name());
        validate(d);
        return d;
    }

    @Override
    public String GUID() {
        return "in_view";
    }
}
