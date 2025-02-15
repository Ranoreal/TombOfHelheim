package com.robertx22.mine_and_slash.uncommon.datasaving;

import com.robertx22.mine_and_slash.uncommon.item_filters.bases.ItemFilterGroup;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.ChatFormatting;

public enum ItemType {

    GEAR(ItemFilterGroup.ANY_GEAR, Words.Gears),
    NONE(null, Words.None);

    ItemType(ItemFilterGroup filter, Words word) {
        this.filter = filter;
        this.word = word;
    }

    public boolean isType(ItemStack stack) {
        if (filter == null) {
            return false;
        }
        return filter.anyMatchesFilter(stack);
    }

    private ItemFilterGroup filter;
    public Words word;

    public static MutableComponent getTooltipString(ItemType types) {

        MutableComponent comp = Words.UsableOn.locName()
            .append(types.word.locName());

        comp.withStyle(ChatFormatting.LIGHT_PURPLE);

        return comp;

    }

    public static ItemType getType(ItemStack stack) {

        for (ItemType type : ItemType.values()) {
            if (type.isType(stack)) {
                return type;
            }
        }
        return NONE;
    }

}
