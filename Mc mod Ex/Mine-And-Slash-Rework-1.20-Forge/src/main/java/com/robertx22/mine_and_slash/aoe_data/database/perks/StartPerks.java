package com.robertx22.mine_and_slash.aoe_data.database.perks;

import com.robertx22.mine_and_slash.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.mine_and_slash.database.OptScaleExactStat;
import com.robertx22.mine_and_slash.database.data.perks.Perk;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.resources.ResourceLocation;

public class StartPerks implements ExileRegistryInit {

    public static String MAGE = "mage";
    public static String WARRIOR = "warrior";
    public static String RANGER = "ranger";
    public static String GUARDIAN = "guardian";


    @Override
    public void registerAll() {


        of(MAGE, "Mage",
                new OptScaleExactStat(9, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(5, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(5, DatapackStats.DEX, ModType.FLAT)
        );
        of(WARRIOR, "Warrior",
                new OptScaleExactStat(5, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(9, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(5, DatapackStats.DEX, ModType.FLAT)
        );
        of(RANGER, "Ranger",
                new OptScaleExactStat(5, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(5, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(9, DatapackStats.DEX, ModType.FLAT)
        );
        of(GUARDIAN, "Guardian",
                new OptScaleExactStat(7, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(7, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(5, DatapackStats.DEX, ModType.FLAT)
        );


        // ascendancies

        ascendancy(AscendancyPerks.LICH,
                new OptScaleExactStat(15, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(5, DatapackStats.DEX, ModType.FLAT)
        );
        ascendancy(AscendancyPerks.ARCANIST,
                new OptScaleExactStat(15, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(5, DatapackStats.DEX, ModType.FLAT)
        );
        ascendancy(AscendancyPerks.BATTLEMAGE,
                new OptScaleExactStat(10, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.DEX, ModType.FLAT)
        );
        ascendancy(AscendancyPerks.GUARDIAN,
                new OptScaleExactStat(5, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(15, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.DEX, ModType.FLAT)
        );
        ascendancy(AscendancyPerks.ELEMENTALIST,
                new OptScaleExactStat(15, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(5, DatapackStats.DEX, ModType.FLAT)
        );
        ascendancy(AscendancyPerks.NECROMANCER,
                new OptScaleExactStat(15, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(5, DatapackStats.DEX, ModType.FLAT)
        );
        ascendancy(AscendancyPerks.JESTER,
                new OptScaleExactStat(15, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.DEX, ModType.FLAT)
        );
        ascendancy(AscendancyPerks.ASCENDANT,
                new OptScaleExactStat(10, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.DEX, ModType.FLAT)
        );
        ascendancy(AscendancyPerks.ASSASSIN,
                new OptScaleExactStat(10, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(15, DatapackStats.DEX, ModType.FLAT)
        );
        ascendancy(AscendancyPerks.TRICKSTER,
                new OptScaleExactStat(10, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.DEX, ModType.FLAT)
        );
        ascendancy(AscendancyPerks.HUNTER,
                new OptScaleExactStat(5, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(15, DatapackStats.DEX, ModType.FLAT)
        );
        ascendancy(AscendancyPerks.RAIDER,
                new OptScaleExactStat(5, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(15, DatapackStats.DEX, ModType.FLAT)
        );
        ascendancy(AscendancyPerks.CHAMPION,
                new OptScaleExactStat(5, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(15, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.DEX, ModType.FLAT)
        );
        ascendancy(AscendancyPerks.CHIEFTAIN,
                new OptScaleExactStat(10, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(10, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(5, DatapackStats.DEX, ModType.FLAT)
        );
    }

    Perk ascendancy(AscendancyKey key, OptScaleExactStat... stats) {

        Perk perk = PerkBuilder.bigStat(key.getStartPerkId(), key.name, stats);
        perk.is_entry = true;
        perk.type = Perk.PerkType.ASC;
        perk.one_kind = "start";
        // perk.icon = new ResourceLocation(SlashRef.MODID, "textures/gui/stat_icons/start/" + "asc" + ".png").toString();
        perk.icon = new ResourceLocation(SlashRef.MODID, "textures/gui/talent_icons/asc/" + key.GUID() + ".png").toString();

        return perk;
    }

    Perk of(String id, String locname, OptScaleExactStat... stats) {

        Perk perk = PerkBuilder.bigStat(id, locname, stats);
        perk.is_entry = true;
        perk.type = Perk.PerkType.START;
        perk.one_kind = "start";
        perk.icon = new ResourceLocation(SlashRef.MODID, "textures/gui/stat_icons/start/" + id + ".png").toString();
        return perk;
    }
}
