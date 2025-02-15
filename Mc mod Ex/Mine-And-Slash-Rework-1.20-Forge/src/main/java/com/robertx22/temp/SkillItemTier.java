package com.robertx22.temp;


import com.robertx22.mine_and_slash.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.mine_and_slash.database.data.level_ranges.LevelRange;
import com.robertx22.mine_and_slash.database.registrators.LevelRanges;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.ChatFormatting;

import java.util.Arrays;
import java.util.Comparator;

public enum SkillItemTier {

    TIER0("Spiritual", IRarity.COMMON_ID, LevelRanges.STARTER_0, 0, 0, 1, ChatFormatting.GRAY, 60 * 5, 20, 50),
    TIER1("Celestial", IRarity.UNCOMMON, LevelRanges.LOW_1, 0.2F, 1, 1.25F, ChatFormatting.GREEN, 60 * 6, 25, 100),
    TIER2("Empyrean", IRarity.RARE_ID, LevelRanges.MIDDLE_2, 0.4F, 2, 1.5F, ChatFormatting.BLUE, 60 * 7, 30, 200),
    TIER3("Angelic", IRarity.EPIC_ID, LevelRanges.HIGH_3, 0.6F, 3, 1.75F, ChatFormatting.LIGHT_PURPLE, 60 * 8, 40, 300),
    TIER4("Divine", IRarity.LEGENDARY_ID, LevelRanges.ENDGAME_4, 0.8F, 4, 2, ChatFormatting.GOLD, 60 * 10, 50, 500),
    TIER5("Godly", IRarity.MYTHIC_ID, LevelRanges.MAX_5, 1F, 5, 2.5F, ChatFormatting.DARK_PURPLE, 60 * 15, 50, 1000);

    SkillItemTier(String word, String rar, LevelRange levelRange, float lvl_req, int tier, float statMulti, ChatFormatting format, int durationseconds, float percent_healed, int repairDurab) {
        this.word = word;
        this.tier = tier;
        this.rar = rar;
        this.statMulti = statMulti;
        this.format = format;
        this.repairDurab = repairDurab;
        this.percent_healed = percent_healed;
        this.durationSeconds = durationseconds;
        this.lvl_req = lvl_req;
        this.levelRange = levelRange;
    }


    public int getDisplayTierNumber() {
        return tier;
    }

    public static SkillItemTier of(int tier) {
        return Arrays.stream(SkillItemTier.values())
                .filter(x -> x.tier == tier)
                .findAny()
                .orElse(SkillItemTier.TIER0);
    }

    public static SkillItemTier ofRarity(String rar) {
        return Arrays.stream(SkillItemTier.values())
                .filter(x -> x.rar.equals(rar))
                .findAny()
                .orElse(SkillItemTier.TIER0);
    }

    public static SkillItemTier fromLevel(int lvl) {
        if (lvl < 1) {
            lvl = 1;
        }
        if (lvl > GameBalanceConfig.get().MAX_LEVEL) {
            lvl = GameBalanceConfig.get().MAX_LEVEL;
        }

        int finalLvl = lvl;

        return Arrays.stream(SkillItemTier.values())
                .filter(x -> {
                    int minlvl = x.levelRange.getMinLevel();
                    return finalLvl >= minlvl;
                })
                .max(Comparator.comparingInt(x -> {
                    return x.tier;
                }))
                .orElseGet(() -> SkillItemTier.TIER0);
    }

    public SkillItemTier lowerTier() {

        if (this == TIER0) {
            return null;
        }
        if (this == TIER1) {
            return TIER0;
        }
        if (this == TIER2) {
            return TIER1;
        }
        if (this == TIER3) {
            return TIER2;
        }
        if (this == TIER4) {
            return TIER3;
        }
        if (this == TIER5) {
            return TIER4;
        }
        return null;

    }

    public SkillItemTier higherTier() {

        if (this == TIER0) {
            return TIER1;
        }
        if (this == TIER1) {
            return TIER2;
        }
        if (this == TIER2) {
            return TIER3;
        }
        if (this == TIER3) {
            return TIER4;
        }
        if (this == TIER4) {
            return TIER5;
        }
        return null;

    }

    public LevelRange levelRange;
    public float lvl_req;
    public String word;
    public String rar;
    public int tier;
    public float statMulti;
    public ChatFormatting format;
    public int durationSeconds;
    public int repairDurab;
    public float percent_healed;

}