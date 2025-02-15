package com.robertx22.mine_and_slash.database.data.stats.types.resources.energy;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

public class Energy extends Stat {
    public static String GUID = "energy";

    public static Energy getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Energy is used to attack with a weapon";
    }

    private Energy() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

        this.format = ChatFormatting.GREEN.getName();
        this.icon = "\u262F";
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
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Energy";
    }

    private static class SingletonHolder {
        private static final Energy INSTANCE = new Energy();
    }
}

