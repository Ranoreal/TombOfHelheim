package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;

import java.util.List;

public interface IStatsContainer {

    List<ExactStatData> GetAllStats(ExileStack gear);

    default boolean isBaseStats() {
        return false;
    }

}
