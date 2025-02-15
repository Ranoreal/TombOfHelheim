package com.robertx22.mine_and_slash.aoe_data.database.unique_gears.uniques.jewelry;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.aoe_data.database.ailments.Ailments;
import com.robertx22.mine_and_slash.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.mine_and_slash.aoe_data.database.stats.EffectStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.OffenseStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.ResourceStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.mine_and_slash.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.Armor;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.DodgeRating;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.mine_and_slash.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.SkillDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.energy.Energy;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.Health;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.mana.Mana;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.special.SpecialStats;
import com.robertx22.mine_and_slash.database.data.stats.types.spirit.AuraCapacity;
import com.robertx22.mine_and_slash.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.tags.all.EffectTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class UniqueRings implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of("air_disaster", "Aria of Disaster",
                        BaseGearTypes.RING)
                .setReplacesName()
                .stats(Arrays.asList(
                        new StatMod(15, 30, EffectStats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.song), ModType.FLAT),
                        new StatMod(5, 15, OffenseStats.AREA_DAMAGE.get(), ModType.FLAT),
                        new StatMod(3, 10, ManaRegen.getInstance(), ModType.FLAT)
                ))
                .devComment("song buffer / area damage")
                .build();

        UniqueGearBuilder.of(
                        "playful_hope",
                        "Playful Hope",
                        BaseGearTypes.RING)
                .setReplacesName()
                .stats(Arrays.asList(
                        new StatMod(-100, 75, OffenseStats.CRIT_CHANCE.get()).percent(), // todo
                        new StatMod(-50, 25, new ElementalResist(Elements.Cold), ModType.FLAT),
                        new StatMod(-50, 25, new ElementalResist(Elements.Fire), ModType.FLAT),
                        new StatMod(-50, 25, new ElementalResist(Elements.Shadow), ModType.FLAT)
                ))
                .devComment("global crit + res, high RNG")
                .build();

        UniqueGearBuilder.of(
                        "spring_blossoms",
                        "Spring Blossoms",
                        BaseGearTypes.RING)
                .setReplacesName()
                .stats(Arrays.asList(
                        new StatMod(10, 10, SpecialStats.HEAL_CLEANSE, ModType.FLAT),
                        new StatMod(5, 10, HealthRegen.getInstance(), ModType.PERCENT),
                        new StatMod(10, 15, ResourceStats.HEAL_STRENGTH.get(), ModType.FLAT),
                        new StatMod(15, 25, new ElementalResist(Elements.Shadow), ModType.FLAT)
                ))

                .devComment("")
                .build();

        UniqueGearBuilder.of(
                        "autumn_harvest",
                        "Autumn Harvest",
                        BaseGearTypes.RING)
                .setReplacesName()

                .stats(Arrays.asList(
                        new StatMod(10, 20, new ElementalResist(Elements.Shadow), ModType.FLAT),
                        new StatMod(10, 20, new ElementalResist(Elements.Fire), ModType.FLAT),
                        new StatMod(10, 20, new ElementalResist(Elements.Cold), ModType.FLAT),
                        new StatMod(25, 25, SpecialStats.BETTER_FOOD_BUFFS, ModType.FLAT),
                        new StatMod(5, 10, Armor.getInstance(), ModType.PERCENT),
                        new StatMod(5, 10, DodgeRating.getInstance(), ModType.PERCENT),
                        new StatMod(2, 3, AllAttributes.getInstance(), ModType.FLAT)
                ))


                .devComment("")
                .build();

        UniqueGearBuilder.of(
                        "winter_chill",
                        "Winter Chill",
                        BaseGearTypes.RING)
                .setReplacesName()
                .stats(Arrays.asList(new StatMod(5, 10, Health.getInstance(), ModType.FLAT),
                        new StatMod(20, 40, new ElementalResist(Elements.Cold), ModType.FLAT),
                        new StatMod(10, 25, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Cold), ModType.FLAT),
                        new StatMod(5, 15, Mana.getInstance(), ModType.PERCENT),
                        new StatMod(5, 15, OffenseStats.CRIT_CHANCE.get(), ModType.FLAT)
                ))
                .devComment("")
                .build();

        UniqueGearBuilder.of("summer_heat", "Summer Heat", BaseGearTypes.RING)
                .setReplacesName()
                .stats(Arrays.asList(new StatMod(5, 10, Health.getInstance(), ModType.FLAT),
                        new StatMod(20, 40, new ElementalResist(Elements.Fire), ModType.FLAT),
                        new StatMod(10, 25, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.FLAT),
                        new StatMod(5, 15, Mana.getInstance(), ModType.PERCENT),
                        new StatMod(5, 15, OffenseStats.CRIT_DAMAGE.get(), ModType.FLAT)
                ))
                .devComment("")
                .build();
        UniqueGearBuilder.of(
                        "azuna_ring",
                        "Azuna's Eternal Decree",
                        BaseGearTypes.RING)
                .setReplacesName()
                .stats(Arrays.asList(
                        new StatMod(-50, 50, new ElementalResist(Elements.Fire), ModType.FLAT),
                        new StatMod(-50, 50, new ElementalResist(Elements.Shadow), ModType.FLAT),
                        new StatMod(-50, 15, TreasureQuality.getInstance(), ModType.FLAT),
                        new StatMod(-50, 15, TreasureQuantity.getInstance(), ModType.FLAT)
                ))
                .devComment("God's ring: item find and luck")
                .build();

        UniqueGearBuilder.of(
                        "witch_brew",
                        "Witch's Brew",
                        BaseGearTypes.RING)

                .stats(Arrays.asList(
                        new StatMod(15, 25, new ElementalResist(Elements.Shadow), ModType.FLAT),
                        new StatMod(-20, 20, AuraEffect.getInstance(), ModType.FLAT),
                        new StatMod(10, 15, SkillDamage.getInstance(), ModType.FLAT),
                        new StatMod(5, 10, ManaRegen.getInstance(), ModType.PERCENT)
                ))

                .devComment("Food buff mage ring")
                .build();

        UniqueGearBuilder.of(
                        "ghostly_shores",
                        "Ghostly Shores",
                        BaseGearTypes.RING)
                .setReplacesName()
                .stats(Arrays.asList(
                        new StatMod(5, 15, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Cold), ModType.FLAT),
                        new StatMod(6, 10, DodgeRating.getInstance(), ModType.PERCENT),
                        new StatMod(6, 15, ResourceStats.RESOURCE_ON_KILL.get(ResourceType.mana), ModType.FLAT),
                        new StatMod(10, 15, OffenseStats.CRIT_DAMAGE.get(), ModType.FLAT),
                        new StatMod(5, 10, SpellChangeStats.COOLDOWN_REDUCTION.get(), ModType.FLAT),
                        new StatMod(-3, -6, DatapackStats.STR, ModType.FLAT)
                ))

                .build();


        UniqueGearBuilder.of("ele_gamble", "Gamble of the Elements",
                        BaseGearTypes.RING)
                .setReplacesName()
                .stats(Arrays.asList(
                        new StatMod(-50, 35, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Cold), ModType.FLAT),
                        new StatMod(-50, 35, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.FLAT),
                        new StatMod(-50, 35, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Nature), ModType.FLAT)
                ))

                .build();


        UniqueGearBuilder.of("thread_of_hope", "Thread of Hope", BaseGearTypes.RING)
                .keepsBaseName()
                .stat(AuraCapacity.getInstance().mod(-25, 20))
                .stat(Health.getInstance().mod(5, 10))
                .stat(Mana.getInstance().mod(5, 10))
                .stat(Energy.getInstance().mod(5, 10))
                .build();


        UniqueGearBuilder.of("piercing_touch", "Piercing Touch", BaseGearTypes.RING)
                .keepsBaseName()
                .stat(OffenseStats.PROJECTILE_DAMAGE.get().mod(10, 25))
                .stat(OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Fire).mod(15, 40))
                .stat(OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Cold).mod(15, 40))
                .stat(OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Shadow).mod(15, 40))
                .build();


        UniqueGearBuilder.of("spark_aura", "Aura of Sparks", BaseGearTypes.RING)
                .keepsBaseName()
                .stat(new AilmentChance(Ailments.BURN).mod(10, 20))
                .stat(AuraEffect.getInstance().mod(5, 15))
                .stat(new ElementalResist(Elements.Fire).mod(10, 25))
                .build();

        UniqueGearBuilder.of("curse_effect_ring", "Eternal Suffering", BaseGearTypes.RING)
                .keepsBaseName()
                .stat(EffectStats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.curse).mod(10, 25))
                .stat(EffectStats.EFFECT_DURATION_YOU_CAST_PER_TAG.get(EffectTags.curse).mod(50, 50))
                .stat(new ElementalResist(Elements.Shadow).mod(10, 25))
                .build();
    }
}