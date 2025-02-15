package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts;

import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatWithContext;

import java.util.ArrayList;
import java.util.List;


public class CraftStatData {


    public int p = 0; // perc. this is just for tooltips

    public ExactStatData s = null;

    public ExactStatData getStat() {
        return s;
    }

    public List<TooltipStatWithContext> getAllStatsWithCtx(StatRangeInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();
        list.add(new TooltipStatWithContext(new TooltipStatInfo(s, p, info), null, 1));
        return list;
    }
}
