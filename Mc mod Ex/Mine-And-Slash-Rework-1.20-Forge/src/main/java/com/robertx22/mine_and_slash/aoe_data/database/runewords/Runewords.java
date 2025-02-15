package com.robertx22.mine_and_slash.aoe_data.database.runewords;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.aoe_data.database.ailments.Ailments;
import com.robertx22.mine_and_slash.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.mine_and_slash.aoe_data.database.stats.OffenseStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.ResourceStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.mine_and_slash.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.mine_and_slash.database.data.aura.AuraGems;
import com.robertx22.mine_and_slash.database.data.stats.types.MaxAllSpellLevels;
import com.robertx22.mine_and_slash.database.data.stats.types.MaxSpellLevel;
import com.robertx22.mine_and_slash.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.mine_and_slash.database.data.stats.types.ailment.AilmentDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.gear_base.GearDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.gear_base.GearDefense;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.energy.Energy;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.Health;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.mana.Mana;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.tags.all.SpellTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.AttackType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.vanilla_mc.items.gemrunes.RuneType;

import java.util.Arrays;

public class Runewords implements ExileRegistryInit {

    // todo add more stuff

    public static String EMPTY = "empty";

    @Override
    public void registerAll() {
        RunewordBuilder.of(EMPTY, EMPTY,
                Arrays.asList(
                        GearDamage.getInstance().mod(1, 1).percent()
                ),
                Arrays.asList(RuneType.MOS, RuneType.NET, RuneType.ITA, RuneType.XER, RuneType.HAR),
                GearSlots.STAFF);

        // todo turn this into an actual builder so editing later doesnt become a pain

        RunewordBuilder.of("plague", "Plague",
                Arrays.asList(
                        GearDamage.getInstance().mod(50, 150).percent(),
                        new MaxAllSpellLevels().mod(1, 2),
                        new AilmentChance(Ailments.BLEED).mod(5, 15),
                        new AilmentDamage(Ailments.POISON).mod(10, 15),
                        new ElementalPenetration(Elements.Shadow).mod(10, 25)
                ),
                Arrays.asList(RuneType.VEN, RuneType.ANO, RuneType.ITA, RuneType.XER, RuneType.HAR),
                GearSlots.BOW, GearSlots.STAFF);

        RunewordBuilder.of("flickering_flame", "Flickering Flame",
                Arrays.asList(
                        GearDefense.getInstance().mod(50, 100).percent(),
                        new MaxSpellLevel(SpellTags.FIRE).mod(1, 2),
                        SpellChangeStats.SPECIFIC_AURA_COST.get(AuraGems.FIRE_RESIST).mod(-25, -50),
                        new ElementalPenetration(Elements.Fire).mod(10, 15),
                        new ElementalResist(Elements.Shadow).mod(15, 25)
                ),
                Arrays.asList(RuneType.ENO, RuneType.MOS, RuneType.ANO, RuneType.XER, RuneType.HAR),
                GearSlots.HELMET);


        RunewordBuilder.of("cure", "Cure",
                Arrays.asList(
                        GearDefense.getInstance().mod(25, 100).percent(),
                        new MaxAllSpellLevels().mod(1, 1),
                        OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.thorns).mod(15, 25),
                        ResourceStats.HEAL_STRENGTH.get().mod(15, 25),
                        new ElementalResist(Elements.Shadow).mod(25, 25),
                        Energy.getInstance().mod(5, 10).percent(),
                        Mana.getInstance().mod(5, 10).percent()
                ),
                Arrays.asList(RuneType.WIR, RuneType.MOS, RuneType.ANO, RuneType.CEN, RuneType.HAR),
                GearSlots.CHEST);


        RunewordBuilder.of("bramble", "Bramble",
                Arrays.asList(
                        GearDefense.getInstance().mod(25, 100).percent(),
                        new MaxSpellLevel(SpellTags.PHYSICAL).mod(1, 1),
                        OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.thorns).mod(15, 25),
                        Health.getInstance().mod(5, 10).percent(),
                        Energy.getInstance().mod(10, 10).percent(),
                        Mana.getInstance().mod(10, 10).percent()
                ),
                Arrays.asList(RuneType.FEY, RuneType.MOS, RuneType.ANO, RuneType.CEN),
                GearSlots.CHEST);

        RunewordBuilder.of("abyssal_depths", "Abyssal Depths",
                Arrays.asList(
                        GearDefense.getInstance().mod(25, 100).percent(),
                        new MaxSpellLevel(SpellTags.summon).mod(1, 1),
                        Health.getInstance().mod(5, 10).percent(),
                        Energy.getInstance().mod(10, 25).percent(),
                        Mana.getInstance().mod(10, 25).percent(),
                        OffenseStats.SUMMON_DAMAGE.get().mod(5, 15)
                ),
                Arrays.asList(RuneType.NOS, RuneType.MOS, RuneType.ITA, RuneType.CEN),
                GearSlots.CHEST);


        RunewordBuilder.of("nature_wrath", "Nature's Wrath",
                Arrays.asList(
                        new MaxSpellLevel(SpellTags.LIGHTNING).mod(1, 2),
                        new MaxSpellLevel(SpellTags.COLD).mod(1, 2),
                        new ElementalResist(Elements.Fire).mod(-25, -10),
                        Mana.getInstance().mod(10, 25).percent()
                ),
                Arrays.asList(RuneType.BRI, RuneType.MOS, RuneType.UND, RuneType.ITA),
                GearSlots.NECKLACE);

        RunewordBuilder.of("choosen_of_azuna", "Choosen of Azuna",
                Arrays.asList(
                        DatapackStats.INT.mod(3, 5),
                        DatapackStats.STR.mod(3, 5),
                        DatapackStats.DEX.mod(3, 5),
                        OffenseStats.TOTAL_DAMAGE.get().mod(5, 20)
                ),
                Arrays.asList(RuneType.MOS, RuneType.NOS, RuneType.ITA),
                GearSlots.RING);

        RunewordBuilder.of("the_novice", "The Novice",
                Arrays.asList(
                        GearDamage.getInstance().mod(30, 75).percent(),
                        ResourceStats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.energy, AttackType.hit)).mod(1, 1),
                        ResourceStats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.hit)).mod(1, 1)
                ),
                Arrays.asList(RuneType.NOS, RuneType.DOS, RuneType.ITA, RuneType.MOS),
                GearSlots.allWeapons());


    }
}
