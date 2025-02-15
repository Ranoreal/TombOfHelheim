package com.robertx22.mine_and_slash.uncommon.interfaces.data_items;

import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.registry.ExileDB;

import java.util.Arrays;
import java.util.List;

public interface IRarity {


    // public static int TOTAL_GEAR_RARITIES = 6;

    public String getRarityId();

    public default GearRarity getRarity() {
        return ExileDB.GearRarities().get(getRarityId());
    }

    default boolean isUnique() {
        return this.getRarityId()
                .equals(UNIQUE_ID);
    }


    String COMMON_ID = "common";
    String UNCOMMON = "uncommon";
    String RARE_ID = "rare";
    String EPIC_ID = "epic";
    String LEGENDARY_ID = "legendary";
    String MYTHIC_ID = "mythic";
    String BOSS = "boss";
    String UBER = "uber";

    public static List<String> NORMAL_GEAR_RARITIES = Arrays.asList(COMMON_ID, UNCOMMON, RARE_ID, EPIC_ID, LEGENDARY_ID, MYTHIC_ID);


    String UNIQUE_ID = "unique";
    String RUNEWORD_ID = "runeword";
    String SUMMON_ID = "summon";

    public static List<String> ALL_GEAR_RARITIES = Arrays.asList(COMMON_ID, UNCOMMON, RARE_ID, EPIC_ID, LEGENDARY_ID, MYTHIC_ID, RUNEWORD_ID, UNIQUE_ID);


}
