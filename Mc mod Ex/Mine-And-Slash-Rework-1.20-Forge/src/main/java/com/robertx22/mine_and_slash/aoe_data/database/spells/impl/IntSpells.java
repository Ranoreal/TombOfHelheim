package com.robertx22.mine_and_slash.aoe_data.database.spells.impl;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.a_libraries.player_animations.SpellAnimations;
import com.robertx22.mine_and_slash.aoe_data.database.spells.PartBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellCalcs;
import com.robertx22.mine_and_slash.database.data.spells.components.SpellConfiguration;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.SpellAction;
import com.robertx22.mine_and_slash.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashBlocks;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashEntities;
import com.robertx22.mine_and_slash.tags.all.SpellTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityFinder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;

import java.util.Arrays;

public class IntSpells implements ExileRegistryInit {
    public static String BLACK_HOLE = "black_hole";
    public static String TELEPORT = "teleport";

    @Override
    public void registerAll() {

        SpellBuilder.of(BLACK_HOLE, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(30, 20 * 60, 40)
                                .setSwingArm(), "Black Hole",
                        Arrays.asList(SpellTags.damage, SpellTags.area, SpellTags.CHAOS))

                .animations(SpellAnimations.STAFF_CAST_WAVE_LOOP, SpellAnimations.STAFF_CAST_FINISH)

                .weaponReq(CastingWeapon.MAGE_WEAPON)

                .manualDesc("Summon a dark sphere that attracts nearby enemies to it, dealing "
                        + SpellCalcs.BLACK_HOLE.getLocDmgTooltip() + " "
                        + Elements.Shadow.getIconNameDmg() + " when it expires.")

                .onCast(PartBuilder.playSound(SoundEvents.END_PORTAL_SPAWN, 1D, 1D))

                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(SlashBlocks.BLACK_HOLE.get(), 20D * 3)
                        .put(MapField.ENTITY_NAME, "block")
                        .put(MapField.BLOCK_FALL_SPEED, 0D)
                        .put(MapField.FIND_NEAREST_SURFACE, true)
                        .put(MapField.IS_BLOCK_FALLING, false)))

                .onTick("block", PartBuilder.particleOnTick(3D, ParticleTypes.PORTAL, 40D, 1D))
                .onTick("block", PartBuilder.particleOnTick(3D, ParticleTypes.WITCH, 8D, 1D))
                .onTick("block", PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                        .addTarget(TargetSelector.AOE.create(3D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
                .onExpire("block", PartBuilder.damageInAoe(SpellCalcs.BLACK_HOLE, Elements.Shadow, 2D))
                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.WITCH, 100D, 3D, 3D).tick(3D))
                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.WITCH, 100D, 3D, 1.5D).tick(3D))
                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.WITCH, 50D, 1.5D, 3D).tick(3D))
                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.WITCH, 50D, 1.5D, 1.5D).tick(3D))
                .build();

        SpellBuilder.of(TELEPORT, PlayStyle.INT, SpellConfiguration.Builder.instant(20, 20 * 30)
                        , "Teleport",
                        Arrays.asList(SpellTags.damage, SpellTags.movement)
                )
                .manualDesc("Teleport yourself in the direction you're looking at.")
                .disableInMapDimension()
                .teleportForward()

                .onCast(PartBuilder.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1D, 1D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 30D, 2D))

                .build();

    }
}
