package com.robertx22.mine_and_slash.database.data.spells.components.actions;

import com.robertx22.mine_and_slash.database.data.spells.components.ComponentPart;
import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.world.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;

public class DoSpecificAction extends SpellAction {

    public DoSpecificAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        String action = data.get(MapField.SPECIFIC_ACTION);

        for (ComponentPart componentPart : ctx.calculatedSpellData.getSpell().attached.entity_components.get(action)) {
            componentPart.tryActivate(ctx);
        }

    }

    public MapHolder create(String action) {
        MapHolder c = new MapHolder();
        c.put(MapField.SPECIFIC_ACTION, action);
        c.type = GUID();
        this.validate(c);
        return c;
    }

    @Override
    public String GUID() {
        return "specific_action";
    }

}
