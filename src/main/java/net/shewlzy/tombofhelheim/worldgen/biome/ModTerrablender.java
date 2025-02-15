package net.shewlzy.tombofhelheim.worldgen.biome;

import net.minecraft.resources.ResourceLocation;
import net.shewlzy.tombofhelheim.TombOfHelheim;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void registerBiomes() {
        Regions.register(new ModOverworldRegion(new ResourceLocation(TombOfHelheim.MOD_ID, "overworld"), 5));
    }
}