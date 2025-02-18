package com.robertx22.mine_and_slash.aoe_data.database.stats.old;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;

import java.util.HashSet;
import java.util.Set;

public class AutoDatapackStats implements ExileRegistryInit {
    public static Set<BaseDatapackStat> STATS_TO_ADD_TO_SERIALIZATION = new HashSet<>();

    @Override
    public void registerAll() {
        STATS_TO_ADD_TO_SERIALIZATION
                .forEach(x -> x.addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO));
    }
}
