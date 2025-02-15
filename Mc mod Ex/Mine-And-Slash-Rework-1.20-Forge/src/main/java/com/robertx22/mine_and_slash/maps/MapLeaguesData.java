package com.robertx22.mine_and_slash.maps;

import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.database.data.league.LeagueMechanic;
import com.robertx22.mine_and_slash.database.data.league.LeagueMechanics;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.PointData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MapLeaguesData {


    HashMap<String, LeagueData> map = new HashMap<>();

    public int totalGenDungeonChunks = 0;
    public int processedChunks = 0;

    // chunks mechanics were tried to spawn or succeded in (limit 1 per chunk, both attempts and successes)
    public Set<PointData> mechsChunks = new HashSet<>();

    // calc chance in such a way that if a mechanic should spawn 5 times, and the dungeon has 20 chunks left, it scatters the spawn chunks but never accidentally not generates
    public float calcSpawnChance(BlockPos pos) {

        var cp = new ChunkPos(pos);
        var point = new PointData(cp.x, cp.z);

        if (mechsChunks.contains(point)) {
            return 0;
        }

        int remaining = getTotalSpawnsRemainingFromAllLeagues();
        int chunksLeft = totalGenDungeonChunks - processedChunks;


        if (chunksLeft < remaining) {
            return 100;
        }


        float chance = remaining / (float) chunksLeft * 100F;

        return chance;

    }

    public int getTotalSpawnsRemainingFromAllLeagues() {
        return (int) map.values().stream().mapToInt(x -> x.remainingSpawns).sum();
    }

    public List<LeagueMechanic> getLeagueMechanics() {
        return map.keySet().stream().map(x -> ExileDB.LeagueMechanics().get(x)).collect(Collectors.toList());
    }

    // todo eventually player stats will decide mechanic spawn chances etc
    public void setupOnMapStart(MapItemData map, Player p) {

        for (LeagueMechanic m : ExileDB.LeagueMechanics().getList()) {
            if (m.getBaseSpawnChance() > 0 && RandomUtils.roll(m.getBaseSpawnChance())) {
                var data = new LeagueData();
                m.onMapStartSetupBase(map, data);
                m.onMapStartSetup(data);
                this.map.put(m.GUID(), data);
            }
        }

        if (map.isUber()) {
            var data = new LeagueData();
            LeagueMechanics.UBER.onMapStartSetupBase(map, data);
            LeagueMechanics.UBER.onMapStartSetup(data);
            this.map.put(LeagueMechanics.UBER_ID, data);
        }

        var mapBoss = new LeagueData();
        LeagueMechanics.MAP_BOSS.onMapStartSetupBase(map, mapBoss);
        LeagueMechanics.MAP_BOSS.onMapStartSetup(mapBoss);
        this.map.put(LeagueMechanics.MAP_BOSS_ID, mapBoss);

        var mapReward = new LeagueData();
        LeagueMechanics.MAP_REWARD.onMapStartSetupBase(map, mapReward);
        LeagueMechanics.MAP_REWARD.onMapStartSetup(mapReward);
        this.map.put(LeagueMechanics.MAP_REWARD_ID, mapReward);
    }

    public LeagueData get(LeagueMechanic m) {
        return map.getOrDefault(m.GUID(), LeagueData.EMPTY);
    }

}
