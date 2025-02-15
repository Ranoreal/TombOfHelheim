package com.robertx22.mine_and_slash.aoe_data.database.spells.schools;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.a_libraries.player_animations.AnimationHolder;
import com.robertx22.mine_and_slash.a_libraries.player_animations.SpellAnimations;
import com.robertx22.mine_and_slash.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.mine_and_slash.aoe_data.database.spells.PartBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellCalcs;
import com.robertx22.mine_and_slash.database.data.spells.components.SpellConfiguration;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.*;
import com.robertx22.mine_and_slash.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.mine_and_slash.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.mine_and_slash.database.data.value_calc.ValueCalculation;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashBlocks;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashEntities;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashPotions;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SlashItems;
import com.robertx22.mine_and_slash.tags.all.SpellTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.DashUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityFinder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;

public class RangerSpells implements ExileRegistryInit {

    public static String EXPLOSIVE_ARROW_ID = "explosive_arrow";
    public static String CHARGED_BOLT = "charged_bolt";
    public static String ARROW_STORM = "arrow_storm";
    public static String RECOIL_SHOT = "recoil_shot";
    public static String DASH_ID = "dash";

    public static String FROST_TRAP = "frost_trap";
    public static String POISON_TRAP = "poison_trap";
    public static String FIRE_TRAP = "fire_trap";
    public static String HUNTER_POTION = "hunter_potion";
    public static String SMOKE_BOMB = "smoke_bomb";
    public static String BARRAGE = "arrow_barrage";

    public static String ARROW_TOTEM = "arrow_totem";
    public static String BOOMERANG = "boomerang";
    public static String QUICKDRAW = "quickdraw";
    public static String GALE_WIND = "gale_wind";
    public static String METEOR_ARROW = "meteor_arrow";

    @Override

    public void registerAll() {

        SpellBuilder.of(ARROW_TOTEM, PlayStyle.DEX, SpellConfiguration.Builder.instant(25, 10)
                                .setChargesAndRegen("arrow_totem", 3, 20 * 30)
                        , "Arrow Totem",
                        Arrays.asList(SpellTags.projectile, SpellTags.damage, SpellTags.totem, SpellTags.PHYSICAL))

                .manualDesc("Summons a totem that rapidly shoots arrows, dealing " + SpellCalcs.ARROW_TOTEM.getLocDmgTooltip(Elements.Physical))

                .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(SlashBlocks.PROJECTILE_TOTEM.get(), 20D * 7.5D)
                        .put(MapField.ENTITY_NAME, "block")
                        .put(MapField.BLOCK_FALL_SPEED, 0D)
                        .put(MapField.FIND_NEAREST_SURFACE, false)
                        .put(MapField.IS_BLOCK_FALLING, false)))

                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 100D, 3D, 0.5D).tick(2D))

                .onTick("block", PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR,
                                1D, 2.5D, SlashEntities.SIMPLE_ARROW.get(), 40D, false)
                        .put(MapField.ENTITY_NAME, "arrow")
                        .put(MapField.POS_SOURCE, PositionSource.SOURCE_ENTITY.name())
                        .put(MapField.SHOOT_DIRECTION, SummonProjectileAction.ShootWay.FIND_ENEMY.name())
                ).tick(5D))
                .onTick("block", PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D).tick(5D))

                .onExpire("arrow", PartBuilder.damageInAoe(SpellCalcs.ARROW_TOTEM, Elements.Physical, 1.5D))
                .levelReq(20)

                .build();


        SpellBuilder.of(BOOMERANG, PlayStyle.DEX, SpellConfiguration.Builder.instant(10, 20 * 5)
                                .setChargesAndRegen("boomerang", 3, 20 * 10)
                                .applyCastSpeedToCooldown(), "Boomerang",
                        Arrays.asList(SpellTags.projectile, SpellTags.damage, SpellTags.chaining, SpellTags.PHYSICAL))
                .manualDesc("Strike enemies with a projectile that deals " + SpellCalcs.BOOMERANG.getLocDmgTooltip(Elements.Physical))
                .animations(SpellAnimations.THROW, AnimationHolder.none())
                .onCast(PartBuilder.playSound(SoundEvents.SNOWBALL_THROW, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(SlashItems.BOOMERANG.get(), 1D, 1D, SlashEntities.SIMPLE_PROJECTILE.get(), 50D, false)
                        .put(MapField.CHAIN_COUNT, 5D)
                ))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CRIT, 10D, 0.01D))
                .onExpire(PartBuilder.damageInAoe(SpellCalcs.BOOMERANG, Elements.Physical, 1D))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.CRIT, 50D, 0.5D))
                .onExpire(PartBuilder.playSound(SoundEvents.TRIDENT_HIT, 1D, 1D))
                .levelReq(1)
                .build();


        SpellBuilder.of(HUNTER_POTION, PlayStyle.DEX, SpellConfiguration.Builder.instant(0, 60 * 20 * 3), "Hunter's Potion",
                        Arrays.asList(SpellTags.heal)
                )
                .manualDesc("Drink a potion, healing you for " + SpellCalcs.HUNTER_POTION_HEAL.getLocDmgTooltip() + " health.")
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1D, 1D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 40D, 1.5D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
                .onCast(PartBuilder.healCaster(SpellCalcs.HUNTER_POTION_HEAL))
                .levelReq(10)
                .build();

        trap(FROST_TRAP, "Frost Trap", ParticleTypes.ITEM_SNOWBALL, SpellCalcs.RANGER_TRAP, Elements.Cold).levelReq(10).build();
        trap(POISON_TRAP, "Acid Trap", ParticleTypes.ITEM_SLIME, SpellCalcs.RANGER_TRAP, Elements.Shadow).levelReq(30).build();
        trap(FIRE_TRAP, "Fire Trap", ParticleTypes.FLAME, SpellCalcs.RANGER_TRAP, Elements.Fire).levelReq(1).build();

        SpellBuilder.of(SMOKE_BOMB, PlayStyle.DEX, SpellConfiguration.Builder.instant(7, 20 * 60), "Smoke Bomb",
                        Arrays.asList())
                .manualDesc("Throw out a smoke bomb, blinding enemies and reducing threat.")
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.SPLASH_POTION_BREAK, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.AGGRO.create(SpellCalcs.SMOKE_BOMB, AggroAction.Type.DE_AGGRO))
                        .addActions(SpellAction.EXILE_EFFECT.create(ModEffects.BLIND.resourcePath, ExileEffectAction.GiveOrTake.GIVE_STACKS, 20D * 5))
                        .addTarget(TargetSelector.AOE.create(10D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.SMOKE, 200D, 3D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.EFFECT, 50D, 3D))
                .levelReq(20)
                .build();


        SpellBuilder.of(DASH_ID, PlayStyle.DEX, SpellConfiguration.Builder.instant(10, 15)

                                .setChargesAndRegen("dash", 3, 20 * 30)
                        , "Dash",
                        Arrays.asList(SpellTags.movement, SpellTags.weapon_skill))
                .manualDesc(
                        "Dash forwards quickly and gain slowfall.")
                .weaponReq(CastingWeapon.NON_MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.CREEPER_PRIMED, 1D, 1.6D)
                        .addActions(SpellAction.POTION.createGive(MobEffects.SLOW_FALLING, 20D))
                        .addTarget(TargetSelector.CASTER.create()))
                .onCast(PartBuilder.playSound(SoundEvents.FIRE_EXTINGUISH, 1D, 1.6D))

                .teleportForward()
                .levelReq(1)

                .build();

        // todo this one needs a big hold and release anim
        SpellBuilder.of(CHARGED_BOLT, PlayStyle.DEX, SpellConfiguration.Builder.arrowSpell(8, 20 * 15), "Charged Bolt",
                        Arrays.asList(SpellTags.projectile, SpellTags.area, SpellTags.damage, SpellTags.LIGHTNING))

                .manualDesc(
                        "Shoot a slowing charged arrow that goes through enemies and deals "
                                + SpellCalcs.CHARGED_BOLT.getLocDmgTooltip() + " " + Elements.Nature.getIconNameDmg() + " in a radius along its path.")

                .weaponReq(CastingWeapon.RANGED)
                .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
                .onCast(PartBuilder.playSound(SoundEvents.DRAGON_FIREBALL_EXPLODE, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)
                        .put(MapField.PROJECTILE_SPEED, 1D)
                        .put(MapField.EXPIRE_ON_ENTITY_HIT, false)
                        .put(MapField.GRAVITY, false)))

                .onHit(PartBuilder.aoeParticles(ParticleTypes.CRIT, 100D, 1D))
                .onHit(PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
                .onHit(PartBuilder.damageInAoe(SpellCalcs.CHARGED_BOLT, Elements.Nature, 2D)
                        .addPerEntityHit(PartBuilder.justAction(SpellAction.POTION.createGive(MobEffects.MOVEMENT_SLOWDOWN, 40D))))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CRIT, 4D, 0.1D))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ENCHANTED_HIT, 4D, 0.1D))
                .levelReq(1)
                .build();


        SpellBuilder.of(QUICKDRAW, PlayStyle.DEX, SpellConfiguration.Builder.instant(5, 20 * 5)
                        .setChargesAndRegen("quickdraw", 3, 20 * 60), "Quickdraw", Arrays.asList())
                .manualDesc("Your bow now fires instantly, and you gain a stack of arrows if you don't have infinity.")

                .onCast(PartBuilder.playSound(SoundEvents.WOLF_HOWL, 1D, 1D))
                .onCast(PartBuilder.giveSelfEffect(SlashPotions.INSTANT_ARROWS.get(), 20D * 10, 16D))
                .onCast(PartBuilder.giveSelfEffect(MobEffects.MOVEMENT_SPEED, 20D * 3))
                .onCast(PartBuilder.justAction(SpellAction.GIVE_ARROWS.create()))
                .levelReq(10)
                .build();


        // todo does this do anything?
        SpellBuilder.of(ARROW_STORM, PlayStyle.DEX, SpellConfiguration.Builder.arrowSpell(20, 20 * 25), "Arrow Storm",
                        Arrays.asList(SpellTags.projectile, SpellTags.damage, SpellTags.PHYSICAL))
                .weaponReq(CastingWeapon.RANGED)

                .singleAnimation(SpellAnimations.SINGLE_ARROW_SHOT)

                .manualDesc("Shoot out arrows in an arc, dealing " + SpellCalcs.ARROW_STORM.getLocDmgTooltip(Elements.Physical))
                .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(5D)))
                .onHit(PartBuilder.particleOnTick(3D, ParticleTypes.CLOUD, 3D, 0.1D))
                .onHit(PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
                .onHit(PartBuilder.damage(SpellCalcs.ARROW_STORM, Elements.Physical))
                .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.CRIT, 5D, 0.1D))
                .levelReq(30)
                .build();

        SpellBuilder.of(GALE_WIND, PlayStyle.DEX, SpellConfiguration.Builder.multiCast(20, 20 * 10, 10, 3), "Gale Wind",
                        Arrays.asList(SpellTags.projectile, SpellTags.damage, SpellTags.PHYSICAL))
                .manualDesc("Summons multiple clouds, knocking back and dealing " + SpellCalcs.GALE_WIND.getLocDmgTooltip(Elements.Physical))
                .onCast(PartBuilder.playSound(SoundEvents.EGG_THROW, 1D, 0.5D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 5D, 0.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 40D, false)))

                .onTick(PartBuilder.particleOnTick(2D, ParticleTypes.CLOUD, 3D, 0.1D))

                .onHit(PartBuilder.playSound(SoundEvents.ANVIL_HIT, 1D, 2D))
                .onHit(PartBuilder.knockback(5D))
                .onHit(PartBuilder.damage(SpellCalcs.GALE_WIND, Elements.Physical))

                .onExpire(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 5D, 1D))
                .levelReq(20)

                .build();

        SpellBuilder.of(BARRAGE, PlayStyle.DEX, SpellConfiguration.Builder.multiCast(20, 20, 20, 10)
                                .setChargesAndRegen(BARRAGE, 3, 20 * 10), "Arrow Barrage",
                        Arrays.asList(SpellTags.projectile, SpellTags.damage, SpellTags.PHYSICAL))

                .weaponReq(CastingWeapon.RANGED)
                .animations(SpellAnimations.SHOOT_ARROW_FAST, SpellAnimations.CAST_FINISH)
                .manualDesc("Shoot out arrows in rapid succession, dealing " + SpellCalcs.ARROW_STORM.getLocDmgTooltip(Elements.Physical))
                .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)))
                .onHit(PartBuilder.particleOnTick(3D, ParticleTypes.CLOUD, 3D, 0.1D))
                .onHit(PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
                .onHit(PartBuilder.damage(SpellCalcs.ARROW_STORM, Elements.Physical))
                .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.CRIT, 5D, 0.1D))
                .levelReq(1)
                .build();


        SpellBuilder.of(EXPLOSIVE_ARROW_ID, PlayStyle.DEX, SpellConfiguration.Builder.arrowSpell(10, 20 * 10), "Explosive Arrow",
                        Arrays.asList(SpellTags.projectile, SpellTags.damage, SpellTags.FIRE))
                .weaponReq(CastingWeapon.RANGED)
                .manualDesc("Shoot an arrow that upon impact, deals " + SpellCalcs.EXPLOSIVE_ARROW.getLocDmgTooltip(Elements.Fire) + " in an area")
                .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 1D, 0.1D))
                .onExpire(PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
                .onExpire(PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))
                .onExpire(PartBuilder.damageInAoe(SpellCalcs.EXPLOSIVE_ARROW, Elements.Fire, 2D)
                        .addPerEntityHit(PartBuilder.justAction(SpellAction.POTION.createGive(MobEffects.MOVEMENT_SLOWDOWN, 40D))))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CRIT, 4D, 0.1D))
                .levelReq(10)
                .build();

        SpellBuilder.of(RECOIL_SHOT, PlayStyle.DEX, SpellConfiguration.Builder.arrowSpell(10, 20 * 10), "Recoil Shot",
                        Arrays.asList(SpellTags.projectile, SpellTags.damage, SpellTags.PHYSICAL))
                .weaponReq(CastingWeapon.RANGED)
                .manualDesc("Flip backwards while shooting an arrow that deals " + SpellCalcs.EXPLOSIVE_ARROW.getLocDmgTooltip(Elements.Physical) + " and applies Wounds in an area.")
                .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)))
                .onHit(PartBuilder.damage(SpellCalcs.DIRECT_ARROW_HIT, Elements.Physical))
                .onCast(PartBuilder.pushCaster(DashUtils.Way.BACKWARDS, DashUtils.Strength.MEDIUM_DISTANCE))
                .onHit(PartBuilder.addExileEffectToEnemiesInAoe(ModEffects.WOUNDS.resourcePath, 1D, 20 * 20D))
                .onHit(PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
                .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.CRIT, 5D, 0.1D)
                )
                .levelReq(10)
                .build();

        SpellBuilder.of(METEOR_ARROW, PlayStyle.DEX, SpellConfiguration.Builder.arrowSpell(15, 10)
                                .setChargesAndRegen("meteor_arrow", 3, 20 * 10), "Meteor Arrow",
                        Arrays.asList(SpellTags.projectile, SpellTags.area, SpellTags.damage, SpellTags.FIRE))
                .weaponReq(CastingWeapon.RANGED)
                .manualDesc("Shoots an arrow that deals "
                        + SpellCalcs.METEOR.getLocDmgTooltip(Elements.Physical)
                        + " and summons a meteor that deals "
                        + SpellCalcs.METEOR.getLocDmgTooltip(Elements.Fire))
                .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D).put(MapField.ENTITY_NAME, "arrow")))

                .onHit("arrow", PartBuilder.damage(SpellCalcs.METEOR, Elements.Physical))
                .onHit("arrow", PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
                .onTick("arrow", PartBuilder.particleOnTick(1D, ParticleTypes.LAVA, 1D, 0.1D))
                .onTick("arrow", PartBuilder.particleOnTick(1D, ParticleTypes.FLAME, 1D, 0.1D))

                .onExpire("arrow", PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 0D, 7D)
                        .put(MapField.ENTITY_NAME, "height_en")
                        .put(MapField.DISTANCE, 0D)
                        .put(MapField.POS_SOURCE, PositionSource.SOURCE_ENTITY.name())
                ))

                .onExpire("height_en", PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.MAGMA_BLOCK, 200D)
                        .put(MapField.ENTITY_NAME, "meteor")
                        .put(MapField.FIND_NEAREST_SURFACE, false)
                        .put(MapField.BLOCK_FALL_SPEED, -0.03D)
                        .put(MapField.IS_BLOCK_FALLING, true)))

                .onTick("meteor", PartBuilder.particleOnTick(2D, ParticleTypes.LAVA, 2D, 0.5D))
                .onExpire("meteor", PartBuilder.damageInAoe(SpellCalcs.METEOR, Elements.Fire, 3D))
                .onExpire("meteor", PartBuilder.aoeParticles(ParticleTypes.LAVA, 150D, 3D))
                .onExpire("meteor", PartBuilder.aoeParticles(ParticleTypes.ASH, 25D, 3D))
                .onExpire("meteor", PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 15D, 3D))
                .onExpire("meteor", PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))

                .levelReq(20)
                .build();


    }

    static SpellBuilder trap(String id, String name, SimpleParticleType particle, ValueCalculation dmg, Elements element) {

        return SpellBuilder.of(id, PlayStyle.DEX, SpellConfiguration.Builder.instant(7, 5)
                                .setChargesAndRegen(id, 3, 20 * 30)
                                .setSwingArm(), name,
                        Arrays.asList(SpellTags.damage, SpellTags.area, SpellTags.trap, element.spellTag))
                .manualDesc(
                        "Throw out a trap that stays on the ground and activates when an enemy approaches to deal "
                                + dmg.getLocDmgTooltip() + " " + element.getIconNameDmg() + " in area around itself."
                )
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.SNOWBALL_THROW, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.IRON_INGOT, 1D, 0.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 100D, true)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(SlashBlocks.TRAP.get(), 30 * 20D)
                        .put(MapField.ENTITY_NAME, "trap")
                        .put(MapField.FIND_NEAREST_SURFACE, true)
                        .put(MapField.IS_BLOCK_FALLING, false)))

                .onTick("trap", PartBuilder.aoeParticles(particle, 5D, 1D)
                        .addCondition(EffectCondition.IS_ENTITY_IN_RADIUS.enemiesInRadius(1D))
                        .addActions(SpellAction.EXPIRE.create())
                        .addActions(SpellAction.SPECIFIC_ACTION.create("explode"))
                        .tick(2D))

                .addSpecificAction("explode", PartBuilder.damageInAoe(dmg, element, 3D))
                .addSpecificAction("explode", PartBuilder.aoeParticles(particle, 30D, 3D))
                .addSpecificAction("explode", PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D));

    }

}
