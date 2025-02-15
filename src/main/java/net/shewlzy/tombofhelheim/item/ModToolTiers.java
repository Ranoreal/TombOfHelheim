package net.shewlzy.tombofhelheim.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.shewlzy.tombofhelheim.TombOfHelheim;
import net.shewlzy.tombofhelheim.item.util.ModTags;

import java.util.List;

public class ModToolTiers {
    public static final Tier DIVAN = TierSortingRegistry.registerTier(
            new ForgeTier(10, 3000, 10f, 8f, 50,
                    ModTags.Blocks.NEEDS_DIVAN_TOOL, () -> Ingredient.of(ModItems.DIVAN_ALLOY.get())),
            new ResourceLocation(TombOfHelheim.MOD_ID, "divan"), List.of(Tiers.NETHERITE), List.of());

}