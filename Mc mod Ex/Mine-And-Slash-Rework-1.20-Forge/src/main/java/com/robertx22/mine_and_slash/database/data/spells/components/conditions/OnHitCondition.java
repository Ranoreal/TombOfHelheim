package com.robertx22.mine_and_slash.database.data.spells.components.conditions;

import com.robertx22.mine_and_slash.database.data.spells.components.EntityActivation;
import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;

import java.util.Arrays;

public class OnHitCondition extends EffectCondition {

    public OnHitCondition() {
        super(Arrays.asList());
    }

    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {
        return ctx.activation == EntityActivation.ON_HIT;
    }

    public MapHolder create() {
        MapHolder d = new MapHolder();
        d.type = GUID();
        return d;
    }

    @Override
    public String GUID() {
        return "on_hit";
    }
}

