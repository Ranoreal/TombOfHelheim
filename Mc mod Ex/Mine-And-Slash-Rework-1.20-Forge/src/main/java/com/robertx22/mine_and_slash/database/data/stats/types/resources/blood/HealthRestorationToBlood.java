package com.robertx22.mine_and_slash.database.data.stats.types.resources.blood;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.effects.game_changers.HealthRestorationToBloodEffect;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

public class HealthRestorationToBlood extends Stat {
    public static String GUID = "hp_resto_to_blood";

    private HealthRestorationToBlood() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.Misc;

        this.is_long = true;
        this.statEffect = HealthRestorationToBloodEffect.getInstance();
    }

    public static HealthRestorationToBlood getInstance() {
        return HealthRestorationToBlood.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "";
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
        return ChatFormatting.GRAY + "You refill your " + Blood.getInstance()
            .getIconNameFormat() + " by " + ChatFormatting.GREEN + Stat.VAL1 + ChatFormatting.GRAY + "% of your non-spell health restoration effects.";
    }

    private static class SingletonHolder {
        private static final HealthRestorationToBlood INSTANCE = new HealthRestorationToBlood();
    }
}

