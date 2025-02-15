package com.robertx22.mine_and_slash.loot.blueprints.bases;

import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;
import net.minecraft.util.Mth;

public class LevelPart extends BlueprintPart<Integer, ItemBlueprint> {

    public boolean LevelRange = true;

    public int LevelVariance = ServerContainer.get().ITEM_LEVEL_VARIANCE.get();

    public int minLevel = 1;

    public int maxLevel = Integer.MAX_VALUE;

    public int number;

    public LevelPart(ItemBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected Integer generateIfNull() {

        int finalLvl = number;

        if (LevelRange) {
            finalLvl = RandomUtils.RandomRange(number - LevelVariance, number + LevelVariance);
        }

        finalLvl = Mth.clamp(finalLvl, minLevel, maxLevel);

        return Mth.clamp(finalLvl, this.minLevel, GameBalanceConfig.get().MAX_LEVEL);
    }

}