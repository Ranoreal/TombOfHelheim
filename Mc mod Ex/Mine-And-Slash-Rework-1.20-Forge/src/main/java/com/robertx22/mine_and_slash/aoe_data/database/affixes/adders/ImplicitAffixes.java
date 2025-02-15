package com.robertx22.mine_and_slash.aoe_data.database.affixes.adders;

import com.robertx22.mine_and_slash.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.ailments.Ailments;
import com.robertx22.mine_and_slash.aoe_data.database.stats.EffectStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.OffenseStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.ResourceStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.mine_and_slash.database.data.stats.types.ailment.AilmentDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.energy.Energy;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.Health;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.mana.Mana;
import com.robertx22.mine_and_slash.tags.all.EffectTags;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.tags.all.SpellTags;
import com.robertx22.mine_and_slash.tags.imp.SlotTag;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.function.Consumer;

// implicits can't be rerolled, have way less variation and are there to add flavor and mostly reliable stats for newbies
public class ImplicitAffixes implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new Builder(SlotTags.ring).build(x -> {
            x.add("hp_ring", "Coral Ring", Health.getInstance().mod(5, 10));
            x.add("mana_ring", "Paua Ring", Mana.getInstance().mod(10, 20));
            x.add("ms_ring", "Moonstone Ring", MagicShield.getInstance().mod(5, 10));
            x.add("ene_ring", "Emerald Ring", Energy.getInstance().mod(10, 20));
        });
        new Builder(SlotTags.necklace).build(x -> {
            x.add("amber_amulet", "Amber Amulet", DatapackStats.STR.mod(5, 10).percent());
            x.add("lapis_amulet", "Lapis Amulet", DatapackStats.DEX.mod(5, 10).percent());
            x.add("jade_amulet", "Jade Amulet", DatapackStats.INT.mod(5, 10).percent());
        });

        new Builder(SlotTags.PLATE_HELMET).build(x -> {
            x.add("gladiator_helm", "Gladiator Helm", OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Physical).mod(3, 6));
            x.add("siege_helm", "Siege Helmet", OffenseStats.STYLE_DAMAGE.get(PlayStyle.STR).mod(5, 10));
            x.add("royal_helm", "Royal Helm", DatapackStats.STR.mod(3, 6).percent());
        });

        new Builder(SlotTags.LEATHER_HELMET).build(x -> {
            x.add("bandit_mask", "Bandit Mask", OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.trap).mod(5, 10));
            x.add("vdo_mask", "Voodoo Mask", ResourceStats.INCREASED_LEECH.get().mod(5, 10));
            x.add("hunter_hood", "Hunter Hood", OffenseStats.PROJECTILE_DAMAGE.get().mod(3, 6));
            x.add("wolf_pelt", "Wolf Pelt", OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.beast).mod(4, 8));
        });

        new Builder(SlotTags.CLOTH_HELMET).build(x -> {
            x.add("lunaris_circlet", "Lunaris Circlet", 200, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Cold).mod(3, 6));
            x.add("mind_cage", "Mind Cage", Mana.getInstance().mod(10, 20));
            x.add("necro_helmet", "Necromancer Helm", 100, SpellChangeStats.MAX_SUMMON_CAPACITY.get().mod(1, 1));
            x.add("bone_helm", "Bone Circlet", OffenseStats.SUMMON_DAMAGE.get().mod(3, 6));
            x.add("golden_crown", "Golden Crown", new ElementalResist(Elements.Physical).mod(2, 4));
        });

        // todo i hate crossbows..
        new Builder(SlotTags.crossbow).build(x -> {
            x.add("bone_crossbow", "Bone Crossbow", OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Physical).mod(5, 10));
            x.add("imperial_crossbow", "Imperial Crossbow", OffenseStats.CRIT_DAMAGE.get().mod(5, 10));
            x.add("ass_crossbow", "Assassin Crossbow", OffenseStats.CRIT_CHANCE.get().mod(3, 6));
            x.add("thicket_crossbow", "Thicket Crossbow", OffenseStats.PROJECTILE_DAMAGE.get().mod(5, 10));
        });

        new Builder(SlotTags.bow).build(x -> {
            x.add("bone_bow", "Bone Bow", OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Physical).mod(5, 10));
            x.add("imperial_bow", "Imperial Bow", OffenseStats.CRIT_DAMAGE.get().mod(5, 10));
            x.add("ass_bow", "Assassin Bow", OffenseStats.CRIT_CHANCE.get().mod(3, 6));
            x.add("thicket_bow", "Thicket Bow", OffenseStats.PROJECTILE_DAMAGE.get().mod(5, 10));
        });
        new Builder(SlotTags.sword).build(x -> {
            x.add("rusted_sword", "Rusted Sword", new AilmentChance(Ailments.POISON).mod(4, 10));
            x.add("dusk_blade", "Dusk Blade", new AilmentDamage(Ailments.POISON).mod(7, 15));
            x.add("twilight_blade", "Twilight Blade", OffenseStats.CRIT_DAMAGE.get().mod(5, 10));
            x.add("thorn_rapier", "Thorn Rapier", OffenseStats.CRIT_CHANCE.get().mod(3, 3), OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.thorns).mod(5, 10));
        });

        new Builder(SlotTags.staff).build(x -> {
            x.add("arcane_staff", "Arcane Staff", OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Elemental).mod(4, 8));
            x.add("oakwood_staff", "Oak Wood Staff", OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.thorns).mod(10, 20));
            x.add("cleric_staff", "Cleric Staff", ResourceStats.HEAL_STRENGTH.get().mod(10, 20));
            x.add("glacial_staff", "Glacial Staff", OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Cold).mod(10, 15));
            x.add("wildfire_staff", "Wildfire Staff", OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Fire).mod(5, 10), new AilmentChance(Ailments.BURN).mod(3, 5));
            x.add("cursed_staff", "Cursed Staff", OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Shadow).mod(5, 10), EffectStats.EFFECT_DURATION_YOU_CAST_PER_TAG.get(EffectTags.curse).mod(5, 10));
            x.add("lightning_staff", "Lightning Staff", OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Nature).mod(5, 10), OffenseStats.CRIT_CHANCE.get().mod(2, 3));
        });


    }

    static class Builder {

        SlotTag slot;


        public Builder(SlotTag slot) {
            this.slot = slot;
        }

        public void build(Consumer<Builder> c) {
            c.accept(this);
        }

        public void add(String id, String name, int weight, StatMod... mods) {
            AffixBuilder.Normal(id)
                    .Named(name)
                    .stats(mods)
                    .mustIncludesAllTags(slot)
                    .Implicit()
                    .Weight(weight)
                    .Build();
        }

        public void add(String id, String name, StatMod... mods) {
            this.add(id, name, 1000, mods);
        }


    }
}
