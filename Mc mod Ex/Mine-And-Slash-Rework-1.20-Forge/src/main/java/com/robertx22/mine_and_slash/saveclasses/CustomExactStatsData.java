package com.robertx22.mine_and_slash.saveclasses;

import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CustomExactStatsData implements IStatCtx {

    public CustomExactStatsData() {

    }

    public HashMap<String, ExactStatData> stats = new HashMap<>();

    public HashMap<String, StatMod> mods = new HashMap<>();

    public void addExactStat(String hashmapGUID, String statGUID, float v1, ModType type) {
        try {
            stats.put(hashmapGUID, ExactStatData.noScaling(v1, type, statGUID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeExactStat(String hashmapGUID) {
        stats.remove(hashmapGUID);
    }

    public void addMod(String hashmapGUID, String statGUID, float v1, float v2, ModType type) {
        try {
            mods.put(hashmapGUID, new StatMod(v1, v2, statGUID, type));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeMod(String hashmapGUID) {
        mods.remove(hashmapGUID);
    }

    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> stats = new ArrayList<>();

        stats.addAll(this.stats.values());
        this.mods.values()
                .forEach(x -> stats.add(x.ToExactStat(100, Load.Unit(en)
                        .getLevel())));
        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.COMMAND_EXACT_STATS, stats));
    }

}
