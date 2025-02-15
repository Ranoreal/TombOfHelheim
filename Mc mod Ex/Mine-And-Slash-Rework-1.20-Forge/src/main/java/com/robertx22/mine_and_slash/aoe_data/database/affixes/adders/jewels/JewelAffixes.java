package com.robertx22.mine_and_slash.aoe_data.database.affixes.adders.jewels;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.stats.OffenseStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.Armor;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.DodgeRating;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.Health;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.mine_and_slash.database.data.stats.types.spirit.AuraCapacity;
import com.robertx22.mine_and_slash.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class JewelAffixes implements ExileRegistryInit {
    @Override
    public void registerAll() {
        String PREFIX = "jewel_";

        // jewels dont need names
        ElementalAffixBuilder.start()
                .guid(x -> x.guidName + "_jewel_res")
                .add(Elements.Fire, "")
                .add(Elements.Nature, "")
                .add(Elements.Cold, "")
                .add(Elements.Shadow, "")
                .stats(x -> Arrays.asList(new StatMod(5, 10, new ElementalResist(x), ModType.FLAT)))
                .includesTags(SlotTags.any_jewel)
                .Weight(5000)
                .Jewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "aura_cost")
                .stat(AuraCapacity.getInstance().mod(1, 3))
                .includesTags(SlotTags.any_jewel)
                .Weight(1000)
                .Jewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "aura_eff")
                .stat(AuraEffect.getInstance().mod(1, 3))
                .includesTags(SlotTags.any_jewel)
                .Weight(1000)
                .Jewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "hp")
                .stat(Health.getInstance().mod(1, 5).percent())
                .includesTags(SlotTags.any_jewel)
                .Weight(1000)
                .Jewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "ms")
                .stat(MagicShield.getInstance().mod(1, 5).percent())
                .includesTags(SlotTags.any_jewel)
                .Weight(1000)
                .Jewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "armor")
                .stat(Armor.getInstance().mod(1, 5).percent())
                .includesTags(SlotTags.any_jewel)
                .Weight(1000)
                .Jewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "dodge")
                .stat(DodgeRating.getInstance().mod(1, 5).percent())
                .includesTags(SlotTags.any_jewel)
                .Weight(1000)
                .Jewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "cdr")
                .stat(SpellChangeStats.COOLDOWN_REDUCTION.get().mod(1, 3))
                .includesTags(SlotTags.any_jewel)
                .Weight(500)
                .Jewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "crit")
                .stat(OffenseStats.CRIT_CHANCE.get().mod(0.5F, 1))
                .includesTags(SlotTags.any_jewel)
                .Weight(500)
                .Jewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "critdmg")
                .stat(OffenseStats.CRIT_DAMAGE.get().mod(1, 3))
                .includesTags(SlotTags.any_jewel)
                .Weight(500)
                .Jewel()
                .Build();
    }
}
