package net.shewlzy.tombofhelheim.worldgen.biome.surface;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.shewlzy.tombofhelheim.worldgen.biome.ModBiomes;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.PACKED_MUD);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.MUD);
    private static final SurfaceRules.RuleSource STONE_BLOCK = makeStateRule(Blocks.SOUL_SAND);
    private static final SurfaceRules.RuleSource DEEP_BLOCK = makeStateRule(Blocks.SOUL_SOIL);

    public static SurfaceRules.RuleSource makeRules()
    {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);

        return SurfaceRules.sequence(
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.HELHEIM_FOREST),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, GRASS_BLOCK)),
                        SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, DIRT)),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT),
                        SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, STONE_BLOCK),
                        SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, DEEP_BLOCK),

                // Default to a grass and dirt surface
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block)
    {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
