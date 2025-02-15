package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.library_of_exile.deferred.RegObj;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.ICreativeTabNbt;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlashTabs {
    public static RegObj<CreativeModeTab> CREATIVE = Def.creativeTab("tab", () -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 2)
            .icon(() -> RarityItems.RARITY_STONE.get(IRarity.MYTHIC_ID).get().getDefaultInstance())
            .title(Words.MOD_NAME.locName().withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD))
            .displayItems((pParameters, x) -> {
                for (Item item : VanillaUTIL.REGISTRY.items().getAll()) {
                    if (VanillaUTIL.REGISTRY.items().getKey(item).getNamespace().equals(SlashRef.MODID)) {

                        if (item instanceof ICreativeTabNbt nbt) {
                            for (ItemStack stack : nbt.createAllVariationsForCreativeTabs()) {
                                x.accept(stack);
                            }
                        } else {
                            if (item instanceof ICreativeIgnoreOption op) {
                                if (op.shouldShowInCreative()) {
                                    x.accept(() -> item);
                                }
                            } else {
                                x.accept(() -> item);
                            }

                        }
                    }
                }
            })
            .build());

    public static void init() {


    }
}
