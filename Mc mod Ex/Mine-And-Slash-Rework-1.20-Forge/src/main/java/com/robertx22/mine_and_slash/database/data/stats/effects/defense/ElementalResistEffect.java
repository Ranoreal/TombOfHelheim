package com.robertx22.mine_and_slash.database.data.stats.effects.defense;

import com.robertx22.mine_and_slash.database.data.stats.IUsableStat;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.mine_and_slash.database.data.stats.layers.StatLayers;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class ElementalResistEffect extends BaseDamageEffect {

    @Override
    public StatPriority GetPriority() {

        return StatPriority.Damage.DAMAGE_LAYERS;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Target;
    }

    @Override
    public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {

        float pene = effect.getPenetration();

        // otherwise phys pene makes this do.. 10x the dmg as its a flat pene value and its used here as a % value
        if (stat.getElement() == Elements.Physical) {
            pene = 0;
        }

        float resist = data.getValue();

        resist -= pene;

        var usable = (IUsableStat) stat;

        resist = usable.getUsableValue(effect.targetData.getUnit(), (int) resist, effect.targetData.getLevel()) * 100F;


        if (stat.getElement() == Elements.Physical) {
            effect.getLayer(StatLayers.Defensive.PHYS_MITIGATION, EventData.NUMBER, Side()).reduce(resist);
        } else {
            effect.getLayer(StatLayers.Defensive.ELEMENTAL_MITIGATION, EventData.NUMBER, Side()).reduce(resist);
        }

        effect.data.setBoolean(EventData.RESISTED_ALREADY, true);

        return effect;

    }


    @Override
    public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
        if (stat.getElement() == Elements.Elemental) {
            // we use ele res as a way to transfer to other stats already
            return false;
        }
        if (effect.data.getBoolean(EventData.RESISTED_ALREADY)) {
            return false;
        }
        if (effect.GetElement().equals(stat.getElement())) {
            return true;
        }

        return false;
    }

}
