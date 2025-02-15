package com.robertx22.mine_and_slash.database.data;

import com.robertx22.mine_and_slash.database.OptScaleExactStat;
import com.robertx22.mine_and_slash.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.SlotFamily;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.registry.IWeighted;

import java.util.ArrayList;
import java.util.List;

public class BaseGem implements IGUID, IWeighted {

    public List<OptScaleExactStat> on_armor_stats = new ArrayList<>();

    public List<OptScaleExactStat> on_jewelry_stats = new ArrayList<>();

    public List<OptScaleExactStat> on_weapons_stats = new ArrayList<>();

    public String item_id = "";

    public String identifier = "";

    public int tier = 1;
    public int weight = 1000;

    public float min_lvl_multi = 0;

    public int getReqLevelToDrop() {
        return (int) (GameBalanceConfig.get().MAX_LEVEL * min_lvl_multi);
    }

    public final List<OptScaleExactStat> getFor(SlotFamily sfor) {
        if (sfor == SlotFamily.Armor) {
            return on_armor_stats;
        }
        if (sfor == SlotFamily.Jewelry) {
            return on_jewelry_stats;
        }
        if (sfor == SlotFamily.Weapon) {
            return on_weapons_stats;
        }

        return on_armor_stats;

    }

    @Override
    public String GUID() {
        return identifier;
    }

    @Override
    public int Weight() {
        return weight;
    }
}
