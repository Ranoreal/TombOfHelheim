package com.robertx22.mine_and_slash.database.data.game_balance_config;

import net.minecraft.util.Mth;

public class LevelScalingConfig {

    float base_scaling;
    float per_level_scaling;
    boolean cap_to_max_lvl = true;

    public LevelScalingConfig() {
    }

    public LevelScalingConfig(float base_scaling, float per_level_scaling, boolean cap_to_max_lvl) {
        this.base_scaling = base_scaling;
        this.per_level_scaling = per_level_scaling;
        this.cap_to_max_lvl = cap_to_max_lvl;
    }

    public float getMultiFor(float lvl) {
        if (cap_to_max_lvl) {
            lvl = Mth.clamp(lvl, 1, GameBalanceConfig.get().MAX_LEVEL);
        }
        return base_scaling + (per_level_scaling * (lvl - 1));

    }
    

}
