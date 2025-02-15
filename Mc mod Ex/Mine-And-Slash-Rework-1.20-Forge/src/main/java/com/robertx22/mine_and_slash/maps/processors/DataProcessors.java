package com.robertx22.mine_and_slash.maps.processors;


import com.robertx22.mine_and_slash.maps.processors.boss.UberBossAltarProcessor;
import com.robertx22.mine_and_slash.maps.processors.league.LeagueControlBlockProcessor;
import com.robertx22.mine_and_slash.maps.processors.league.LeagueSpawnPos;
import com.robertx22.mine_and_slash.maps.processors.league.LeagueTpBackProcessor;
import com.robertx22.mine_and_slash.maps.processors.league.MapTeleporterProcessor;
import com.robertx22.mine_and_slash.maps.processors.misc.RemoveAllBesidesOneProcessor;
import com.robertx22.mine_and_slash.maps.processors.mob.*;
import com.robertx22.mine_and_slash.maps.processors.reward.ChanceChestProcessor;
import com.robertx22.mine_and_slash.maps.processors.reward.ChestProcessor;
import com.robertx22.mine_and_slash.maps.processors.reward.MapRewardChestProcessor;

import java.util.ArrayList;
import java.util.List;

public class DataProcessors {

    public static DataProcessor MOB = new MobProcessor();

    static List<DataProcessor> all = new ArrayList<>();

    public static List<DataProcessor> getAll() {


        if (all.isEmpty()) {
            all.add(new MapTeleporterProcessor());
            all.add(new MapRewardChestProcessor());
            all.add(new MapBossProcessor());
            all.add(new BossProcessor());
            all.add(new EliteProcessor());
            all.add(MOB);
            all.add(new ChestProcessor());
            all.add(new MobHordeProcessor());
            all.add(new EliteMobHorde());
            all.add(new ChanceChestProcessor());
            all.add(new RemoveAllBesidesOneProcessor());
            all.add(new ComplexMobProcessor());
            all.add(new UberBossAltarProcessor());
            all.add(new LeagueControlBlockProcessor());
            all.add(new LeagueTpBackProcessor());
            all.add(new LeagueSpawnPos());
        }

        return all;

    }

}
