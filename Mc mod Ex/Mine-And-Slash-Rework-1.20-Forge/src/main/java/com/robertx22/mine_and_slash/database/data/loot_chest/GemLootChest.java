package com.robertx22.mine_and_slash.database.data.loot_chest;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.data.loot_chest.base.LootChest;
import com.robertx22.mine_and_slash.database.data.loot_chest.base.LootChestData;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.generators.GemLootGen;
import com.robertx22.mine_and_slash.loot.req.DropRequirement;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GemLootChest extends LootChest {

    @Override
    public ItemStack generateOne(LootChestData data) {
        ItemStack stack = new GemLootGen(LootInfo.ofLevel(data.lvl)).generateOne();
        stack.setCount(data.getRarity().item_tier + 1);
        return stack;
    }

    @Override
    public DropRequirement getDropReq() {
        return DropRequirement.Builder.of().build();
    }

    @Override
    public Item getKey() {
        return null;
    }

    @Override
    public Item getChestItem(LootChestData data) {
        return RarityItems.GEAR_CHESTS.get(data.getRarity().GUID()).get();
    }

    @Override
    public String GUID() {
        return "gem";
    }

    @Override
    public int Weight() {
        return (int) (ServerContainer.get().GEM_DROPRATE.get() * 100);
    }


}
