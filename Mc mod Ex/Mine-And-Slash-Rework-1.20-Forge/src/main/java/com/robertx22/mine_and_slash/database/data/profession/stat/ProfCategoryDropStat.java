package com.robertx22.mine_and_slash.database.data.profession.stat;

import com.robertx22.mine_and_slash.database.data.profession.Profession;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

public class ProfCategoryDropStat extends Stat {

    Profession.DropCategory cat;
    String prof;

    public ProfCategoryDropStat(Profession.DropCategory cat, String prof) {
        this.cat = cat;
        this.prof = prof;

        this.min = 0;
        this.scaling = StatScaling.NONE;
        this.group = StatGroup.Misc;

    }

    @Override
    public String locDescForLangFile() {
        return "Prof drops are split into categories, this stat increases the droprate of one such";
    }


    @Override
    public String GUID() {
        return prof + "_" + cat.id + "_drop_bonus";
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return cat.locname + " Drops";
    }

}
