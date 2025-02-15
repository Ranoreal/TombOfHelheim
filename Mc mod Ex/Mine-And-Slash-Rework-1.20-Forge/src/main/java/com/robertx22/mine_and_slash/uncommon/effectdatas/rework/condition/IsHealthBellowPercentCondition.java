package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;
import net.minecraft.world.entity.LivingEntity;

public class IsHealthBellowPercentCondition extends StatCondition {

    EffectSides side;
    int perc;

    public IsHealthBellowPercentCondition(String id, int percent, EffectSides side) {
        super(id, "is_hp_under");
        this.side = side;
        this.perc = percent;
    }

    IsHealthBellowPercentCondition() {
        super("", "is_hp_under");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        LivingEntity en = event.getSide(side);
        
        return perc > en.getHealth() / en.getMaxHealth() * 100;
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsHealthBellowPercentCondition.class;
    }

}
