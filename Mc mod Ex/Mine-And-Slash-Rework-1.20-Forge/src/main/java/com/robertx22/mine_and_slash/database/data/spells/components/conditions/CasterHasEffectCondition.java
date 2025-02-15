package com.robertx22.mine_and_slash.database.data.spells.components.conditions;

import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import java.util.Arrays;

public class CasterHasEffectCondition extends EffectCondition {

    public CasterHasEffectCondition() {
        super(Arrays.asList(MapField.POTION_ID));
    }


    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {
        MobEffect potion = BuiltInRegistries.MOB_EFFECT.get(new ResourceLocation(data.get(MapField.POTION_ID)));
        return ctx.caster.hasEffect(potion);
    }

    public MapHolder create(MobEffect effect) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(MapField.POTION_ID, BuiltInRegistries.MOB_EFFECT.getKey(effect)
                .toString());
        return d;
    }

    @Override
    public String GUID() {
        return "caster_has_potion";
    }
}
