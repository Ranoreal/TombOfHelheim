package com.robertx22.mine_and_slash.database.data.stats.datapacks.serializers;

import com.robertx22.mine_and_slash.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.mine_and_slash.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.mine_and_slash.database.data.stats.datapacks.stats.*;

import java.util.HashMap;

public class StatSerializers {

    public static StatSerializers INSTANCE = new StatSerializers();

    public HashMap<String, IStatSerializer<? extends BaseDatapackStat>> map = new HashMap<>();

    private StatSerializers() {

        map.put(AddPerPercentOfOther.SER_ID, new OneAppliesToOtherSer());
        map.put(MoreXPerYOf.SER_ID, new MoreXPerYOfSer());
        map.put(MarkerStat.SER_ID, new MarkerSer());
        map.put(AttributeStat.SER_ID, new AttributeStatSer());
        map.put(CoreStat.SER_ID, new CoreStatSer());
        map.put(BonusStatPerEffectStacks.SER_ID, new BonusPerChargesSer());
    }

}
