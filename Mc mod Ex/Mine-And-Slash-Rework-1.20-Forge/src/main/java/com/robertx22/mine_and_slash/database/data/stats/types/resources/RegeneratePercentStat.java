package com.robertx22.mine_and_slash.database.data.stats.types.resources;

import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseRegenEffect;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.energy.Energy;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.Health;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.mana.Mana;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

import java.util.function.Function;

public class RegeneratePercentStat extends Stat {

    public static RegeneratePercentStat HEALTH = new RegeneratePercentStat(Health.getInstance(), ResourceType.health, x -> x.getUnit()
            .healthData()
            .getValue());
    public static RegeneratePercentStat MANA = new RegeneratePercentStat(Mana.getInstance(), ResourceType.mana, x -> x.getUnit()
            .manaData()
            .getValue());
    public static RegeneratePercentStat ENERGY = new RegeneratePercentStat(Energy.getInstance(), ResourceType.energy, x -> x.getUnit()
            .energyData()
            .getValue());
    public static RegeneratePercentStat MAGIC_SHIELD = new RegeneratePercentStat(MagicShield.getInstance(), ResourceType.magic_shield, x -> x.getUnit()
            .magicShieldData()
            .getValue());

    Stat statRestored;
    ResourceType type;

    Function<EntityData, Float> maxGetter;

    private RegeneratePercentStat(Stat statRestored, ResourceType rtype, Function<EntityData, Float> getmax) {
        this.min = 0;
        this.max = 25;
        
        this.statRestored = statRestored;
        this.scaling = StatScaling.NONE;
        this.type = rtype;
        this.maxGetter = getmax;

        this.group = StatGroup.RESTORATION;

        this.statEffect = new BaseRegenEffect() {

            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }

            @Override
            public StatPriority GetPriority() {
                return StatPriority.Spell.FIRST;
            }

            @Override
            public RestoreResourceEvent activate(RestoreResourceEvent effect, StatData data, Stat stat) {
                effect.data.getNumber(EventData.NUMBER).number += maxGetter.apply(effect.targetData) * data.getValue() / 100F;
                return effect;
            }

            @Override
            public boolean canActivate(RestoreResourceEvent effect, StatData data, Stat stat) {
                return effect.data.getResourceType() == type && effect.data.getRestoreType() == RestoreType.regen;
            }
        };

    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "Restore % of your total per regen tick.";
    }

    @Override
    public String locNameForLangFile() {
        return statRestored.locNameForLangFile() + " Per Regen";
    }

    @Override
    public String GUID() {
        return statRestored.GUID() + "_per_sec";
    }

}
