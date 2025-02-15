package com.robertx22.mine_and_slash.database.data.stats.types.misc;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EventBuilder;
import com.robertx22.mine_and_slash.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class DamageTakenToMana extends Stat {

    private DamageTakenToMana() {
        this.statEffect = new Effect();
    }

    public static DamageTakenToMana getInstance() {
        return DamageTakenToMana.SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return "% of damage taken is restored to mana.";
    }

    @Override
    public String GUID() {
        return "dmg_taken_to_mana";
    }

    @Override
    public String locNameForLangFile() {
        return "Damage Taken to Mana";
    }

    private static class Effect extends BaseDamageEffect {

        @Override
        public StatPriority GetPriority() {
            return StatPriority.Damage.FINAL_DAMAGE;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Target;
        }

        @Override
        public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {

            float restore = effect.data.getNumber() * data.getValue() / 100F; // todo dmg number

            if (restore > 0) {
                RestoreResourceEvent mana = EventBuilder.ofRestore(effect.source, effect.target, ResourceType.mana, RestoreType.heal, restore)
                        .build();
                mana.Activate();

            }

            return effect;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return true;
        }

    }

    private static class SingletonHolder {
        private static final DamageTakenToMana INSTANCE = new DamageTakenToMana();
    }
}
