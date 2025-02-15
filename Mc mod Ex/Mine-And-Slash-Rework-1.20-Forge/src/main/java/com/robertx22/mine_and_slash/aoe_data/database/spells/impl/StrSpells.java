package com.robertx22.mine_and_slash.aoe_data.database.spells.impl;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.a_libraries.player_animations.SpellAnimations;
import com.robertx22.mine_and_slash.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.mine_and_slash.aoe_data.database.spells.PartBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellCalcs;
import com.robertx22.mine_and_slash.database.data.spells.components.SpellConfiguration;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.SpellAction;
import com.robertx22.mine_and_slash.tags.all.SpellTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;

import java.util.Arrays;

public class StrSpells implements ExileRegistryInit {
    public static String SHRED = "shred";

    @Override
    public void registerAll() {

        SpellBuilder.of(SHRED, PlayStyle.STR, SpellConfiguration.Builder.instant(10, 20 * 1)
                                .setSwingArm()
                                .setChargesAndRegen("shred", 3, 20 * 15), "Shred",
                        Arrays.asList(SpellTags.weapon_skill, SpellTags.area, SpellTags.damage, SpellTags.PHYSICAL))
                .singleAnimation(SpellAnimations.SINGLE_MELEE_SLASH)
                .manualDesc(
                        "Slash all nearby enemies, dealing "
                                + SpellCalcs.SHRED.getLocDmgTooltip() + " " + Elements.Physical.getIconNameDmg()
                                + " damage and reducing their defenses."
                )

                .onCast(PartBuilder.playSound(SoundEvents.WITHER_SKELETON_HURT, 1D, 1D))

                .onCast(PartBuilder.swordSweepParticles())
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 50D, 2.8D, 0.5D))
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.ENCHANTED_HIT, 25D, 2D, 0.5D))
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 25D, 1D, 0.5D))
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.ENCHANTED_HIT, 50D, 1D, 0.5D))

                .onCast(PartBuilder.justAction(SpellAction.EXILE_EFFECT.giveSeconds(ModEffects.SHRED, 10))
                        .enemiesInRadius(3D))
                .onCast(PartBuilder.damageInAoe(SpellCalcs.SHRED, Elements.Physical, 3D)
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.ENCHANTED_HIT, 15D, 0.5D, 1D)))

                .build();


    }
}
