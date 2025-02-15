package com.robertx22.mine_and_slash.database.data.spells.components.actions;

import com.robertx22.mine_and_slash.database.data.exile_effects.ExileEffect;
import com.robertx22.mine_and_slash.database.data.spells.components.ComponentPart;
import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.tags.imp.EffectTag;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.world.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;

public class DoActionForEachEffectOnTarget extends SpellAction {

    public DoActionForEachEffectOnTarget() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        try {
            if (ctx.world.isClientSide) {
                return;
            }

            String action = data.get(MapField.SPECIFIC_ACTION);

            String tag = data.get(MapField.EFFECT_TAG);

            int amount = 0;

            for (LivingEntity x : targets) {

                for (String k : Load.Unit(x)
                        .getStatusEffectsData().exileMap.keySet()) {
                    ExileEffect eff = ExileDB.ExileEffects()
                            .get(k);
                    if (eff.hasTag(tag)) {
                        amount++;
                    }
                }
            }

            for (int i = 0; i < amount; i++) {
                for (ComponentPart componentPart : ctx.calculatedSpellData.getSpell().attached.entity_components.get(action)) {
                    componentPart.tryActivate(ctx);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public MapHolder create(String action, EffectTag tag) {
        MapHolder c = new MapHolder();
        c.put(MapField.SPECIFIC_ACTION, action);
        c.put(MapField.EFFECT_TAG, tag.GUID());
        c.type = GUID();
        this.validate(c);
        return c;
    }

    @Override
    public String GUID() {
        return "action_per_effect_with_tag_on_target";
    }

}
