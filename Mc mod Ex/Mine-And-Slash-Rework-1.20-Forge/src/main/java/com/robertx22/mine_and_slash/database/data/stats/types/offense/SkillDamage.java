package com.robertx22.mine_and_slash.database.data.stats.types.offense;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;
import net.minecraft.ChatFormatting;

public class SkillDamage extends Stat {

    private SkillDamage() {
        this.group = StatGroup.MAIN;

        this.statEffect = new Effect();
        this.format = ChatFormatting.LIGHT_PURPLE.getName();


    }

    public static String GUID = "spell_damage";

    public static SkillDamage getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases DMG skills deal";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Skill Damage";
    }

    private static class Effect extends BaseDamageIncreaseEffect {

        private Effect() {
            super();
        }

   
        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return effect.isSpell();
        }
    }

    private static class SingletonHolder {
        private static final SkillDamage INSTANCE = new SkillDamage();
    }
}
