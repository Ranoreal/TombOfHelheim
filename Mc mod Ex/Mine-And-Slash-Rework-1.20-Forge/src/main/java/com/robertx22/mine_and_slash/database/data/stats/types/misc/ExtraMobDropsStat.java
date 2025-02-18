package com.robertx22.mine_and_slash.database.data.stats.types.misc;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

public class ExtraMobDropsStat extends Stat {
    public static String GUID = "extra_mob_drops";

    public static ExtraMobDropsStat getInstance() {
        return ExtraMobDropsStat.SingletonHolder.INSTANCE;
    }

    private ExtraMobDropsStat() {
        this.min = -100;
        this.scaling = StatScaling.NONE;
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
        return "Extra Drops";
    }

    @Override
    public String locDescForLangFile() {
        return "Mobs with this stat drop extra loot when slain.";
    }

    private static class SingletonHolder {
        private static final ExtraMobDropsStat INSTANCE = new ExtraMobDropsStat();
    }
}

