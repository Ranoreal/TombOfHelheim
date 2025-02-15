package com.robertx22.mine_and_slash.uncommon.datasaving;

import com.robertx22.mine_and_slash.saveclasses.CustomExactStatsData;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.nbt.CompoundTag;

public class CustomExactStats {

    public static final String LOC = "mmorpg:custom_exact_stats_data";

    public static CustomExactStatsData Load(CompoundTag nbt) {

        if (nbt == null) {
            return null;
        }

        return LoadSave.Load(CustomExactStatsData.class, new CustomExactStatsData(), nbt, LOC);

    }

    public static CompoundTag Save(CompoundTag nbt, CustomExactStatsData gear) {

        if (nbt == null) {
            return new CompoundTag();
        }

        if (gear != null) {
            return LoadSave.Save(gear, nbt, LOC);
        }

        return new CompoundTag();
    }
}
